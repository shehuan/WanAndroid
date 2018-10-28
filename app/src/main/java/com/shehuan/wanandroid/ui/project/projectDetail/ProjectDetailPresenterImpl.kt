package com.shehuan.wanandroid.ui.project.projectDetail

import com.shehuan.wanandroid.apis.WanAndroidApis
import com.shehuan.wanandroid.base.BasePresenter
import com.shehuan.wanandroid.base.net.RequestManager
import com.shehuan.wanandroid.base.net.RetrofitManager
import com.shehuan.wanandroid.base.net.exception.ResponseException
import com.shehuan.wanandroid.base.net.observer.BaseObserver
import com.shehuan.wanandroid.bean.project.ProjectBean

class ProjectDetailPresenterImpl(view: ProjectDetailContract.View) : BasePresenter<ProjectDetailContract.View>(view), ProjectDetailContract.Presenter {
    override fun getProjectDetail(pageNum: Int, cid: Int) {
        RequestManager.execute(this, RetrofitManager.create(WanAndroidApis::class.java).projectDetail(pageNum, cid),
                object : BaseObserver<ProjectBean>(true) {
                    override fun onSuccess(data: ProjectBean) {
                        view.onProjectDetailSuccess(data)
                    }

                    override fun onError(e: ResponseException) {
                        view.onProjectDetailError(e)
                    }
                })
    }

    override fun getNewProjectList(pageNum: Int) {
        RequestManager.execute(this, RetrofitManager.create(WanAndroidApis::class.java).newProject(pageNum),
                object : BaseObserver<ProjectBean>(true) {
                    override fun onSuccess(data: ProjectBean) {
                        view.onNewProjectListSuccess(data)
                    }

                    override fun onError(e: ResponseException) {
                        view.onNewProjectListError(e)
                    }
                })
    }
}