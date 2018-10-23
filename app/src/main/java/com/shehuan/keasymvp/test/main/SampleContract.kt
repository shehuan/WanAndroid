package com.shehuan.keasymvp.test.main

import com.shehuan.keasymvp.test.bean.BannerBean
import com.shehuan.keasymvp.base.BaseView
import com.shehuan.keasymvp.base.net.exception.ResponseException
import com.shehuan.keasymvp.test.bean.FriendBean

interface SampleContract {
    interface View : BaseView {
        fun onBannerSuccess(data: List<BannerBean>)
        fun onBannerError(e: ResponseException)

        fun onFriedSuccess(data: List<FriendBean>)
        fun onFriendError(e: ResponseException)
    }

    interface Presenter {
        fun getBannerData()

        fun getFriendData()
    }
}