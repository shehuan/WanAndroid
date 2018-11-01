package com.shehuan.wanandroid.ui.chapter.chapterDetail

import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import com.shehuan.library.StatusView
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
import kotlinx.android.synthetic.main.toolbar_layout.*

class ChapterDetailActivity : BaseMvpActivity<ChapterDetailPresenterImpl>(), ChapterDetailContract.View {
    private var pageNum: Int = 0
    private var chapterId: Int = 0

    private lateinit var chapterDetailListAdapter: ChapterDetailListAdapter

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
        toolbar.title = title
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener {
            finish()
        }

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

    override fun onChapterArticleListSuccess(data: ChapterArticleBean) {
        if (pageNum == 0) {
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
        chapterDetailListAdapter.loadFailed()
    }

    override fun onQueryChapterArticleListSuccess(data: ChapterArticleBean) {

    }

    override fun onQueryChapterArticleListError(e: ResponseException) {

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
