package com.icyrockton.school_app.fragment.score.overview

data class ScoreWrapper(
    val scoreList:ArrayList<ScoreDetail>,
    val scoreRatio: ScoreRatio,
    val scoreAverage: ScoreAverage
)