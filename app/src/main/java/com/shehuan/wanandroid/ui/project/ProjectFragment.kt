package com.shehuan.wanandroid.ui.project

import com.shehuan.wanandroid.R
import com.shehuan.wanandroid.base.fragment.BaseMvpFragment
import com.shehuan.wanandroid.base.net.exception.ResponseException
import com.shehuan.wanandroid.bean.newProject.NewProjectBean

class ProjectFragment : BaseMvpFragment<ProjectPresenterImpl>(), ProjectContract.View {
    companion object {
        fun newInstance() = ProjectFragment()
    }

    override fun initPresenter(): ProjectPresenterImpl {
        return ProjectPresenterImpl(this)
    }

    override fun loadData() {

    }

    override fun initLayoutResID(): Int {
        return R.layout.fragment_project
    }

    override fun initData() {

    }

    override fun initView() {

    }

    override fun onProjectSuccess(data: NewProjectBean) {

    }

    override fun onProjectError(e: ResponseException) {

    }
}
