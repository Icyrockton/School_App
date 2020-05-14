package com.icyrockton.school_app.fragment.email

data class SendEmail(
    val message_ID:String,//信息ID

    val type:String,//类型
    val recipient:String,//收件人
    val title:String,//标题
    val date:String,//时间
    val read:Boolean,//已读
    val attachment:String?//附件下载地址

)