package com.icyrockton.school_app.fragment.score.daily


data class DailyScoreWrapper(
    var code:String,//代码
    var course_name:String,//课程名称
    var class_number:String,//班号
    var teacher_name:String,//教师
    var data:List<DailyScore>,
    var summary:String//成绩总结
)