package com.icyrockton.school_app.fragment.second_class

data class SecondClassSelectedInfo (
    val ID:String,//跳转ID
    val delete_ID:String,//删除ID
    val img_url:String,//图像地址
    val course_level:String,//课程级别
    val semester:String,//学期
    val teaching_method:String,//授课方式
    val institute:String,//开学单位
    val course_name:String,//课程名称
    val course_ID:String,//选课编号
    val course_category:String,//课程类别
    val course_category_fine:String,//课程小类
    val  credit:String,//课程学时
    val course_hour:String,//课程学分
    val max_credit:String,//课程最大学时
    val total_capacity:String,//课程容量
    val start_selection_time:String,
    val end_selection_time:String,
    val drop_time:String,
    val first_class_time:String,
    val association:String,
    val more_class_time :String,
    val campus:String,
    val location:String,
    val campus_restrictions :String,
    val institute_restrictions:String ,
    val grade_restrictions:String,
    val major_restrictions:String,
    val funds:String,
    val teacher_phone:String,
    val teacher_name:String,
    val second_phone:String,
    val speaker:String,
    val office_location:String,
    val speaker_introduction:String,
    val activity_arrangement:String,//活动安排
    val course_introduction:String,//课程介绍
    val course_summary:String,//
    val remarks:String

)