package com.icyrockton.school_app.fragment.score.overview

data class ScoreRatio (
    var excellent: Int=0,//90-100 优秀
    var good: Int=0,//80-90 良好
    var pass: Int=0,//60-80 及格
    var fail: Int=0//0-60 不及格
)


data class ScoreAverage(
    var totalScore:Float,
    var count :Int,
    var average:Float
)