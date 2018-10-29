package com.shehuan.wanandroid.ui.collection

import android.content.Context
import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import com.shehuan.wanandroid.R
import com.shehuan.wanandroid.adapter.CollectionListAdapter
import com.shehuan.wanandroid.base.activity.BaseMvpActivity
import com.shehuan.wanandroid.base.net.exception.ResponseException
import com.shehuan.wanandroid.bean.article.ArticleBean
import com.shehuan.wanandroid.ui.article.ArticleActivity
import com.shehuan.wanandroid.widget.DivideItemDecoration
import kotlinx.android.synthetic.main.activity_my_collection.*
import kotlinx.android.synthetic.main.toolbar_layout.*

class MyCollectionActivity : BaseMvpActivity<MyCollectionPresenterImpl>(), MyCollectionContract.View {
    private var pageNum: Int = 0
    private lateinit var collectionListAdapter: CollectionListAdapter

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, MyCollectionActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun initPresenter(): MyCollectionPresenterImpl {
        return MyCollectionPresenterImpl(this)
    }

    override fun loadData() {
        presenter.getCollectionList(pageNum)
    }

    override fun initLayoutResID(): Int {
        return R.layout.activity_my_collection
    }

    override fun initData() {

    }

    override fun initView() {
        toolbar.title = "收藏"
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener {
            finish()
        }

        collectionListAdapter = CollectionListAdapter(mContext, null, true)
        collectionListAdapter.setLoadingView(R.layout.rv_loading_layout)
        collectionListAdapter.setLoadEndView(R.layout.rv_load_end_layout)
        collectionListAdapter.setLoadFailedView(R.layout.rv_load_failed_layout)

        collectionListAdapter.setOnItemClickListener { _, data, _ ->
            ArticleActivity.start(mContext, data.title, data.link)
        }
        collectionListAdapter.setOnLoadMoreListener {
            presenter.getCollectionList(pageNum)
        }
        val linearLayoutManager = LinearLayoutManager(mContext)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        collectionRv.layoutManager = linearLayoutManager
        collectionRv.addItemDecoration(DivideItemDecoration())
        collectionRv.adapter = collectionListAdapter
    }

    override fun onCollectionListSuccess(data: ArticleBean) {
        if (pageNum == 0) {
            collectionListAdapter.setNewData(data.datas)
        } else {
            collectionListAdapter.setLoadMoreData(data.datas)
        }
        pageNum++
        if (pageNum == data.pageCount) {
            collectionListAdapter.loadEnd()
            return
        }
    }

    override fun onCollectionListError(e: ResponseException) {
        collectionListAdapter.loadFailed()
    }
}
