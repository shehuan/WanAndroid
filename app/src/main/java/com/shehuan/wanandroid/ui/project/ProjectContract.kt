package com.shehuan.wanandroid.ui.project

import com.shehuan.wanandroid.base.BaseView
import com.shehuan.wanandroid.base.net.exception.ResponseException
import com.shehuan.wanandroid.bean.newProject.NewProjectBean

interface ProjectContract {
    interface View : BaseView {
        fun onProjectSuccess(data: NewProjectBean)
        fun onProjectError(e: ResponseException)
    }

    interface Presenter {
        fun getProject(pageNum: Int)
    }
}