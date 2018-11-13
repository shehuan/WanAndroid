package com.shehuan.wanandroid.ui.chapter.chapterDetail

import android.content.Intent
import androidx.core.view.MenuItemCompat
import android.view.Menu
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shehuan.wanandroid.R
import com.shehuan.wanandroid.adapter.ChapterDetailListAdapter
import com.shehuan.wanandroid.base.activity.BaseActivity
import com.shehuan.wanandroid.base.activity.BaseMvpActivity
import com.shehuan.wanandroid.base.net.exception.ResponseException
import com.shehuan.wanandroid.bean.chapter.ChapterArticleBean
import com.shehuan.wanandroid.bean.chapter.DatasItem
import com.shehuan.wanandroid.ui.article.ArticleActivity
import com.shehuan.wanandroid.utils.ToastUtil
import com.shehuan.wanandroid.widget.DividerItemDecoration
import kotlinx.android.synthetic.main.activity_chapter_detail.*
import kotlinx.android.synthetic.main.floating_button_layout.*

class ChapterDetailActivity : BaseMvpActivity<ChapterDetailPresenterImpl>(), ChapterDetailContract.View {
    private var pageNum: Int = 0
    private var chapterId: Int = 0
    private lateinit var title: String

    private lateinit var chapterDetailListAdapter: ChapterDetailListAdapter

    private var queryPageNum: Int = 0
    private lateinit var keyWord: String
    private lateinit var queryResultAdapter: ChapterDetailListAdapter
    private var isInitQuery: Boolean = false
    // 搜索结果是否为空
    private var isEmpty: Boolean = false

    private lateinit var collectDataItem: DatasItem
    private var collectPosition: Int = 0

    companion object {
        fun start(context: BaseActivity, title: String, chapterId: Int) {
            val intent = Intent(context, ChapterDetailActivity::class.java)
            intent.apply {
                putExtra("chapterId", chapterId)
                putExtra("title", title)
            }
            context.startActivity(intent)
        }
    }

    override fun initPresenter(): ChapterDetailPresenterImpl {
        return ChapterDetailPresenterImpl(this)
    }

    override fun initLoad() {
        statusView.showLoadingView()
        presenter.getChapterArticleList(chapterId, pageNum)
    }

    override fun initLayoutResID(): Int {
        return R.layout.activity_chapter_detail
    }

    override fun initData() {
        intent?.let {
            title = it.getStringExtra("title")
            chapterId = it.getIntExtra("chapterId", 0)
        }
    }

    override fun initView() {
        initToolbar(title)
        initChapterList()
        initQueryChapterList()

        initStatusView(R.id.contentFl) {
            initLoad()
        }
    }

    /**
     * 文章列表初始化
     */
    private fun initChapterList() {
        // 悬浮按钮点击事件
        floatBtn.hide()
        floatBtn.setOnClickListener {
            chapterDetailRv.smoothScrollToPosition(0)
        }

        chapterDetailListAdapter = ChapterDetailListAdapter(mContext, null, true).apply {
            setLoadingView(R.layout.rv_loading_layout)
            setLoadEndView(R.layout.rv_load_end_layout)
            setLoadFailedView(R.layout.rv_load_failed_layout)

            setOnItemClickListener { _, data, _ ->
                ArticleActivity.start(mContext, data.title, data.link)
            }
            setOnItemChildClickListener(R.id.chapterArticleCollectIv) { _, data, position ->
                collectDataItem = data
                collectPosition = position
                if (!data.collect) {
                    presenter.collect(data.id)
                } else {
                    presenter.uncollect(data.id)
                }
            }
            setOnLoadMoreListener {
                presenter.getChapterArticleList(chapterId, pageNum)
            }
        }
        val linearLayoutManager = LinearLayoutManager(mContext)
        linearLayoutManager.orientation = RecyclerView.VERTICAL
        chapterDetailRv.run {
            layoutManager = linearLayoutManager
            addItemDecoration(DividerItemDecoration())
            adapter = chapterDetailListAdapter
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
    }

    /**
     * 搜索列表初始化
     */
    private fun initQueryChapterList() {
        queryResultAdapter = ChapterDetailListAdapter(mContext, null, true).apply {
            setLoadingView(R.layout.rv_loading_layout)
            setLoadEndView(R.layout.rv_load_end_layout)
            setLoadFailedView(R.layout.rv_load_failed_layout)

            setOnItemClickListener { _, data, _ ->
                ArticleActivity.start(mContext, data.title, data.link)
            }
            setOnItemChildClickListener(R.id.chapterArticleCollectIv) { _, data, position ->
                collectDataItem = data
                collectPosition = position
                if (!data.collect) {
                    presenter.collect(data.id)
                } else {
                    presenter.uncollect(data.id)
                }
            }
            setOnLoadMoreListener {
                presenter.queryChapterArticle(chapterId, pageNum, keyWord)
            }
        }
        val linearLayoutManager = LinearLayoutManager(mContext)
        linearLayoutManager.orientation = RecyclerView.VERTICAL
        queryChapterRv.layoutManager = linearLayoutManager
        queryChapterRv.addItemDecoration(DividerItemDecoration())
        queryChapterRv.adapter = queryResultAdapter
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_query_menu_layout, menu)
        val queryItem = menu?.findItem(R.id.action_query)
        val searchView = MenuItemCompat.getActionView(queryItem) as SearchView
        searchView.run {
            // 是否显示提交按钮
            isSubmitButtonEnabled = false
            // 搜索框是否展开,false表示展开
            isIconified = true
            queryHint = getString(R.string.query_tip)
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(content: String): Boolean {
                    searchView.clearFocus()
                    keyWord = content
                    if (!keyWord.isEmpty()) {
                        queryPageNum = 0
                        isInitQuery = true
                        presenter.queryChapterArticle(chapterId, queryPageNum, keyWord)
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
                queryChapterRv.visibility = View.GONE
                return@setOnCloseListener false
            }
        }

        return super.onCreateOptionsMenu(menu)
    }

    override fun onChapterArticleListSuccess(data: ChapterArticleBean) {
        if (pageNum == 0) {
            statusView.showContentView()
            chapterDetailListAdapter.setNewData(data.datas)
        } else {
            chapterDetailListAdapter.setLoadMoreData(data.datas)
        }
        pageNum++
        if (pageNum == data.pageCount) {
            chapterDetailListAdapter.loadEnd()
        }
    }

    override fun onChapterArticleListError(e: ResponseException) {
        if (pageNum == 0) {
            statusView.showErrorView()
        } else {
            chapterDetailListAdapter.loadFailed()
        }
    }

    override fun onQueryChapterArticleListSuccess(data: ChapterArticleBean) {
        if (queryChapterRv.visibility == View.GONE) {
            queryChapterRv.visibility = View.VISIBLE
        }

        if (isInitQuery) {
            isInitQuery = false
            queryChapterRv.scrollToPosition(0)
            queryResultAdapter.reset()
        }

        if (queryPageNum == 0) {
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
        queryPageNum++
        if (queryPageNum == data.pageCount) {
            queryResultAdapter.loadEnd()
        }
    }

    override fun onQueryChapterArticleListError(e: ResponseException) {
        queryResultAdapter.loadEnd()
    }

    override fun onCollectSuccess(data: String) {
        collectDataItem.collect = true
        chapterDetailListAdapter.change(collectPosition)
        ToastUtil.showToast(mContext, R.string.collect_success)
    }

    override fun onCollectError(e: ResponseException) {

    }

    override fun onUncollectSuccess(data: String) {
        collectDataItem.collect = false
        chapterDetailListAdapter.change(collectPosition)
        ToastUtil.showToast(mContext, R.string.uncollect_success)
    }

    override fun onUncollectError(e: ResponseException) {

    }
}
