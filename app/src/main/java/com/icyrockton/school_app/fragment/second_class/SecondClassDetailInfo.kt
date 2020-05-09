package com.icyrockton.school_app.fragment.second_class

data class SecondClassDetailInfo(
    val ID:String,//跳转ID
    val img_url:String,//图片url
    val date:String,//课程时间
    val credit:String,//课程学时
    val total_capacity:String,//总容量
    val selected_capacity:String,//已选容量
    val course_category:String,//课程类别    社会实践与志愿服务/学术科技与创新创业/社会工作与领导能力....
    val location:String,//课程地点
    val campus:String,//选课校区
    val target:String,//面向对象
    val teaching_method:String,//授课方式
    val registration_time:String,//报名时间
    val credit_explain: String,//学时说明
    val course_introduction:String,//课程介绍
    val activity_arrangement:String,//活动安排
    val person_in_charge:String,//负责人
    val speaker:String,//主讲人
    val FQA:String//常见问题

)