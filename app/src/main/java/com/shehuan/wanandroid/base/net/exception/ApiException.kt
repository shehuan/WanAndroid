package com.shehuan.wanandroid.base.net.exception

class ApiException(val errorCode: String, errorMessage: String) : RuntimeException(errorMessage)