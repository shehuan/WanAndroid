package com.shehuan.wanandroid.ui.project

import com.shehuan.wanandroid.apis.WanAndroidApis
import com.shehuan.wanandroid.base.BasePresenter
import com.shehuan.wanandroid.base.net.RequestManager
import com.shehuan.wanandroid.base.net.RetrofitManager
import com.shehuan.wanandroid.base.net.exception.ResponseException
import com.shehuan.wanandroid.base.net.observer.BaseObserver
import com.shehuan.wanandroid.bean.ProjectCategoryBean

class ProjectPresenterImpl(view: ProjectContract.View) : BasePresenter<ProjectContract.View>(view), ProjectContract.Presenter {
    override fun getProjectCategory() {
        RequestManager.execute(this, RetrofitManager.create(WanAndroidApis::class.java).projectCategory(),
                object : BaseObserver<List<ProjectCategoryBean>>(true) {
                    override fun onSuccess(data: List<ProjectCategoryBean>) {
                        view.onProjectCategorySuccess(data)
                    }

                    override fun onError(e: ResponseException) {
                        view.onProjectCategoryError(e)
                    }
                })
    }
}