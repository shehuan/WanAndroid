package com.shehuan.keasymvp.test.apis

import com.shehuan.keasymvp.test.bean.BannerBean
import com.shehuan.keasymvp.mvp.BaseResponse
import com.shehuan.keasymvp.test.bean.FriendBean
import io.reactivex.Observable
import retrofit2.http.GET

interface WanAndroidApis {

    @GET("banner/json")
    fun banner(): Observable<BaseResponse<List<BannerBean>>>

    @GET("friend/json")
    fun friend(): Observable<BaseResponse<List<FriendBean>>>
}