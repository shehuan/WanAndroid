package com.shehuan.wanandroid.ui.project

import com.shehuan.wanandroid.apis.WanAndroidApis
import com.shehuan.wanandroid.base.BasePresenter
import com.shehuan.wanandroid.base.net.RequestManager
import com.shehuan.wanandroid.base.net.RetrofitManager
import com.shehuan.wanandroid.base.net.exception.ResponseException
import com.shehuan.wanandroid.base.net.observer.BaseObserver
import com.shehuan.wanandroid.bean.newProject.NewProjectBean

class ProjectPresenterImpl(view: ProjectContract.View) : BasePresenter<ProjectContract.View>(view), ProjectContract.Presenter {
    override fun getProject(pageNum: Int) {
        RequestManager.execute(this, RetrofitManager.create(WanAndroidApis::class.java).project(pageNum),
                object : BaseObserver<NewProjectBean>(true) {
                    override fun onSuccess(data: NewProjectBean) {
                        view.onProjectSuccess(data)
                    }

                    override fun onError(e: ResponseException) {
                        view.onProjectError(e)
                    }
                })
    }
}