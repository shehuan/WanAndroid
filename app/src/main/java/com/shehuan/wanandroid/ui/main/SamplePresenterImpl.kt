package com.shehuan.wanandroid.ui.main

import com.shehuan.wanandroid.base.BasePresenter
import com.shehuan.wanandroid.base.net.observer.BaseObserver
import com.shehuan.wanandroid.base.net.RequestManager
import com.shehuan.wanandroid.base.net.RetrofitManager
import com.shehuan.wanandroid.base.net.exception.ResponseException
import com.shehuan.wanandroid.base.net.observer.LoadingObserver
import com.shehuan.wanandroid.apis.WanAndroidApis
import com.shehuan.wanandroid.bean.FriendBean
import com.shehuan.wanandroid.bean.newProject.NewProjectBean

class SamplePresenterImpl(view: SampleContract.View) : BasePresenter<SampleContract.View>(view), SampleContract.Presenter {
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

    override fun getNewProject(pageNum: Int) {
        RequestManager.execute(this, RetrofitManager.create(WanAndroidApis::class.java).newProject(pageNum),
                object : BaseObserver<NewProjectBean>(true) {
                    override fun onSuccess(data: NewProjectBean) {
                        view.onNewProjectSuccess(data)
                    }

                    override fun onError(e: ResponseException) {
                        view.onNewProjectError(e)
                    }
                })
    }
}