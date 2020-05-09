package com.icyrockton.school_app.fragment.second_class


data class SecondClassScoreInfo(
    val course_name:String,
    val semester:String,
    val course_category:String,//课程类别
    val confirm_credit:String,//认定学时
    val score:String//成绩
)