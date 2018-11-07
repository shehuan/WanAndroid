package com.shehuan.wanandroid.adapter

import android.content.Context
import com.othershe.baseadapter.ViewHolder
import com.othershe.baseadapter.base.CommonBaseAdapter
import com.shehuan.wanandroid.R
import com.shehuan.wanandroid.bean.ChapterBean

class ChapterAdapter(context: Context?, data: List<ChapterBean>?, isOpenLoadMore: Boolean) :
        CommonBaseAdapter<ChapterBean>(context, data, isOpenLoadMore) {

    private val colors = intArrayOf(R.color.c9DD3FA,
            R.color.cF7F7D0,
            R.color.cFFC09F,
            R.color.cA0D8DE,
            R.color.cE2DBBE,
            R.color.cEAE1F0)

    override fun getItemLayoutId(): Int {
        return R.layout.rv_item_chapter_layout
    }

    override fun convert(viewHolder: ViewHolder, data: ChapterBean, position: Int) {
        with(viewHolder) {
            setText(R.id.chapterNameTv, data.name)
            setBgColor(R.id.chapterNameTv, mContext.resources.getColor(colors[position % 6]))
        }
    }
}