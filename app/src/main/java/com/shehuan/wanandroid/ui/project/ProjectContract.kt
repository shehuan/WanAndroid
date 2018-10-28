package com.shehuan.wanandroid.ui.project

import com.shehuan.wanandroid.base.BaseView
import com.shehuan.wanandroid.base.net.exception.ResponseException
import com.shehuan.wanandroid.bean.ProjectCategoryBean

interface ProjectContract {
    interface View : BaseView {
        fun onProjectCategorySuccess(data: List<ProjectCategoryBean>)
        fun onProjectCategoryError(e: ResponseException)
    }

    interface Presenter {
        fun getProjectCategory()
    }
}