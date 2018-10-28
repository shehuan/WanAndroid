package com.shehuan.wanandroid.ui.chapter.chapterDetail

import android.content.Context
import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import com.shehuan.wanandroid.R
import com.shehuan.wanandroid.adapter.ChapterDetailListAdapter
import com.shehuan.wanandroid.base.activity.BaseMvpActivity
import com.shehuan.wanandroid.base.net.exception.ResponseException
import com.shehuan.wanandroid.bean.chapter.ChapterArticleBean
import com.shehuan.wanandroid.widget.DivideItemDecoration
import kotlinx.android.synthetic.main.activity_chapter_detail.*

class ChapterDetailActivity : BaseMvpActivity<ChapterDetailPresenterImpl>(), ChapterDetailContract.View {
    private var pageNum: Int = 0
    private var chapterId: Int = 0

    private lateinit var chapterDetailListAdapter: ChapterDetailListAdapter

    companion object {
        fun start(context: Context, chapterId: Int) {
            val intent = Intent(context, ChapterDetailActivity::class.java)
            intent.putExtra("chapterId", chapterId)
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
        chapterId = intent.getIntExtra("chapterId", 0)
    }

    override fun initView() {
        chapterDetailListAdapter = ChapterDetailListAdapter(mContext, null, true)

        chapterDetailListAdapter.setOnItemClickListener { _, data, position ->

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

    }

    override fun onQueryChapterArticleListSuccess(data: ChapterArticleBean) {

    }

    override fun onQueryChapterArticleListError(e: ResponseException) {

    }
}
