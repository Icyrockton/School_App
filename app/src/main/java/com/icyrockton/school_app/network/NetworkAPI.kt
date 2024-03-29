package com.icyrockton.school_app.network

import com.icyrockton.school_app.fragment.login.LoginResult
import com.icyrockton.school_app.fragment.second_class.SecondClassDeleteResult
import com.icyrockton.school_app.fragment.second_class.SecondClassPostResult
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface NetworkAPI {
    @GET("service/login.html")
    suspend fun getLoginSESSION(): ResponseBody

    @GET("vatuu/GetRandomNumberToJPEG")
    suspend fun getVerifyCodeImage(@Query("test") currentTime: Long): ResponseBody

    @FormUrlEncoded
    @Headers("Referer: http://jwc.swjtu.edu.cn/service/login.html")
    @POST("vatuu/UserLoginAction")
    suspend fun login(
        @Field("username") userID: String,
        @Field("password") userPassword: String,
        @Field("ranstring") verifyCode: String,
        @Field("url") url: String = "",
        @Field("returnUrl") returnUrl: String = "",
        @Field("area") area: String = ""
    ): LoginResult

    @FormUrlEncoded
    @Headers("Referer: http://jwc.swjtu.edu.cn/service/login.html")
    @POST("vatuu/UserLoadingAction")
    suspend fun afterLogin(
        @Field("url") url: String = "",
        @Field("returnUrl") returnUrl: String = "",
        @Field("loginMsg") loginMsg: String = ""
    ): ResponseBody

    @GET("vatuu/StudentScoreInfoAction")
    suspend fun getAllScore(//查询所有成绩
        @Query("orderType") orderType: String, //排序类型
        @Query("setAction") action: String = "studentScoreQuery",
        @Query("viewType") viewType: String = "studentScore"
    ): ResponseBody


    @GET("vatuu/StudentScoreInfoAction")
    suspend fun getAllScoreByDate(//按时间顺序查询
        @Query("orderValue") orderValue: String = "desc",
        @Query("orderType") orderType: String = "submitDate",
        @Query("setAction") action: String = "studentScoreQuery",
        @Query("viewType") viewType: String = "studentScore"
    ): ResponseBody


    @GET("vatuu/StudentScoreInfoAction")
    suspend fun getDailyScore(//获取平时成绩
        @Query("setAction") action: String = "studentNormalMark"
    ): ResponseBody


    @GET("vatuu/StudentScoreInfoAction")
    suspend fun getCETScore(//获取4,6级成绩
        @Query("setAction") action: String = "studentCETScore"
    ): ResponseBody


    @GET("vatuu/UserFramework")
    suspend fun getUserFramework(): ResponseBody//解析账号 姓名


    @GET("vatuu/UserInfoSetAction?setAction=queryUserAccountInfo")
    suspend fun getUserInfo(): ResponseBody//获取头像地址

    @GET("vatuu/StudentInfoAction?setAction=studentInfoQuery")
    suspend fun getStudentInfo(): ResponseBody //获取学籍信息

    @GET("vatuu/StudentInfoAction?setAction=studentParentInfo")
    suspend fun getStudentParentInfo(): ResponseBody //获取学籍信息


    @GET("vatuu/YouthInfoAction?setAction=youthCourseRecord")
    suspend fun getSecondClassInfo(): ResponseBody //获取第二课堂信息

    @FormUrlEncoded
    @POST("vatuu/YouthInfoSetAction?setAction=addApply")
    suspend fun postSecondClass(
        @Field("course_id") course_id: String,
        @Field("apply_type") apply_type: String   //主办方,参与者
    ): SecondClassPostResult   //报名

    @GET("vatuu/YouthIndexAction")
    suspend fun getSecondClassDetailInfo(
        @Query("course_id") ID: String
        , @Query("setAction") action: String = "courseInfo"
    ): ResponseBody  //获取第二课堂详细信息


    @GET("vatuu/YouthInfoAction")
    suspend fun getSecondClassRecord(
        @Query("course_id") course_id: String,
        @Query("setAction") action: String = "dealYouthCourseRecord",
        @Query("viewType") viewType: String = "show"
    ): ResponseBody

    @GET("vatuu/YouthInfoAction?setAction=youthCourseStudentList")
    suspend fun getSelectedClassInfo(
    ): ResponseBody


    @GET("vatuu/YouthInfoSetAction")
    suspend fun deleteSecondClass(
        @Query("list_id") list_id: String,
        @Query("setAction") action: String = "deleteApply"
    ): SecondClassDeleteResult


    @GET("vatuu/AjaxXML?selectType=TermInfo&selectValue=termNormal")
    suspend fun getTermInfo(): ResponseBody


    @FormUrlEncoded
    @Headers(
        "Accept-Language: zh-CN,zh;q=0.9,en;q=0.8,en-GB;q=0.7,en-US;q=0.6"  //不加此header 没有权限
    )
    @POST("vatuu/YouthInfoAction?setAction=youthCourseStudentScore")
    suspend fun getSecondClassScore( //获取第二课堂成绩
        @Field("term_name") term_name: String,
        @Field("term_id") term_ID: String
    ): ResponseBody


    @GET("vatuu/YouthInfoAction?setAction=youthCourseStudentListHistory")
    suspend fun getHistorySecondClass(//历史选课
    ): ResponseBody

    @GET("vatuu/YouthStudentStatsAction?setAction=studentData")
    suspend fun getDistribution(): ResponseBody


    @GET("vatuu/WebMessageInfoAction?setAction=queryMyMessage&viewType=received")
    suspend fun getEmail(): ResponseBody //获取所有邮件信息


    @GET("vatuu/WebMessageInfoAction?setAction=queryMyMessage&viewType=sent")
    suspend fun getSendEmail(): ResponseBody //获取所有发送的邮件信息


    @GET("vatuu/WebMessageInfoAction")
    suspend fun getEmailDetail(
        @Query("sid") message_ID: String,
        @Query("setAction") action: String = "messageInfo",
        @Query("viewType") viewType: String = "reply"
    ): ResponseBody

    @GET("vatuu/AssessAction?setAction=list")
    suspend fun getAllNeedEvaluationCourse(): ResponseBody //获取所有的需要评价的课程

    @FormUrlEncoded
    @POST("vatuu/AssessAction")
    @Headers(
        "Accept: */*",
        "Accept-Encoding: gzip, deflate",
        "Accept-Language: zh-CN,zh;q=0.9",
        "Origin: http://jwc.swjtu.edu.cn"
    )
    suspend fun postEvaluationCourse(   //提交
        @Header("Referer") referer:String,
        @Field("answer") answer: String,
        @Field("assess_id") assess_id: String,
        @Field("id") id: String,
        @Field("logId") logId: String,
        @Field("scores")scores:String,
        @Field("percents") percents: String,
        @Field("t") t: String=Math.random().toString(),//默认值
        @Field("keyword") keyword: String = "null",//默认值
        @Field("teacherId") teacherId: String = "",//默认值
        @Field("setAction") setAction: String = "answerStudent",//默认值
        @Field("templateFlag") templateFlag: String = "0"//默认值
    ): ResponseBody

    @GET("vatuu/AssessAction")
    suspend fun getNeedEvaluationCourse(   //获取具体的需要评价的课程
        @Query("sid") sid: String,
        @Query("lid") lid: String,
        @Query("templateFlag") templateFlag: String = "0",
        @Query("setAction") action: String = "viewAssess"
    ):ResponseBody
}