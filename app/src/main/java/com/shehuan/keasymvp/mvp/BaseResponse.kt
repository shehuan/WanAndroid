package com.shehuan.keasymvp.mvp

data class BaseResponse<T>(var errorMsg: String, var errorCode: String, var data: T)