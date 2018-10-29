package com.shehuan.wanandroid.ui.project.projectDetail

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.shehuan.wanandroid.R
import com.shehuan.wanandroid.adapter.ProjectListAdapter
import com.shehuan.wanandroid.base.fragment.BaseMvpFragment
import com.shehuan.wanandroid.base.net.exception.ResponseException
import com.shehuan.wanandroid.bean.project.ProjectBean
import com.shehuan.wanandroid.ui.article.ArticleActivity
import com.shehuan.wanandroid.widget.DivideItemDecoration
import kotlinx.android.synthetic.main.fragment_project_detail.*

private const val CID = "cid"

class ProjectDetailFragment : BaseMvpFragment<ProjectDetailPresenterImpl>(), ProjectDetailContract.View {
    private var pageNum: Int = 0
    private lateinit var projectListAdapter: ProjectListAdapter

    private var cid: Int = 0

    companion object {
        fun newInstance(cid: Int) =
                ProjectDetailFragment().apply {
                    arguments = Bundle().apply {
                        putInt("cid", cid)
                    }
                }
    }

    override fun initPresenter(): ProjectDetailPresenterImpl {
        return ProjectDetailPresenterImpl(this)
    }

    override fun initLayoutResID(): Int {
        return R.layout.fragment_project_detail
    }

    override fun initData() {
        arguments?.let {
            cid = it.getInt(CID)
        }
    }

    override fun initView() {
        projectListAdapter = ProjectListAdapter(context, null, true)
        projectListAdapter.setLoadingView(R.layout.rv_loading_layout)
        projectListAdapter.setLoadEndView(R.layout.rv_load_end_layout)
        projectListAdapter.setLoadFailedView(R.layout.rv_load_failed_layout)
        projectListAdapter.setOnItemClickListener { _, data, _ ->
            ArticleActivity.start(mContext, data.title, data.link)
        }
        projectListAdapter.setOnLoadMoreListener {
            if (cid == -1) {
                presenter.getNewProjectList(pageNum)
            } else {
                presenter.getProjectDetail(pageNum, cid)
            }
        }
        val linearLayoutManager = LinearLayoutManager(context)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        projectRv.layoutManager = linearLayoutManager
        projectRv.addItemDecoration(DivideItemDecoration())
        projectRv.adapter = projectListAdapter
    }

    override fun loadData() {
        if (cid == -1) {
            presenter.getNewProjectList(pageNum)
        } else {
            presenter.getProjectDetail(pageNum, cid)
        }
    }

    private fun setData(data: ProjectBean) {
        if (pageNum == 0) {
            projectListAdapter.setNewData(data.datas)
        } else {
            projectListAdapter.setLoadMoreData(data.datas)
        }
        pageNum++
        if (pageNum == data.pageCount) {
            projectListAdapter.loadEnd()
            return
        }
    }

    override fun onNewProjectListSuccess(data: ProjectBean) {
        setData(data)
    }

    override fun onNewProjectListError(e: ResponseException) {
        projectListAdapter.loadFailed()
    }

    override fun onProjectDetailSuccess(data: ProjectBean) {
        setData(data)
    }

    override fun onProjectDetailError(e: ResponseException) {
        projectListAdapter.loadFailed()
    }
}
