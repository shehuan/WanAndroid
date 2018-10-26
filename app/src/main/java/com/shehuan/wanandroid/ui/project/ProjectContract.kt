package com.shehuan.wanandroid.ui.project

import com.shehuan.wanandroid.base.BaseView
import com.shehuan.wanandroid.base.net.exception.ResponseException
import com.shehuan.wanandroid.bean.project.ProjectBean

interface ProjectContract {
    interface View : BaseView {
        fun onProjectListSuccess(data: ProjectBean)
        fun onProjectListError(e: ResponseException)
    }

    interface Presenter {
        fun getProjectList(pageNum: Int)
    }
}