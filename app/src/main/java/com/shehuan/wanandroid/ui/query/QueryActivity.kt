package com.shehuan.wanandroid.ui.query

import android.content.Intent
import androidx.core.view.MenuItemCompat
import android.view.Menu
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shehuan.wanandroid.R
import com.shehuan.wanandroid.adapter.QueryResultAdapter
import com.shehuan.wanandroid.base.activity.BaseActivity
import com.shehuan.wanandroid.base.activity.BaseMvpActivity
import com.shehuan.wanandroid.base.net.exception.ResponseException
import com.shehuan.wanandroid.bean.HotKeyBean
import com.shehuan.wanandroid.bean.query.QueryBean
import com.shehuan.wanandroid.ui.article.ArticleActivity
import com.shehuan.wanandroid.widget.DividerItemDecoration
import kotlinx.android.synthetic.main.activity_query.*
import com.shehuan.wanandroid.utils.QueryHistoryDbUtil
import com.shehuan.wanandroid.utils.addCommonView
import com.shehuan.wanandroid.utils.childName


class QueryActivity : BaseMvpActivity<QueryPresenterImpl>(), QueryContract.View {
    private var pageNum: Int = 0
    private lateinit var keyWord: String
    private var isInitQuery: Boolean = false
    private lateinit var queryResultAdapter: QueryResultAdapter
    private lateinit var searchView: SearchView
    // 搜索结果是否为空
    private var isEmpty: Boolean = false

    companion object {
        fun start(context: BaseActivity) {
            val intent = Intent(context, QueryActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun initPresenter(): QueryPresenterImpl {
        return QueryPresenterImpl(this)
    }

    override fun initLoad() {
        statusView.showLoadingView()
        presenter.getHotKey()
    }

    override fun initLayoutResID(): Int {
        return R.layout.activity_query
    }

    override fun initData() {

    }

    override fun initView() {
        initToolbar(R.string.query)

        // 搜索记录相关
        val queryHistoryBeans = QueryHistoryDbUtil.query()
        if (!queryHistoryBeans.isEmpty()) {
            queryHistoryRl.visibility = View.VISIBLE
            for (queryHistory in queryHistoryBeans) {
                queryHistoryFL.addCommonView(mContext, queryHistory.name, R.color.c8A8A8A, R.drawable.query_history_selector, false) {
                    flexboxClick(queryHistory.name)
                }
            }
        }
        // 清空搜索记录
        clearHistoryTv.setOnClickListener {
            QueryHistoryDbUtil.clear()
            queryHistoryFL.removeAllViews()
            queryHistoryRl.visibility = View.GONE
        }

        // 搜索结果列表相关初始化
        queryResultAdapter = QueryResultAdapter(mContext, null, true).apply {
            setLoadingView(R.layout.rv_loading_layout)
            setLoadEndView(R.layout.rv_load_end_layout)
            setLoadFailedView(R.layout.rv_load_failed_layout)

            setOnItemClickListener { _, data, _ ->
                ArticleActivity.start(mContext, data.title, data.link)
            }
            setOnLoadMoreListener {
                if (!keyWord.isEmpty()) {
                    presenter.query(pageNum, keyWord, false)
                }
            }
        }

        val linearLayoutManager = LinearLayoutManager(mContext)
        linearLayoutManager.orientation = RecyclerView.VERTICAL
        queryResultRv.layoutManager = linearLayoutManager
        queryResultRv.addItemDecoration(DividerItemDecoration())
        queryResultRv.adapter = queryResultAdapter

        initStatusView(R.id.contentFl) {
            initLoad()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_query_menu_layout, menu)
        val queryItem = menu?.findItem(R.id.action_query)
        searchView = MenuItemCompat.getActionView(queryItem) as SearchView
        searchView.run {
            // 是否显示提交按钮
            isSubmitButtonEnabled = false
            // 搜索框是否展开
            isIconified = false
            queryHint = getString(R.string.query_tip1)
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(content: String): Boolean {
                    clearFocus()
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
            setOnCloseListener {
                isEmpty = false
                statusView.showContentView()
                queryResultRv.visibility = View.GONE
                return@setOnCloseListener false
            }
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
            if (data.datas.isEmpty()) {
                isEmpty = true
                statusView.showEmptyView()
                return
            } else if (isEmpty) {
                statusView.showContentView()
            }
            queryResultAdapter.setNewData(data.datas)
        } else {
            queryResultAdapter.setLoadMoreData(data.datas)
        }
        pageNum++
        if (pageNum == data.pageCount) {
            queryResultAdapter.loadEnd()
        }
    }

    override fun onQueryError(e: ResponseException) {
        queryResultAdapter.loadFailed()
    }

    override fun onHotKeySuccess(data: List<HotKeyBean>) {
        statusView.showContentView()
        for (hotKey in data) {
            hotKeyFL.addCommonView(mContext, hotKey.name, R.color.c515151, R.drawable.hotkey_selector) {
                flexboxClick(hotKey.name)
            }
        }
    }

    override fun onHotKeyError(e: ResponseException) {
        statusView.showErrorView()
    }

    /**
     * 点击搜索记录、热门搜索时调用
     */
    private fun flexboxClick(name: String) {
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

        queryHistoryFL.addCommonView(mContext, name, R.color.c8A8A8A, R.drawable.query_history_selector) {
            flexboxClick(name)
        }
        QueryHistoryDbUtil.save(name)
        queryHistoryRl.visibility = View.VISIBLE
    }
}
