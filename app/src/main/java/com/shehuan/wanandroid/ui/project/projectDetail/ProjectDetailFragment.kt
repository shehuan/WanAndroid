package com.shehuan.wanandroid.ui.project.projectDetail

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.shehuan.wanandroid.R
import com.shehuan.wanandroid.adapter.ProjectListAdapter
import com.shehuan.wanandroid.base.fragment.BaseMvpFragment
import com.shehuan.wanandroid.base.net.exception.ResponseException
import com.shehuan.wanandroid.bean.project.DatasItem
import com.shehuan.wanandroid.bean.project.ProjectBean
import com.shehuan.wanandroid.ui.article.ArticleActivity
import com.shehuan.wanandroid.utils.ToastUtil
import com.shehuan.wanandroid.widget.DivideItemDecoration
import kotlinx.android.synthetic.main.fragment_project_detail.*

private const val CID = "cid"

class ProjectDetailFragment : BaseMvpFragment<ProjectDetailPresenterImpl>(), ProjectDetailContract.View {
    private var pageNum: Int = 0
    private lateinit var projectListAdapter: ProjectListAdapter
    private lateinit var collectDataItem: DatasItem
    private var collectPosition: Int = 0

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
        projectListAdapter.setOnItemChildClickListener(R.id.projectCollectIv) { _, data, position ->
            collectDataItem = data
            collectPosition = position
            if (!data.collect) {
                presenter.collect(data.id)
            } else {
                presenter.uncollect(data.id)
            }
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

        initStatusView(projectDetailRootLayout) {
            loadData()
        }
    }

    override fun loadData() {
        statusView.showLoadingView()
        if (cid == -1) {
            presenter.getNewProjectList(pageNum)
        } else {
            presenter.getProjectDetail(pageNum, cid)
        }
    }

    private fun setData(data: ProjectBean) {
        if (pageNum == 0) {
            statusView.showContentView()
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
        if (pageNum == 0) {
            statusView.showErrorView()
        } else {
            projectListAdapter.loadFailed()
        }
    }

    override fun onProjectDetailSuccess(data: ProjectBean) {
        setData(data)
    }

    override fun onProjectDetailError(e: ResponseException) {
        if (pageNum == 0) {
            statusView.showErrorView()
        } else {
            projectListAdapter.loadFailed()
        }
    }

    override fun onCollectSuccess(data: String) {
        collectDataItem.collect = true
        projectListAdapter.change(collectPosition)
        ToastUtil.showToast(mContext, "收藏成功")
    }

    override fun onCollectError(e: ResponseException) {

    }

    override fun onUncollectSuccess(data: String) {
        collectDataItem.collect = false
        projectListAdapter.change(collectPosition)
        ToastUtil.showToast(mContext, "取消收藏成功")
    }

    override fun onUncollectError(e: ResponseException) {

    }
}
