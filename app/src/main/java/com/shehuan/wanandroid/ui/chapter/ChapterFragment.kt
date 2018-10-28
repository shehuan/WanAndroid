package com.shehuan.wanandroid.ui.chapter

import android.support.v7.widget.GridLayoutManager
import com.shehuan.wanandroid.R
import com.shehuan.wanandroid.adapter.ChapterAdapter
import com.shehuan.wanandroid.base.fragment.BaseMvpFragment
import com.shehuan.wanandroid.base.net.exception.ResponseException
import com.shehuan.wanandroid.bean.ChapterBean
import com.shehuan.wanandroid.ui.chapter.chapterDetail.ChapterDetailActivity
import kotlinx.android.synthetic.main.fragment_official_accounts.*

class ChapterFragment : BaseMvpFragment<ChapterPresenterImpl>(), ChapterContract.View {
    private lateinit var chapterAdapter: ChapterAdapter

    companion object {
        fun newInstance() = ChapterFragment()
    }

    override fun initPresenter(): ChapterPresenterImpl {
        return ChapterPresenterImpl(this)
    }

    override fun loadData() {
        presenter.getChapter()
    }

    override fun initLayoutResID(): Int {
        return R.layout.fragment_official_accounts
    }

    override fun initData() {

    }

    override fun initView() {
        chapterAdapter = ChapterAdapter(context, null, false)
        chapterAdapter.setOnItemClickListener { _, data, _ ->
            ChapterDetailActivity.start(mContext, data.id)
        }
        val gridLayoutManager = GridLayoutManager(context, 2)
        chapterRv.layoutManager = gridLayoutManager
        chapterRv.adapter = chapterAdapter
    }

    override fun onChapterSuccess(data: List<ChapterBean>) {
        chapterAdapter.setNewData(data)
    }

    override fun onChapterError(e: ResponseException) {

    }
}
