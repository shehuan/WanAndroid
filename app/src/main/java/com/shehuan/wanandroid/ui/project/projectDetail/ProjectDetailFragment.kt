package com.shehuan.wanandroid.ui.project.projectDetail

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shehuan.wanandroid.R
import com.shehuan.wanandroid.adapter.ProjectListAdapter
import com.shehuan.wanandroid.base.fragment.BaseMvpFragment
import com.shehuan.wanandroid.base.net.exception.ResponseException
import com.shehuan.wanandroid.bean.project.DatasItem
import com.shehuan.wanandroid.bean.project.ProjectBean
import com.shehuan.wanandroid.ui.article.ArticleActivity
import com.shehuan.wanandroid.utils.ToastUtil
import com.shehuan.wanandroid.widget.DividerItemDecoration
import kotlinx.android.synthetic.main.floating_button_layout.*
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
        // 悬浮按钮点击事件
        floatBtn.hide()
        floatBtn.setOnClickListener {
            projectRv.smoothScrollToPosition(0)
        }

        projectListAdapter = ProjectListAdapter(context, null, true).apply {
            setLoadingView(R.layout.rv_loading_layout)
            setLoadEndView(R.layout.rv_load_end_layout)
            setLoadFailedView(R.layout.rv_load_failed_layout)
            setOnItemClickListener { _, data, _ ->
                ArticleActivity.start(mContext, data.title, data.link)
            }
            setOnItemChildClickListener(R.id.projectCollectIv) { _, data, position ->
                collectDataItem = data
                collectPosition = position
                if (!data.collect) {
                    presenter.collect(data.id)
                } else {
                    presenter.uncollect(data.id)
                }
            }
            setOnLoadMoreListener {
                if (cid == -1) {
                    presenter.getNewProjectList(pageNum)
                } else {
                    presenter.getProjectDetail(pageNum, cid)
                }
            }
        }
        val linearLayoutManager = LinearLayoutManager(context)
        linearLayoutManager.orientation = RecyclerView.VERTICAL
        projectRv.run {
            layoutManager = linearLayoutManager
            addItemDecoration(DividerItemDecoration())
            adapter = projectListAdapter
            // 控制悬浮按钮
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if (linearLayoutManager.findLastVisibleItemPosition() > 10) {
                        floatBtn.show()
                    } else {
                        floatBtn.hide()
                    }
                }
            })
        }

        initStatusView(projectDetailRootLayout) {
            initLoad()
        }
    }

    override fun initLoad() {
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
        ToastUtil.showToast(mContext, R.string.collect_success)
    }

    override fun onCollectError(e: ResponseException) {

    }

    override fun onUncollectSuccess(data: String) {
        collectDataItem.collect = false
        projectListAdapter.change(collectPosition)
        ToastUtil.showToast(mContext, R.string.uncollect_success)
    }

    override fun onUncollectError(e: ResponseException) {

    }
}
