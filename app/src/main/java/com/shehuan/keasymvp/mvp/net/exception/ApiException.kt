package com.shehuan.keasymvp.mvp.net.exception

class ApiException(private var errorCode: String, errorMessage: String) : RuntimeException(errorMessage) {

    fun getErrorCode(): String {
        return errorCode
    }

    fun setErrorCode(errorCode: String) {
        this.errorCode = errorCode
    }
}