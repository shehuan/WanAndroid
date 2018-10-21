package com.shehuan.keasymvp.mvp.net.convert

import com.shehuan.keasymvp.mvp.BaseResponse
import com.shehuan.keasymvp.mvp.net.exception.ApiException
import io.reactivex.functions.Function

class ResponseConvert<E> : Function<BaseResponse<E>, E> {
    override fun apply(baseResponse: BaseResponse<E>): E {
        if ("0" != baseResponse.errorCode) {
            throw ApiException(baseResponse.errorCode, baseResponse.errorMsg)
        }

        return baseResponse.data
    }
}