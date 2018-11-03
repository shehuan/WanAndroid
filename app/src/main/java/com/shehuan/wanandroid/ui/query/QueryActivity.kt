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
import com.shehuan.wanandroid.bean.db.QueryHistoryBean
import com.shehuan.wanandroid.utils.QueryHistoryDbUtil


class QueryActivity : BaseMvpActivity<QueryPresenterImpl>(), QueryContract.View {
    private var pageNum: Int = 0
    private lateinit var keyWord: String
    private var isInitQuery: Boolean = false
    private lateinit var queryResultAdapter: QueryResultAdapter
    private lateinit var searchView: SearchView

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
        statusView.showLoadingView()
        presenter.getHotKey()
    }

    override fun initLayoutResID(): Int {
        return R.layout.activity_query
    }

    override fun initData() {

    }

    override fun initView() {
        initToolbar("搜索")

        // 搜索记录相关
        val queryHistoryBeans = QueryHistoryDbUtil.query()
        if (!queryHistoryBeans.isEmpty()) {
            queryHistoryRl.visibility = View.VISIBLE
            queryHistoryFL.addQueryHistoryViews(queryHistoryBeans)
        }
        // 清空搜索记录
        clearHistoryTv.setOnClickListener {
            QueryHistoryDbUtil.clear()
            queryHistoryFL.removeAllViews()
            queryHistoryRl.visibility = View.GONE
        }

        // 搜索结果列表相关初始化
        queryResultAdapter = QueryResultAdapter(mContext, null, true)
        queryResultAdapter.setLoadingView(R.layout.rv_loading_layout)
        queryResultAdapter.setLoadEndView(R.layout.rv_load_end_layout)
        queryResultAdapter.setLoadFailedView(R.layout.rv_load_failed_layout)

        queryResultAdapter.setOnItemClickListener { _, data, _ ->
            ArticleActivity.start(mContext, data.title, data.link)
        }
        queryResultAdapter.setOnLoadMoreListener {
            if (!keyWord.isEmpty()) {
                presenter.query(pageNum, keyWord, false)
            }
        }
        val linearLayoutManager = LinearLayoutManager(mContext)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        queryResultRv.layoutManager = linearLayoutManager
        queryResultRv.addItemDecoration(DivideItemDecoration())
        queryResultRv.adapter = queryResultAdapter

        initStatusView(R.id.hotKeyFL) {
            loadData()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_query_menu_layout, menu)
        val queryItem = menu?.findItem(R.id.action_query)
        searchView = MenuItemCompat.getActionView(queryItem) as SearchView
        // 是否显示提交按钮
        searchView.isSubmitButtonEnabled = false
        // 搜索框是否展开
        searchView.isIconified = false
        searchView.queryHint = "输入关键字，多个用空格隔开"
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(content: String): Boolean {
                searchView.clearFocus()
                addQueryHistory(content)
                keyWord = content
                if (!keyWord.isEmpty()) {
                    isInitQuery = true
                    pageNum = 0
                    presenter.query(pageNum, keyWord, true)
                }
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                return true
            }
        })

        // 关闭搜索
        searchView.setOnCloseListener {
            queryResultRv.visibility = View.GONE
            return@setOnCloseListener false
        }

        return super.onCreateOptionsMenu(menu)
    }

    override fun onQuerySuccess(data: QueryBean) {
        if (queryResultRv.visibility == View.GONE) {
            queryResultRv.visibility = View.VISIBLE
        }

        if (isInitQuery) {
            isInitQuery = false
            queryResultRv.scrollToPosition(0)
            queryResultAdapter.reset()
        }

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
        statusView.showContentView()
        hotKeyFL.addHotKeyViews(data)
    }

    override fun onHotKeyError(e: ResponseException) {
        statusView.showErrorView()
    }

    private fun FlexboxLayout.addHotKeyViews(data: List<HotKeyBean>) {
        for (hotKey in data) {
            val view = TextView(mContext)
            view.setOnClickListener {
                flexboxChildClick(hotKey.name)
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
            this.addView(view, params)
        }
    }

    private fun FlexboxLayout.addQueryHistoryViews(data: List<QueryHistoryBean>) {
        for (queryHistory in data) {
            addQueryHistoryView(queryHistory.name)
        }
    }

    private fun FlexboxLayout.addQueryHistoryView(name: String) {
        val view = TextView(mContext)
        view.setOnClickListener {
            flexboxChildClick(name)
        }
        view.text = name
        view.setTextColor(resources.getColor(R.color.c8A8A8A))
        view.background = resources.getDrawable(R.drawable.query_history_selector)
        val padding1 = CommonUtil.dp2px(mContext, 10)
        val padding2 = CommonUtil.dp2px(mContext, 3)
        view.setPadding(padding1, padding2, padding1, padding2)
        val params = FlexboxLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        val margin1 = CommonUtil.dp2px(mContext, 6)
        val margin2 = CommonUtil.dp2px(mContext, 10)
        params.setMargins(margin2, margin1, margin2, margin1)
        view.layoutParams = params
        this.addView(view, 0)
    }

    private fun FlexboxLayout.childName(index: Int): String {
        val view: TextView = this.getChildAt(index) as TextView
        return view.text.toString()
    }

    /**
     * 点击搜索记录、热门搜索时调用
     */
    private fun flexboxChildClick(name: String) {
        addQueryHistory(name)
        if (searchView.isIconified) {
            searchView.isIconified = false
        }
        searchView.setQuery(name, true)
    }

    /**
     * 添加搜索记录
     */
    private fun addQueryHistory(name: String) {
        for (i in 0 until queryHistoryFL.childCount) {
            if (name == queryHistoryFL.childName(i)) {
                val view = queryHistoryFL.getChildAt(i)
                queryHistoryFL.removeView(view)
                queryHistoryFL.addView(view, 0)
                QueryHistoryDbUtil.update(name)
                return
            }
        }

        queryHistoryFL.addQueryHistoryView(name)
        QueryHistoryDbUtil.save(name)
        queryHistoryRl.visibility = View.VISIBLE
    }
}
