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
    }

    suspend fun getAllScore(orderType: String): List<ScoreDetail> = withContext(Dispatchers.IO) {
        val response = networkAPI.getAllScore(orderType)
        return@withContext parseContent(response)
    }

    suspend fun getAllScoreByDate(orderValue: String): List<ScoreDetail> =
        withContext(Dispatchers.IO) {
            val response = networkAPI.getAllScoreByDate(orderValue)
            return@withContext parseContent(response)
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