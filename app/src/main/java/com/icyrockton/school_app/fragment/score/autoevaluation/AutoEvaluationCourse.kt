package com.icyrockton.school_app.fragment.score.autoevaluation

data class AutoEvaluationCourse(
    val courseID:String,  //选课编号
    val courseName:String, //课程名称
    val sid:String,
    val lid:String,
    val templateFlag:Int=0
)