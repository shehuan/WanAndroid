package com.shehuan.wanandroid.ui.project

import com.shehuan.wanandroid.apis.WanAndroidApis
import com.shehuan.wanandroid.base.BasePresenter
import com.shehuan.wanandroid.base.net.RequestManager
import com.shehuan.wanandroid.base.net.RetrofitManager
import com.shehuan.wanandroid.base.net.exception.ResponseException
import com.shehuan.wanandroid.base.net.observer.BaseObserver
import com.shehuan.wanandroid.bean.project.ProjectBean

class ProjectPresenterImpl(view: ProjectContract.View) : BasePresenter<ProjectContract.View>(view), ProjectContract.Presenter {
    override fun getProjectList(pageNum: Int) {
        RequestManager.execute(this, RetrofitManager.create(WanAndroidApis::class.java).project(pageNum),
                object : BaseObserver<ProjectBean>(true) {
                    override fun onSuccess(data: ProjectBean) {
                        view.onProjectListSuccess(data)
                    }

                    override fun onError(e: ResponseException) {
                        view.onProjectListError(e)
                    }
                })
    }
}