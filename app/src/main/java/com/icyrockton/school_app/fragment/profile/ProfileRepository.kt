package com.icyrockton.school_app.fragment.profile

import android.util.Log
import com.icyrockton.school_app.network.NetworkAPI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup

class ProfileRepository(private val networkAPI: NetworkAPI) {
    suspend fun getProfileInfo() = withContext(Dispatchers.IO) {
        val response = networkAPI.getStudentInfo()
        val tableBody = Jsoup.parse(response.string()).getElementById("table4").selectFirst("tbody")


        
        val td = tableBody.select("td")
        return@withContext ProfileInfo(
            td[2].text(),
            td[4].text(),
            td[7].text(),
            td[9].text(),
            td[11].text(),
            td[13].text(),//
            td[15].text(),
            td[17].text(),
            td[19].text(),
            td[21].text(),
            td[23].text(),
            td[25].text(),
            td[27].text(),
            td[29].text(),
            td[31].text(),
            td[33].text(),
            td[35].text(),
            td[37].text(),
            td[39].text(),
            td[41].text(),
            td[43].text(),
            td[45].text(),
            td[47].text(),
            td[49].text(),
            td[51].text(),
            td[53].text(),
            td[55].text(),
            td[57].text(),
            td[59].text(),
            td[61].text(),
            td[63].text(),
            td[65].text()

            )
    }

    companion object {
        private const val TAG = "ProfileRepository"
    }

    suspend fun getGuardianInfo(): GuardianInfo = withContext(Dispatchers.IO) {

        val response = networkAPI.getStudentParentInfo()
        val tableBody = Jsoup.parse(response.string()).getElementById("table4").selectFirst("tbody")
        val td = tableBody.select("td")
        return@withContext GuardianInfo(
            td[3].text(),
            td[5].text(),
            td[7].text(),
            td[9].text(),
            td[11].text(),
            td[13].text(),
            td[15].text(),
            td[17].text(),
            td[19].text(),
            td[21].text(),
            td[23].text(),
            td[25].text()
        )
    }
}