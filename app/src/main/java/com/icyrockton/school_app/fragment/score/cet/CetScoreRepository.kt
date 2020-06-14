package com.icyrockton.school_app.fragment.score.cet

import android.util.Log
import com.icyrockton.school_app.fragment.score.NeedCourseEvaluationException
import com.icyrockton.school_app.network.NetworkAPI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup

class CetScoreRepository(private val networkAPI: NetworkAPI) {
    companion object {
        private const val TABLE_NAME = "table3"
    }

    suspend fun getCETScore() = withContext(Dispatchers.IO) {
        val response = networkAPI.getCETScore()
        val document = Jsoup.parse(response.string())
        Log.d("-------", "getCETScore: $document")
        try {
            val tableBody = document.getElementById(TABLE_NAME).selectFirst("tbody")
            val rows = tableBody.select("tr")
            val list = mutableListOf<CetScore>()
            for (i in 1 until rows.size) {
                val col = rows[i].select("td")
                list.add(
                    CetScore(
                        col[0].text(),
                        col[1].text(),
                        col[5].text(),
                        col[6].text(),
                        col[9].text(),
                        col[10].text(),
                        col[11].text(),
                        col[12].text(),
                        col[13].text()
                    )
                )
            }
            return@withContext list
        }catch (e:NullPointerException){
            val bodyText=document.body().text()
            if (bodyText.indexOf("评价系统") != -1)
                throw NeedCourseEvaluationException()   //抛出课程需要评价
            return@withContext  emptyList<CetScore>()
        }

    }
}