package com.icyrockton.school_app.fragment.main

import android.util.Log
import com.icyrockton.school_app.network.NetworkAPI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup
import org.jsoup.parser.Parser

class MainRepository(private val networkAPI: NetworkAPI) {

    suspend fun getStuInfo():MainStuInfo= withContext(Dispatchers.IO){
        val frameWorkResponse = networkAPI.getUserFramework()
        val frameWorkDocument = Jsoup.parse(frameWorkResponse.string())
        val stuInfoTable = frameWorkDocument.getElementsByClass("infoTb")[0].selectFirst("tbody").selectFirst("tr")
        val rows = stuInfoTable.select("td")
        val stuID=rows[0].text()//ID
        val stuName=rows[1].text()//姓名

        val userInfoResponse = networkAPI.getUserInfo()

        //xml解析
        val userInfoDocument = Jsoup.parse(userInfoResponse.string(),"", Parser.xmlParser())
        val imgUrl = "http://jwc.swjtu.edu.cn${userInfoDocument.selectFirst("userImg").text()}"

        return@withContext MainStuInfo(stuID,stuName,imgUrl)
    }
    companion object{
        private const val TAG = "MainRepository"
    }
}