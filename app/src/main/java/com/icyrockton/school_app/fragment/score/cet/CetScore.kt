package com.icyrockton.school_app.fragment.score.cet


data class CetScore(
    val exam_name: String,//考试名称
    val ID: String,//准考证号
    val exam_date: String,//考试时间
    val campus: String,//校区
    val listening_score: String,//听力
    val comprehensive_score: String,//综合
    val read_score: String,//阅读
    val composition_score: String,//作文
    val score: String //总分
)