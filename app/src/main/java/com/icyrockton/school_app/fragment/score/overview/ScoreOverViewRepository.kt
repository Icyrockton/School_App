package com.icyrockton.school_app.fragment.score.overview

import android.util.Log
import com.icyrockton.school_app.fragment.score.NeedCourseEvaluationException
import com.icyrockton.school_app.network.NetworkAPI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import org.jsoup.Jsoup
import org.jsoup.select.Elements
import org.w3c.dom.Document

class ScoreOverViewRepository(private val networkAPI: NetworkAPI) {
    companion object {
        const val ORDER_BY_TERM_YEAR = "termValue"
        const val ORDER_BY_COURSE_CODE = "courseCode"
        const val ORDER_BY_COURSE_NAME = "courseName"
        const val ORDER_BY_DESC = "desc"
        const val ORDER_BY_ASC = "asc"
        private const val TAG = "ScoreOverViewRepository"
        val COURSE= arrayListOf<String>("编译原理","操作系统","操作系统实验","程序语言综合课程设计","大学物理BⅡ","大学物理BI","大学物理实验AⅠ","大学物理实验AⅡ",
        "概率论与数理统计B","高等数学BⅠ","高等数学BⅡ","高级语言程序设计","高级语言程序设计实验","计算机图形学","计算机图形学实验","计算机网络","计算机网络工程实验",
        "计算机组成实验","计算机组成原理","离散数学A","面向对象程序设计","面向对象程序设计实验","嵌入式系统设计与应用","嵌入式系统设计与应用实验","软件工程","数据结构",
        "数据结构实验","数据库原理与设计","数据库原理与设计实验","数字电子技术B","数字电子技术实验B","算法分析与设计","微机与接口技术A","微机与接口技术实验","现代铁路信息技术导论","线性代数B",
            "英语Ⅰ","英语Ⅱ","Java程序设计","互联网搜索引擎","网络编程技术")
    }


    suspend fun getAllScore(orderType: String): List<ScoreDetail> = withContext(Dispatchers.IO) {
        val response = networkAPI.getAllScore(orderType)
        return@withContext parseContent(response)
    }

    suspend fun getAllScoreByDate(orderValue: String): List<ScoreDetail> =
        withContext(Dispatchers.IO) {
            val response = networkAPI.getAllScoreByDate(orderValue)
            return@withContext parseContent(response).also {
                Log.d(TAG, "getAllScoreByDate: ${it}")
            }
        }

    private fun parseRow(elements: Elements): ScoreDetail {
        return ScoreDetail(
            elements[1].text(),
            elements[2].text(),
            elements[3].text(),
            elements[4].text(),
            elements[5].text(),
            elements[6].text(),
            elements[7].text(),
            elements[9].text(),
            elements[10].text(),
            elements[12].text(),
            elements[13].text()
        )
    }

    private fun parseContent(response: ResponseBody): List<ScoreDetail> {
        val list = mutableListOf<ScoreDetail>()
        val str = response.string()
        val document = Jsoup.parse(str)
        Log.d(TAG, "parseContent: ${document}")

        try {

            val tbody = document.getElementById("table3").selectFirst("tbody")//成绩table
            val rows = tbody.select("tr")
            for (i in 1 until rows.size) {//跳过第0行
                val row = rows[i]
                val contents = row.select("td")//列的内容
                list.add(
                    parseRow(contents)
                )
            }


            return list

        }
        catch (e:NullPointerException){
            val bodyText=document.body().text()
            if (bodyText.indexOf("评价系统") != -1)
                throw NeedCourseEvaluationException()   //抛出课程需要评价
            return emptyList()
        }
    }


}