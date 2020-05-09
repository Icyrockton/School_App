package com.icyrockton.school_app.fragment.second_class

import android.util.Log
import com.icyrockton.school_app.network.NetworkAPI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import org.jsoup.parser.Parser

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
            val startIndex = 3
            val img_url = "http://jwc.swjtu.edu.cn/${img_div.substring(startIndex, endIndex)}.jpg"

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
            if (intro.size >= 2) {
                val raw_arrangement = intro[1].select("p")
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

    suspend fun getSecondClassInfoItem(courseID: String, delete_ID: String) =
        withContext(Dispatchers.IO) {
            val response = networkAPI.getSecondClassRecord(courseID)
            val tds = Jsoup.parse(response.string()).select("td")
            val img_url =
                "http://jwc.swjtu.edu.cn/${tds[2].selectFirst("img").attr("src").substring(3)}"
            var activity_arrangement = ""
            tds[32].select("p").forEach {
                if (it.text().isNotEmpty())
                    activity_arrangement += it.text() + "\n"
            }
            var course_introduction = ""
            tds[33].select("p").forEach {
                if (it.text().isNotEmpty())
                    course_introduction += it.text() + "\n"
            }
            var course_summary = ""
            tds[34].select("p").forEach {
                if (it.text().isNotEmpty())
                    course_summary += it.text() + "\n"
            }
            return@withContext SecondClassSelectedInfo(
                courseID,
                delete_ID,
                img_url,
                tds[0].text(),
                tds[1].text(),
                tds[3].text(),
                tds[4].text(),
                tds[5].text(),
                tds[6].text(),
                tds[7].text(),
                tds[8].text(),
                tds[9].text(),
                tds[10].text(),
                tds[11].text(),
                tds[12].text(),
                tds[13].text(),
                tds[14].text(),
                tds[15].text(),
                tds[16].text(),
                tds[17].text(),
                tds[18].text(),
                tds[19].text(),
                tds[20].text(),
                tds[21].text(),
                tds[22].text(),
                tds[23].text(),
                tds[24].text(),
                tds[25].text(),
                tds[26].text(),
                tds[27].text(),
                tds[28].text(),
                tds[29].text(),
                tds[30].text(),
                tds[31].text(),
                activity_arrangement,
                course_introduction,
                course_summary,
                tds[35].text()

            )
        }

    //获取已选择的所有课程
    suspend fun getSelectedClassInfo(): List<SecondClassSelectedInfo> =
        withContext(Dispatchers.IO) {
            val response = networkAPI.getSelectedClassInfo()
            val trs = Jsoup.parse(response.string()).getElementById("stb").select("tr")
            val list = mutableListOf<SecondClassSelectedInfo>()
            trs.forEach {
                val tds = trs.select("td")
                val ID = parseID(tds[1].select("span")[0].attr("onclick"))
                val deleteID = parseID(tds[8].select("input").attr("onclick"))
                list.add(getSecondClassInfoItem(ID, deleteID))
            }
            return@withContext list
        }


    //删除第二课堂课程
    suspend fun deleteSecondClass(delete_ID: String) = withContext(Dispatchers.IO) {
        return@withContext networkAPI.deleteSecondClass(delete_ID)
    }

    //获取学期信息
    suspend fun getTermInfo(): List<SecondClassTermInfo> = withContext(Dispatchers.IO) {
        val response = networkAPI.getTermInfo()
        val document = Jsoup.parse(response.string(), "", Parser.xmlParser())
        val names = document.select("term_name")
        val ids = document.select("term_id")
        val list = mutableListOf<SecondClassTermInfo>()
        names.zip(ids).forEach {
            list.add(SecondClassTermInfo(it.first.text().trim(), it.second.text().trim()))
        }
        return@withContext list
    }


    //获取第二课堂成绩
    suspend fun getScore(term_name: String, term_id: String) = withContext(Dispatchers.IO) {
        val response = networkAPI.getSecondClassScore(term_name, term_id)
        val trs = Jsoup.parse(response.string()).select("stb").select("tr")
        val list = mutableListOf<SecondClassScoreInfo>()
        trs.forEach {
            list.add(parseScore(it))
        }

        return@withContext list
    }

    private fun parseScore(row: Element): SecondClassScoreInfo {
        val tds = row.select("td")
        val spans = tds[1].select("span")
        val course_name = spans[0].text().trim()
        val semester = spans[2].text().trim()
        val course_category = tds[3].text().trim()
        val confirm_credit = tds[6].text().trim()
        val score = tds[7].text().trim()
        return SecondClassScoreInfo(course_name, semester, course_category, confirm_credit, score)
    }


    suspend fun getImageURL(courseID: String)= withContext(Dispatchers.IO){
        val response = networkAPI.getSecondClassDetailInfo(courseID)
        val document = Jsoup.parse(response.string())
        val basicInfo = document.select(".classDetailGray")

        val img_div = document.select(".leftImg").select("img")[0].attr("src")
        val endIndex = img_div.indexOf("_big")
        val startIndex = 3
        return@withContext "http://jwc.swjtu.edu.cn/${img_div.substring(startIndex, endIndex)}.jpg"
    }


    //历史选课信息
    suspend fun getHistoryInfo()= withContext(Dispatchers.IO){
        val response= networkAPI.getHistorySecondClass()

        val trs = Jsoup.parse(response.string()).getElementById("stb").select("tr")

        val list= mutableListOf<SecondClassHistory>()
        trs.forEach {
            list.add(parseHistory(it))
        }

        return@withContext list
    }

    private suspend fun parseHistory(row: Element): SecondClassHistory = withContext(Dispatchers.IO) {
        val tds = row.select("td")
        val span = tds[1].select("span")[0]
        val course_name=span.text().trim()
        val img_url=getImageURL(parseID(span.attr("onclick")))
        val credit=tds[2].select("span")[1].text().trim()
        return@withContext SecondClassHistory(course_name, img_url, credit)
    }
}