package com.icyrockton.school_app.fragment.score.overview

/**
 *    <string name="order_submitDate_asc">提交时间升序</string>
    <string name="order_submitDate_desc">提交时间降序</string>
    <string name="order_termValue">课程选修时间</string>
    <string name="order_courseName">课程名称</string>
    <string name="order_courseCode">课程代码</string>
 */
interface OrderTypeHandler {
    fun orderSubmitDateAsc()
    fun orderSubmitDateDesc()
    fun orderTermValue()
    fun orderCourseName()
    fun orderCourseCode()
}