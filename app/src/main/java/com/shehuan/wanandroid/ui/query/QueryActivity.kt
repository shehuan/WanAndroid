package com.shehuan.wanandroid.ui.query

import android.content.Intent
import android.support.v4.view.MenuItemCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.google.android.flexbox.FlexboxLayout
import com.shehuan.wanandroid.R
import com.shehuan.wanandroid.adapter.QueryResultAdapter
import com.shehuan.wanandroid.base.activity.BaseActivity
import com.shehuan.wanandroid.base.activity.BaseMvpActivity
import com.shehuan.wanandroid.base.net.exception.ResponseException
import com.shehuan.wanandroid.bean.HotKeyBean
import com.shehuan.wanandroid.bean.query.QueryBean
import com.shehuan.wanandroid.ui.article.ArticleActivity
import com.shehuan.wanandroid.utils.CommonUtil
import com.shehuan.wanandroid.widget.DivideItemDecoration
import kotlinx.android.synthetic.main.activity_query.*
import kotlinx.android.synthetic.main.toolbar_layout.*

class QueryActivity : BaseMvpActivity<QueryPresenterImpl>(), QueryContract.View {
    private var pageNum: Int = 0
    private lateinit var keyWord: String
    private lateinit var queryResultAdapter: QueryResultAdapter

    companion object {
        fun start(context: BaseActivity) {
            val intent = Intent(context, QueryActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun initPresenter(): QueryPresenterImpl {
        return QueryPresenterImpl(this)
    }

    override fun loadData() {
        presenter.getHotKey()
    }

    override fun initLayoutResID(): Int {
        return R.layout.activity_query
    }

    override fun initData() {

    }

    override fun initView() {
        toolbar.title = "搜索"
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener {
            finish()
        }

        queryResultAdapter = QueryResultAdapter(mContext, null, true)
        queryResultAdapter.setLoadingView(R.layout.rv_loading_layout)
        queryResultAdapter.setLoadEndView(R.layout.rv_load_end_layout)
        queryResultAdapter.setLoadFailedView(R.layout.rv_load_failed_layout)

        queryResultAdapter.setOnItemClickListener { _, data, _ ->
            ArticleActivity.start(mContext, data.title, data.link)
        }
        queryResultAdapter.setOnLoadMoreListener {
            if (!keyWord.isEmpty()) {
                presenter.query(pageNum, keyWord)
            }
        }
        val linearLayoutManager = LinearLayoutManager(mContext)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        queryResultRv.layoutManager = linearLayoutManager
        queryResultRv.addItemDecoration(DivideItemDecoration())
        queryResultRv.adapter = queryResultAdapter
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_query_menu_layout, menu)
        val queryItem = menu?.findItem(R.id.action_query)
        val searchView: SearchView = MenuItemCompat.getActionView(queryItem) as SearchView
        searchView.isSubmitButtonEnabled = true
        searchView.isIconified = false
        searchView.queryHint = "多个关键词，用空格隔开"
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(content: String?): Boolean {
                keyWord = content.toString()
                if (!keyWord.isEmpty()) {
                    pageNum = 0
                    presenter.query(pageNum, keyWord)
                }
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                return true
            }
        })

        searchView.setOnCloseListener {
            queryResultRv.visibility = View.GONE
            pageNum = 0
            keyWord = ""
            return@setOnCloseListener false
        }

        return super.onCreateOptionsMenu(menu)
    }

    override fun onQuerySuccess(data: QueryBean) {
        queryResultRv.visibility = View.VISIBLE

        if (pageNum == 0) {
            queryResultAdapter.setNewData(data.datas)
        } else {
            queryResultAdapter.setLoadMoreData(data.datas)
        }
        pageNum++
        if (pageNum == data.pageCount) {
            queryResultAdapter.loadEnd()
            return
        }
    }

    override fun onQueryError(e: ResponseException) {
        queryResultAdapter.loadFailed()
    }

    override fun onHotKeySuccess(data: List<HotKeyBean>) {
        hotKeyFL.setHotKeyData(data)
    }

    override fun onHotKeyError(e: ResponseException) {

    }

    private fun FlexboxLayout.setHotKeyData(data: List<HotKeyBean>) {
        for (hotKey in data) {
            val view = TextView(mContext)
            view.setOnClickListener {
                keyWord = hotKey.name
                presenter.query(pageNum, keyWord)
            }
            view.text = hotKey.name
            view.setTextColor(resources.getColor(R.color.c8A8A8A))
            view.background = resources.getDrawable(R.drawable.hotkey_selector)
            val padding1 = CommonUtil.dp2px(mContext, 10)
            val padding2 = CommonUtil.dp2px(mContext, 3)
            view.setPadding(padding1, padding2, padding1, padding2)
            val params = FlexboxLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            val margin1 = CommonUtil.dp2px(mContext, 6)
            val margin2 = CommonUtil.dp2px(mContext, 10)
            params.setMargins(margin2, margin1, margin2, margin1)
            hotKeyFL.addView(view, params)
        }
    }
}
