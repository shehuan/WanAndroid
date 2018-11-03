package com.shehuan.wanandroid.ui.chapter.chapterDetail

import android.content.Intent
import android.support.v4.view.MenuItemCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.view.Menu
import android.view.View
import com.shehuan.wanandroid.R
import com.shehuan.wanandroid.adapter.ChapterDetailListAdapter
import com.shehuan.wanandroid.base.activity.BaseActivity
import com.shehuan.wanandroid.base.activity.BaseMvpActivity
import com.shehuan.wanandroid.base.net.exception.ResponseException
import com.shehuan.wanandroid.bean.chapter.ChapterArticleBean
import com.shehuan.wanandroid.bean.chapter.DatasItem
import com.shehuan.wanandroid.ui.article.ArticleActivity
import com.shehuan.wanandroid.utils.ToastUtil
import com.shehuan.wanandroid.widget.DivideItemDecoration
import kotlinx.android.synthetic.main.activity_chapter_detail.*

class ChapterDetailActivity : BaseMvpActivity<ChapterDetailPresenterImpl>(), ChapterDetailContract.View {
    private var pageNum: Int = 0
    private var chapterId: Int = 0
    private lateinit var title: String

    private lateinit var chapterDetailListAdapter: ChapterDetailListAdapter

    private var queryPageNum: Int = 0
    private lateinit var keyWord: String
    private lateinit var queryResultAdapter: ChapterDetailListAdapter
    private var isInitQuery: Boolean = false

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

    override fun loadData() {
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
            loadData()
        }
    }

    /**
     * 文章列表初始化
     */
    private fun initChapterList() {
        chapterDetailListAdapter = ChapterDetailListAdapter(mContext, null, true)
        chapterDetailListAdapter.setLoadingView(R.layout.rv_loading_layout)
        chapterDetailListAdapter.setLoadEndView(R.layout.rv_load_end_layout)
        chapterDetailListAdapter.setLoadFailedView(R.layout.rv_load_failed_layout)

        chapterDetailListAdapter.setOnItemClickListener { _, data, _ ->
            ArticleActivity.start(mContext, data.title, data.link)
        }
        chapterDetailListAdapter.setOnItemChildClickListener(R.id.chapterArticleCollectIv) { _, data, position ->
            collectDataItem = data
            collectPosition = position
            if (!data.collect) {
                presenter.collect(data.id)
            } else {
                presenter.uncollect(data.id)
            }
        }
        chapterDetailListAdapter.setOnLoadMoreListener {
            presenter.getChapterArticleList(chapterId, pageNum)
        }
        val linearLayoutManager = LinearLayoutManager(mContext)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        chapterDetailRv.layoutManager = linearLayoutManager
        chapterDetailRv.addItemDecoration(DivideItemDecoration())
        chapterDetailRv.adapter = chapterDetailListAdapter
    }

    /**
     * 搜索列表初始化
     */
    private fun initQueryChapterList() {
        queryResultAdapter = ChapterDetailListAdapter(mContext, null, true)
        queryResultAdapter.setLoadingView(R.layout.rv_loading_layout)
        queryResultAdapter.setLoadEndView(R.layout.rv_load_end_layout)
        queryResultAdapter.setLoadFailedView(R.layout.rv_load_failed_layout)

        queryResultAdapter.setOnItemClickListener { _, data, _ ->
            ArticleActivity.start(mContext, data.title, data.link)
        }
        queryResultAdapter.setOnItemChildClickListener(R.id.chapterArticleCollectIv) { _, data, position ->
            collectDataItem = data
            collectPosition = position
            if (!data.collect) {
                presenter.collect(data.id)
            } else {
                presenter.uncollect(data.id)
            }
        }
        queryResultAdapter.setOnLoadMoreListener {
            presenter.queryChapterArticle(chapterId, pageNum, keyWord)
        }
        val linearLayoutManager = LinearLayoutManager(mContext)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        queryChapterRv.layoutManager = linearLayoutManager
        queryChapterRv.addItemDecoration(DivideItemDecoration())
        queryChapterRv.adapter = queryResultAdapter
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_query_menu_layout, menu)
        val queryItem = menu?.findItem(R.id.action_query)
        val searchView = MenuItemCompat.getActionView(queryItem) as SearchView
        // 是否显示提交按钮
        searchView.isSubmitButtonEnabled = false
        // 搜索框是否展开,false表示展开
        searchView.isIconified = true
        searchView.queryHint = "输入关键字"
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
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
        searchView.setOnCloseListener {
            queryChapterRv.visibility = View.GONE
            return@setOnCloseListener false
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
            return
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
            queryResultAdapter.setNewData(data.datas)
        } else {
            queryResultAdapter.setLoadMoreData(data.datas)
        }
        queryPageNum++
        if (queryPageNum == data.pageCount) {
            queryResultAdapter.loadEnd()
            return
        }
    }

    override fun onQueryChapterArticleListError(e: ResponseException) {
        queryResultAdapter.loadEnd()
    }

    override fun onCollectSuccess(data: String) {
        collectDataItem.collect = true
        chapterDetailListAdapter.change(collectPosition)
        ToastUtil.showToast(mContext, "收藏成功")
    }

    override fun onCollectError(e: ResponseException) {

    }

    override fun onUncollectSuccess(data: String) {
        collectDataItem.collect = false
        chapterDetailListAdapter.change(collectPosition)
        ToastUtil.showToast(mContext, "取消收藏成功")
    }

    override fun onUncollectError(e: ResponseException) {

    }
}
