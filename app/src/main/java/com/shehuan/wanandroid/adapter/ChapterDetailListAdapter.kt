package com.shehuan.wanandroid.adapter

import android.content.Context
import android.widget.ImageView
import com.othershe.baseadapter.ViewHolder
import com.othershe.baseadapter.base.CommonBaseAdapter
import com.shehuan.wanandroid.R
import com.shehuan.wanandroid.bean.chapter.DatasItem

class ChapterDetailListAdapter(context: Context?, data: List<DatasItem>?, isOpenLoadMore: Boolean) :
        CommonBaseAdapter<DatasItem>(context, data, isOpenLoadMore) {
    override fun getItemLayoutId(): Int {
        return R.layout.rv_item_chapter_detail_layout
    }

    override fun convert(viewHolder: ViewHolder, data: DatasItem, position: Int) {
        viewHolder.setText(R.id.chapterArticleTitleTv, data.title)
        viewHolder.setText(R.id.chapterArticleTimeTv, data.niceDate)

        val collectTv = viewHolder.getView<ImageView>(R.id.chapterArticleCollectIv)

        if (data.collect) {
            collectTv.setImageDrawable(mContext.resources.getDrawable(R.drawable.ic_like_fill))
        } else {
            collectTv.setImageDrawable(mContext.resources.getDrawable(R.drawable.ic_like))
        }
    }
}