package com.shehuan.keasymvp.base.net.convert

import com.shehuan.keasymvp.base.BaseResponse
import com.shehuan.keasymvp.base.net.exception.ApiException
import io.reactivex.functions.Function

class ResponseConvert<E> : Function<BaseResponse<E>, E> {
    override fun apply(baseResponse: BaseResponse<E>): E {
        if ("0" != baseResponse.errorCode) {
            throw ApiException(baseResponse.errorCode, baseResponse.errorMsg)
        }

        return baseResponse.data
    }
}