package com.icyrockton.school_app.fragment.score.overview

data class ScoreDetail(
    val code:String,//代码
    val course_name:String,//课程名称
    val class_number:String,//班号
    val property:String,//性质 必修/选修
    val score:String,//成绩
    val final_exam:String,//期末
    val daily_score:String,//平时
    val credit:String,//学分
    val teacher_name:String,//教师
    val academic_year:String,//学年
    val semester:String//学期
)