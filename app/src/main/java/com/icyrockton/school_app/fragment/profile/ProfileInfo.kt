package com.icyrockton.school_app.fragment.profile

data class ProfileInfo(
    val student_ID:String,
    val student_name:String,
    val passport_name:String,
    val gender:String, //学生性别
    val birth_date:String, //出生年月
    val student_state:String,//学籍状态
    val institute:String, //专业学院
    val grade:String,
    val major:String,
    val class_name:String,
    val national_major:String,
    val campus:String,//校区
    val a:String,//行政学院
    val b:String,//行政班级
    val native_place:String,//籍贯
    val national:String,//名族
    val political_status:String,//政治面貌
    val ID_number:String,//身份证
    val examinee_number:String,//考生号
    val travel_range:String,//乘车区间
    val province:String,//省份
    val city:String,//城市
    val phone_number:String,//联系电话
    val home_address:String,//家庭住址
    val student_source:String,//生源地
    val graduated_school:String,//毕业学校
    val student_category:String,//考生类别
    val admission_form:String,//录取形式
    val admission_source:String,//录取来源
    val subject:String,//录取科类
    val double_major:String,//双学位
    val student_tag:String//学生标记



)