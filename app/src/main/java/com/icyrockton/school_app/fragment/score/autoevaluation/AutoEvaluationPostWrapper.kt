package com.icyrockton.school_app.fragment.score.autoevaluation


data class AutoEvaluationPostWrapper(
    val sid:String,
    val lid:String,
    val answer:String,
    val scores:String,
    val percents:String,
    val id:String,
    val assess_id:String
)