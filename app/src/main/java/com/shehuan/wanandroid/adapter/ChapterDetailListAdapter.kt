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
        with(viewHolder) {
            setText(R.id.chapterArticleTitleTv, data.title)
            setText(R.id.chapterArticleTimeTv, data.niceDate)

            getView<ImageView>(R.id.chapterArticleCollectIv).run {
                if (data.collect) {
                    setImageDrawable(mContext.resources.getDrawable(R.drawable.ic_like_fill))
                } else {
                    setImageDrawable(mContext.resources.getDrawable(R.drawable.ic_like))
                }
            }
        }
    }
}