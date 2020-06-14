package com.icyrockton.school_app.fragment.score.daily

import android.util.Log
import com.icyrockton.school_app.fragment.score.NeedCourseEvaluationException
import com.icyrockton.school_app.network.NetworkAPI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup

class DailyScoreRepository(private val networkAPI: NetworkAPI) {

    companion object {
        private const val TABLE_NAME = "table3"
    }

    suspend fun getDailyScore() = withContext(Dispatchers.IO) {
        val response = networkAPI.getDailyScore()
        val document = Jsoup.parse(response.string())
        try {

            val tableBody = document.getElementById(TABLE_NAME).selectFirst("tbody")
            val rows = tableBody.select("tr")//所有行
            val result = mutableListOf<DailyScoreWrapper>()
            var code=""//代码
            var course_name= ""//课程名称
            var class_number= ""//班号
            var teacher_name= ""//教师
            var new_Row= true
            var summary = ""
            val list = mutableListOf<DailyScore>()
            for (i in 1 until rows.size) {
                val row = rows[i]
                val column = row.select("td")
                if (column.size == 1) {
                    summary = column[0].text()//总结
                    result.add(
                        DailyScoreWrapper(
                            code,
                            course_name,
                            class_number,
                            teacher_name,
                            list.toList(),
                            summary
                        )
                    )
                    list.clear()//清空
                    new_Row = true
                    continue
                }
                if (new_Row) {
                    code = column[2].text()
                    course_name = column[3].text()
                    class_number = column[4].text()
                    teacher_name = column[5].text()
                    new_Row = false
                }
                list.add(
                    DailyScore(
                        column[6].text(),
                        column[7].text(),
                        column[8].text(),
                        column[10].text()
                    )
                )
            }
            return@withContext result
        }catch (e:NullPointerException){
            val bodyText=document.body().text()
            if (bodyText.indexOf("评价系统") != -1)
                throw NeedCourseEvaluationException()   //抛出课程需要评价
            return@withContext emptyList<DailyScoreWrapper>()
        }
        }
}