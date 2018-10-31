package com.shehuan.wanandroid.ui.website

import com.shehuan.wanandroid.base.BaseView
import com.shehuan.wanandroid.base.net.exception.ResponseException
import com.shehuan.wanandroid.bean.FriendBean

interface HotWebsiteContract {
    interface View : BaseView {
        fun onFriedSuccess(data: List<FriendBean>)
        fun onFriendError(e: ResponseException)
    }

    interface Presenter {
        fun getFriendData()
    }
}