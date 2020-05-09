package com.icyrockton.school_app.fragment.second_class

import android.util.Log
import com.icyrockton.school_app.network.NetworkAPI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup
import org.jsoup.nodes.Element

class SecondClassRepository(private val networkAPI: NetworkAPI) {

    suspend fun getSecondClassInfo() = withContext(Dispatchers.IO) {
        //:List<SecondClassInfo>
        val response = networkAPI.getSecondClassInfo()
        val rows = Jsoup.parse(response.string()).getElementById("stb").select("tr")
        val list = mutableListOf<SecondClassInfo>()
        rows.forEach {
            list.add(parseSecondClassInfo(it))
        }
        return@withContext list

    }

    private fun parseSecondClassInfo(element: Element): SecondClassInfo {
        val td = element.select("td")

        //课程基本信息
        val basic_spans = td[1].selectFirst("div").select("span")
        val course_name = basic_spans[0].text().trim()
        val ID = parseID(basic_spans[0].attr("onclick"))
        val semester = basic_spans[1].text().substringAfter('：')
        val course_ID = basic_spans[5].text().trim()

        //学时
        val credit_spans = td[2].selectFirst("div").select("span")
        val credit = credit_spans[1].text().trim()
        val max_credit = credit_spans[3].text().trim()

        //课程分类
        val category_spans = td[3].selectFirst("div")
        val course_category = category_spans.text().trim().split(" ")[0]         ///<-----------

        //开课信息
        val course_info = td[4].selectFirst("div")
        val institute = course_info.text().trim().split(" ")[0]
        val teacher = course_info.select("span")[1].text().trim()

        //上课时间地点
        val location_info = td[5].selectFirst("div")
        val split = location_info.text().trim().split(" ")
        val date = "${split[0]} ${split[1]}"
        val location = location_info.selectFirst("span").text().trim()

        //选课容量
        val capacity_info = td[6].select("span")
        val total_capacity = capacity_info[1].text()
        val selected_capacity = capacity_info[0].text()

        var isEnabled = true
        var hintMsg = ""
        //操作
        if (td[7].select("span").size > 0) {
            hintMsg = td[7].select("span")[0].text().trim()//提示信息
            isEnabled = false
        }
        if (td[7].text().trim() == "容量已满") {
            hintMsg = "容量已满"
            isEnabled = false
        }
        return SecondClassInfo(
            ID,
            course_name,
            credit,
            max_credit,
            course_category,
            semester,
            course_ID,
            institute,
            teacher,
            date,
            location,
            total_capacity,
            selected_capacity,
            isEnabled,
            hintMsg
        )
    }

    companion object {
        private const val TAG = "SecondClassRepository"
    }

    private fun parseID(s: String): String {
        val startIndex = s.indexOf(',') + 2
        val endIndex = s.indexOf('\'', startIndex)
        return s.substring(startIndex, endIndex)
    }

    suspend fun postSecondClassAsAudience(courseID: String) = withContext(Dispatchers.IO) {
        return@withContext networkAPI.postSecondClass(courseID, "观众")
    }

    suspend fun postSecondClassAsHost(courseID: String) = withContext(Dispatchers.IO) {
        return@withContext networkAPI.postSecondClass(courseID, "主办方")
    }


    suspend fun getSecondClassDetailInfo(courseID: String, credit: String) =
        withContext(Dispatchers.IO) {
            val response = networkAPI.getSecondClassDetailInfo(courseID)
            val document = Jsoup.parse(response.string())
            val basicInfo = document.select(".classDetailGray")

            val img_div = document.select(".leftImg").select("img")[0].attr("src")
            val endIndex = img_div.indexOf("_big")
            val startIndex=3
            val img_url="http://jwc.swjtu.edu.cn/${img_div.substring(startIndex,endIndex)}.jpg"

            val date = basicInfo[0].text().trim()
            val capacity = basicInfo[1].text().trim().split('/')
            val selected_capacity = capacity[0]
            val total_capacity = capacity[1]
            val course_category = basicInfo[2].text().trim()
            val location = basicInfo[3].text().trim()
            val campus = basicInfo[4].text().trim()
            val target = basicInfo[5].text().trim()
            val teaching_method = basicInfo[6].text().trim()
            val registration_time = basicInfo[7].text().trim()
            val credit_explain = basicInfo[8].text().trim()

            val tabUL = document.getElementById("tabUl")
            val intro = tabUL.select(".simpIntroBox")
            val course_introduction = intro[0].select("p").text()
            var activity_arrangement = ""
            if (intro.size>=2) {
                val raw_arrangement= intro[1].select("p")
                raw_arrangement.forEach {
                    activity_arrangement += "${it.text().trim()}\n"
                }
            }

            val reponsePeople = document.getElementById("classReponsePeople").select("p")
            var person_in_charge = ""
            reponsePeople.forEach {
                person_in_charge += "${it.text().trim()}\n"
            }
            val speaker = document.getElementById("documtnDiv").select("p").text().trim()
            val FQA = document.getElementById("questionDiv").select("p").text().trim()
            return@withContext SecondClassDetailInfo(
                courseID,
                img_url,
                date,
                credit,
                total_capacity,
                selected_capacity,
                course_category,
                location,
                campus,
                target,
                teaching_method,
                registration_time,
                credit_explain,
                course_introduction,
                activity_arrangement,
                person_in_charge,
                speaker,
                FQA
            )
        }


}