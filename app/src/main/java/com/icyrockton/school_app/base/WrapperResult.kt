package com.icyrockton.school_app.base

data class WrapperResult<out T>(
    val data: T?,
    val networkType: NetworkType,
    val networkMsg: String = ""
) {

    companion object {
        fun <T> done(data: T) = WrapperResult(data, NetworkType.DONE)
        fun error(errorMsg: String) = WrapperResult(
            null,
            NetworkType.ERROR, errorMsg
        )
        val  loading = WrapperResult(null, NetworkType.LOADING)
    }

}

enum class NetworkType {
    LOADING,
    ERROR,
    DONE
}