package com.icyrockton.school_app.fragment.second_class



data class SecondClassDistributionWrapper(
    val data:Map<String,MutableList<SecondClassDistribution>>,
    val total_credit:String//总学时
)