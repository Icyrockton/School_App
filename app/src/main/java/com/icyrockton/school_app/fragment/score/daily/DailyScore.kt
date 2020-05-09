package com.icyrockton.school_app.fragment.score.daily

data class DailyScore(
    val score_name: String,//平时成绩名称
    val proportion: String,//占比
    val score: String,//成绩
    val submit_date: String//提交时间
)