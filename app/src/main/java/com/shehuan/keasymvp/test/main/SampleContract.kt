package com.shehuan.keasymvp.test.main

import com.shehuan.keasymvp.test.bean.BannerBean
import com.shehuan.keasymvp.mvp.BaseView
import com.shehuan.keasymvp.mvp.net.exception.ResponseException

interface SampleContract {
    interface View : BaseView {
        fun onBannerSuccess(data: List<BannerBean>)

        fun onBannerError(e: ResponseException)
    }

    interface Presenter {
        fun getBannerData()
    }
}