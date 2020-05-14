package com.icyrockton.school_app.fragment.email

import com.icyrockton.school_app.network.NetworkAPI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup
import org.jsoup.select.Elements

class EmailRepository(private val networkAPI: NetworkAPI) {

    suspend fun getAllEmail()= withContext(Dispatchers.IO){
        val response = networkAPI.getEmail()
        //todo 分页 删除 enhancement
        val trs = Jsoup.parse(response.string()).getElementById("stb").select("tr")
        val list= mutableListOf<Email>()
        for (tr in trs) {
            val tds = tr.select("td")
            //是否读取过
            val read= tds[1].selectFirst("img").attr("img").indexOf("icon_3")!=-1
            val type=tds[2].text().trim()
            val sender=tds[3].text().trim()
            val title=tds[4].selectFirst("span").text().trim()
            val date=tds[5].text().trim()
            val message_ID=parseEmailID(tds[4].attr("onclick"))
            list.add(Email(message_ID, type, sender, title, date, read))
        }
        return@withContext list
    }
    suspend fun getSendEmail()= withContext(Dispatchers.IO){
        val response = networkAPI.getSendEmail()
        //todo 分页 删除 enhancement
        val trs = Jsoup.parse(response.string()).getElementById("stb").select("tr")
        val list= mutableListOf<SendEmail>()
        for (tr in trs) {
            val tds = tr.select("td")
            if (tds.size==1) //补齐行
                break
            val read= tds[1].selectFirst("img").attr("src").indexOf("icon_3")!=-1
            var attachment:String?=null
            if (tds[1].childrenSize()>1){//有附件下载地址
                attachment="http://jwc.swjtu.edu.cn/${tds[1].selectFirst("a").attr("href").substring(4)}"
            }
            val type=tds[2].text().trim()
            val recipient=tds[3].text().trim()
            val title=tds[4].selectFirst("span").text().trim()
            val date=tds[5].text().trim()
            val message_ID=parseEmailID(tds[4].selectFirst("span").attr("onclick"))
            list.add(SendEmail(message_ID,type, recipient, title, date, read, attachment))
        }
        return@withContext list
    }
    private fun parseEmailID(s: String):String {
        val startIndex = s.indexOf('\'')+1
        val endIndex = s.indexOf('\'', startIndex)
        return s.substring(startIndex,endIndex)
    }

}