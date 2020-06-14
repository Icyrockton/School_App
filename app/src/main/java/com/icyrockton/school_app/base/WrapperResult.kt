package com.icyrockton.school_app.base

data class WrapperResult<out T>(
    val data: T?,
    val networkType: NetworkType,
    val networkMsg: Exception?=null
) {

    companion object {
        fun <T> done(data: T) = WrapperResult(data, NetworkType.DONE)
        fun error(errorException: Exception) = WrapperResult(
            null,
            NetworkType.ERROR, errorException
        )
        val  loading = WrapperResult(null, NetworkType.LOADING)
    }

}

enum class NetworkType {
    LOADING,
    ERROR,
    DONE
}