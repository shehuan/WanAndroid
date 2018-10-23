package com.shehuan.keasymvp.base

data class BaseResponse<T>(val errorMsg: String, val errorCode: String, val data: T)