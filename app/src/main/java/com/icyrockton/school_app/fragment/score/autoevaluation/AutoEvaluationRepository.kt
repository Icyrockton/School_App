package com.icyrockton.school_app.fragment.score.autoevaluation

import android.util.Log
import com.icyrockton.school_app.R
import com.icyrockton.school_app.network.NetworkAPI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup

class AutoEvaluationRepository(private val networkAPI: NetworkAPI) {
    companion object{
        private const val TAG = "AutoEvaluationRepositor"
    }

    /*
    获取所有需要评价的课程的sid 和 lid
     */
    suspend fun getAllNeedEvaluationCourse(): List<AutoEvaluationCourse> =
        withContext(Dispatchers.IO) {
            val responseBody = networkAPI.getAllNeedEvaluationCourse()
            val document = Jsoup.parse(responseBody.string())
            val list = mutableListOf<AutoEvaluationCourse>()
            val tbody = document.getElementById("table3").selectFirst("tbody")
            val rows = tbody.select("tr")
            for (i in 1 until rows.size) {
                val row = rows[i]
                val tds = row.select("td")
                if (tds[tds.size - 1].text() != "填写问卷") //跳过.... 已经填写过了...
                    continue
                val courseID = tds[1].text()
                val courseName = tds[2].text() //课程名称
                val url = tds[tds.size - 1].selectFirst("a").attr("href")  //跳转地址
                parseUrl(url)
                val (sid, lid) = parseUrl(url)
                val templateFlag: Int = 0
                list.add(AutoEvaluationCourse(courseID, courseName, sid, lid, templateFlag))
            }
            return@withContext list
        }

    private fun parseUrl(url: String): Pair<String, String> { //解析 sid 和 lid
        val sidIndex = url.indexOf("sid")
        var index = url.indexOf("&", sidIndex)
        val sid = url.substring(sidIndex + 4, index)
        val lidIndex = url.indexOf("lid")
        index = url.indexOf("&", lidIndex)
        val lid = url.substring(lidIndex + 4, index)
        return Pair(sid, lid)
    }


    /*
    生成要post的数据
     */
    suspend fun getNeedEvaluationCourse(
        sid: String,
        lid: String,
        defaultScore: String ,
        defaultContent: String
    ):AutoEvaluationPostWrapper = withContext(Dispatchers.IO) {
        val response = networkAPI.getNeedEvaluationCourse(sid, lid)
        val answerForm = Jsoup.parse(response.string()).getElementById("answerForm")
        val assess_id = answerForm.selectFirst("input[name=assess_id]").attr("value")
        var ids = ""
        var ans = ""
        var scores = ""
        var percents = ""

        //选择题的ID ， 占比等数据
        val problemQuestions = answerForm.select("input[name=problem_id]")


        //所有的选择题
        val choiceQuestions = answerForm.select(".answerDiv.questionDiv")
        for (i in 0 until choiceQuestions.size) {
            val id = problemQuestions[i].attr("value")
            val prercent = problemQuestions[i].attr("perc")
            val score = defaultScore
            var answer = "" // 选择的答案
            //完全符合 符合 基本符合 等选项
            val choices = choiceQuestions[i].select("input")
            choices.forEach {
                if (it.attr("score") == defaultScore) {
                    answer = it.attr("value").trim()
                    return@forEach
                }
            }
            ids += "_$id"
            ans += "_$answer"
            scores += "_$score"
            percents += "_$prercent"
        }

        Log.d(TAG, "getNeedEvaluationCourse: $defaultContent")
        //所有的填空题
        for (i in choiceQuestions.size until problemQuestions.size) {
            val textDiv = problemQuestions[i]//填空题
            val problem = textDiv.selectFirst("input[name=problem_id]")
            val id = problem.attr("value")
            val prercent = problem.attr("perc")
                
            ids += "_$id"
            scores+="_"
            ans += "_$defaultContent"
            percents += "_$prercent"
        }
        return@withContext AutoEvaluationPostWrapper(sid,lid,ans,scores,percents,ids,assess_id)
    }

    /*
    post提交数据
     */
    suspend fun postEvaluationCourse(
        autoEvaluationPostWrapper: AutoEvaluationPostWrapper
    )= withContext(Dispatchers.IO){
        delay(1000L)
        var responseBody=""
        while (true){   //恶心... 非得post两次才行???
            responseBody = networkAPI.postEvaluationCourse(
                "http://jwc.swjtu.edu.cn/vatuu/AssessAction?setAction=viewAssess&sid=${autoEvaluationPostWrapper.sid}&lid=${autoEvaluationPostWrapper.lid}&templateFlag=0",
          autoEvaluationPostWrapper.answer,
          autoEvaluationPostWrapper.assess_id,
          autoEvaluationPostWrapper.id,
          autoEvaluationPostWrapper.lid,
          autoEvaluationPostWrapper.scores,
          autoEvaluationPostWrapper.percents
            ).string()
            if(Jsoup.parse(responseBody).body().text().indexOf("参数错误") ==-1)  //提交完成 退出....
                break
            delay(200L)
        }
        return@withContext responseBody
    }
}