package com.shehuan.keasymvp.base.net.exception

class ApiException(val errorCode: String, errorMessage: String) : RuntimeException(errorMessage)