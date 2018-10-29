package com.shehuan.wanandroid.ui.main

import com.shehuan.wanandroid.base.BasePresenter
import com.shehuan.wanandroid.base.net.RequestManager
import com.shehuan.wanandroid.base.net.RetrofitManager
import com.shehuan.wanandroid.base.net.exception.ResponseException
import com.shehuan.wanandroid.base.net.observer.LoadingObserver
import com.shehuan.wanandroid.apis.WanAndroidApis
import com.shehuan.wanandroid.bean.FriendBean

class MainPresenterImpl(view: MainContract.View) : BasePresenter<MainContract.View>(view), MainContract.Presenter {
    override fun getFriendData() {
        RequestManager.execute(this, RetrofitManager.create(WanAndroidApis::class.java).friend(),
                object : LoadingObserver<List<FriendBean>>(context, false, true) {
                    override fun onSuccess(data: List<FriendBean>) {
                        view.onFriedSuccess(data)
                    }

                    override fun onError(e: ResponseException) {
                        view.onFriendError(e)
                    }
                })
    }
}