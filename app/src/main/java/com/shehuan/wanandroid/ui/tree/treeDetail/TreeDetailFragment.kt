package com.shehuan.wanandroid.ui.tree.treeDetail


import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.shehuan.wanandroid.R
import com.shehuan.wanandroid.adapter.TreeDetailListAdapter
import com.shehuan.wanandroid.base.fragment.BaseMvpFragment
import com.shehuan.wanandroid.base.net.exception.ResponseException
import com.shehuan.wanandroid.bean.treeDetail.DatasItem
import com.shehuan.wanandroid.bean.treeDetail.TreeDetailBean
import com.shehuan.wanandroid.ui.article.ArticleActivity
import com.shehuan.wanandroid.utils.ToastUtil
import com.shehuan.wanandroid.widget.DivideItemDecoration
import kotlinx.android.synthetic.main.fragment_tree_detail.*

private const val CID = "cid"

class TreeDetailFragment : BaseMvpFragment<TreeDetailPresenterImpl>(), TreeDetailContract.View {
    private var pageNum: Int = 0
    private lateinit var treeDetailListAdapter: TreeDetailListAdapter
    private lateinit var collectDataItem: DatasItem
    private var collectPosition: Int = 0

    private var cid: Int = 0

    companion object {
        fun newInstance(cid: Int) =
                TreeDetailFragment().apply {
                    arguments = Bundle().apply {
                        putInt("cid", cid)
                    }
                }
    }

    override fun initPresenter(): TreeDetailPresenterImpl {
        return TreeDetailPresenterImpl(this)
    }

    override fun initLayoutResID(): Int {
        return R.layout.fragment_tree_detail
    }

    override fun initData() {
        arguments?.let {
            cid = it.getInt(CID)
        }
    }

    override fun initView() {
        treeDetailListAdapter = TreeDetailListAdapter(context, null, true)
        treeDetailListAdapter.setLoadingView(R.layout.rv_loading_layout)
        treeDetailListAdapter.setLoadEndView(R.layout.rv_load_end_layout)
        treeDetailListAdapter.setLoadFailedView(R.layout.rv_load_failed_layout)
        treeDetailListAdapter.setOnItemClickListener { _, data, _ ->
            ArticleActivity.start(mContext, data.title, data.link)
        }
        treeDetailListAdapter.setOnItemChildClickListener(R.id.treeArticleCollectIv) { _, data, position ->
            collectDataItem = data
            collectPosition = position
            if (!data.collect) {
                presenter.collect(data.id)
            } else {
                presenter.uncollect(data.id)
            }
        }
        treeDetailListAdapter.setOnLoadMoreListener {
            presenter.getTreeDetail(pageNum, cid)

        }
        val linearLayoutManager = LinearLayoutManager(context)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        treeDetailRv.layoutManager = linearLayoutManager
        treeDetailRv.addItemDecoration(DivideItemDecoration())
        treeDetailRv.adapter = treeDetailListAdapter
    }

    override fun loadData() {
        presenter.getTreeDetail(pageNum, cid)
    }

    override fun onTreeDetailSuccess(data: TreeDetailBean) {
        if (pageNum == 0) {
            treeDetailListAdapter.setNewData(data.datas)
        } else {
            treeDetailListAdapter.setLoadMoreData(data.datas)
        }
        pageNum++
        if (pageNum == data.pageCount) {
            treeDetailListAdapter.loadEnd()
            return
        }
    }

    override fun onTreeDetailError(e: ResponseException) {
        treeDetailListAdapter.loadFailed()
    }

    override fun onCollectSuccess(data: String) {
        collectDataItem.collect = true
        treeDetailListAdapter.change(collectPosition)
        ToastUtil.showToast(mContext, "收藏成功")
    }

    override fun onCollectError(e: ResponseException) {

    }

    override fun onUncollectSuccess(data: String) {
        collectDataItem.collect = false
        treeDetailListAdapter.change(collectPosition)
        ToastUtil.showToast(mContext, "取消收藏成功")
    }

    override fun onUncollectError(e: ResponseException) {

    }
}
