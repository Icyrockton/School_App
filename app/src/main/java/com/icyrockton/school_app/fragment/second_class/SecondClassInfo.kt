package com.icyrockton.school_app.fragment.second_class

data class SecondClassInfo(
    val ID:String,//跳转ID
    val course_name:String, //课程名称
    val credit:String,//学时
    val max_credit:String,//最大学时
    val course_category:String,//课程类别    社会实践与志愿服务/学术科技与创新创业/社会工作与领导能力....
    val semester:String,//学期
    val course_ID:String,//选课编号
    val institute:String,//开课学院
    val teacher:String,//老师
    val date:String,//上课地点
    val location:String,//上课地点
    val total_capacity:String,//总容量
    val selected_capacity:String,//已选容量
    val isEnabled:Boolean,//是否可以选
    val hintMsg:String
)