package com.shehuan.wanandroid.adapter

import android.content.Context
import com.othershe.baseadapter.ViewHolder
import com.othershe.baseadapter.base.CommonBaseAdapter
import com.shehuan.wanandroid.R
import com.shehuan.wanandroid.bean.ChapterBean

class ChapterAdapter(context: Context?, data: List<ChapterBean>?, isOpenLoadMore: Boolean) :
        CommonBaseAdapter<ChapterBean>(context, data, isOpenLoadMore) {

    private val colors = intArrayOf(R.color.c4CB4E7,
            R.color.cFFC09F,
            R.color.cFFEE93,
            R.color.cE2DBBE,
            R.color.cA3A380)

    override fun getItemLayoutId(): Int {
        return R.layout.rv_item_chapter_layout
    }

    override fun convert(viewHolder: ViewHolder, data: ChapterBean, position: Int) {
        viewHolder.setText(R.id.chapterNameTv, data.name)
        viewHolder.setBgColor(R.id.chapterNameTv, mContext.resources.getColor(colors[position % 5]))
    }
}