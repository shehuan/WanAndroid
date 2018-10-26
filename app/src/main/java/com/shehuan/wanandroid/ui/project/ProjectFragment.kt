package com.shehuan.wanandroid.ui.project

import android.support.v7.widget.LinearLayoutManager
import com.shehuan.wanandroid.R
import com.shehuan.wanandroid.adapter.ProjectListAdapter
import com.shehuan.wanandroid.base.fragment.BaseMvpFragment
import com.shehuan.wanandroid.base.net.exception.ResponseException
import com.shehuan.wanandroid.bean.project.ProjectBean
import kotlinx.android.synthetic.main.fragment_project.*

class ProjectFragment : BaseMvpFragment<ProjectPresenterImpl>(), ProjectContract.View {
    private lateinit var projectListAdapter: ProjectListAdapter

    companion object {
        fun newInstance() = ProjectFragment()
    }

    override fun initPresenter(): ProjectPresenterImpl {
        return ProjectPresenterImpl(this)
    }

    override fun loadData() {
        presenter.getProjectList(0)
    }

    override fun initLayoutResID(): Int {
        return R.layout.fragment_project
    }

    override fun initData() {

    }

    override fun initView() {
        projectListAdapter = ProjectListAdapter(context, null, true)
        val linearLayoutManager = LinearLayoutManager(context)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        projectRv.layoutManager = linearLayoutManager
        projectRv.adapter = projectListAdapter
    }

    override fun onProjectListSuccess(data: ProjectBean) {
        projectListAdapter.setNewData(data.datas)
    }

    override fun onProjectListError(e: ResponseException) {

    }
}
