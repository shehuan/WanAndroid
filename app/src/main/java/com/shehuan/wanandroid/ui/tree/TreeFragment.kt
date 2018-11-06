package com.shehuan.wanandroid.ui.tree

import android.support.v7.widget.LinearLayoutManager
import com.shehuan.wanandroid.R
import com.shehuan.wanandroid.adapter.TreeListAdapter
import com.shehuan.wanandroid.base.fragment.BaseMvpFragment
import com.shehuan.wanandroid.base.net.exception.ResponseException
import com.shehuan.wanandroid.bean.tree.TreeBean
import com.shehuan.wanandroid.ui.tree.treeDetail.TreeDetailActivity
import com.shehuan.wanandroid.widget.DividerItemDecoration
import kotlinx.android.synthetic.main.fragment_tree.*

class TreeFragment : BaseMvpFragment<TreePresenterImpl>(), TreeContract.View {
    private lateinit var treeListAdapter: TreeListAdapter

    companion object {
        fun newInstance() = TreeFragment()
    }

    override fun initPresenter(): TreePresenterImpl {
        return TreePresenterImpl(this)
    }

    override fun loadData() {
        statusView.showLoadingView()
        presenter.getTree()
    }

    override fun initLayoutResID(): Int {
        return R.layout.fragment_tree
    }

    override fun initData() {

    }

    override fun initView() {
        treeListAdapter = TreeListAdapter(context, null, false)
        treeListAdapter.setOnItemClickListener { _, data, _ ->
            TreeDetailActivity.start(mContext, data.name, data.children)
        }
        val linearLayoutManager = LinearLayoutManager(context)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        treeRv.layoutManager = linearLayoutManager
        treeRv.addItemDecoration(DividerItemDecoration()
                .setDividerHeight(20)
                .setDividerColor(resources.getColor(R.color.cEEEEF5)))
        treeRv.adapter = treeListAdapter

        initStatusView(treeRootLayout) {
            loadData()
        }
    }

    override fun onTreeSuccess(data: List<TreeBean>) {
        statusView.showContentView()
        treeListAdapter.setNewData(data)
    }

    override fun onTreeError(e: ResponseException) {
        statusView.showErrorView()
    }
}
