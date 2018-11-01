package com.shehuan.wanandroid.ui.collection

import com.shehuan.wanandroid.base.BaseView
import com.shehuan.wanandroid.base.net.exception.ResponseException

interface CollectContract {
    interface View : BaseView {
        fun onCollectSuccess(data: String)
        fun onCollectError(e: ResponseException)

        fun onUncollectSuccess(data: String)
        fun onUncollectError(e: ResponseException)
    }

    interface Presenter {
        fun collect(id: Int)

        fun uncollect(id: Int)
    }
}