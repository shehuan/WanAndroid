package com.shehuan.wanandroid.adapter

import android.content.Context
import android.text.Html
import android.widget.ImageView
import com.othershe.baseadapter.ViewHolder
import com.othershe.baseadapter.base.CommonBaseAdapter
import com.shehuan.wanandroid.R
import com.shehuan.wanandroid.bean.treeDetail.DatasItem

class TreeDetailListAdapter(context: Context?, data: List<DatasItem>?, isOpenLoadMore: Boolean) :
        CommonBaseAdapter<DatasItem>(context, data, isOpenLoadMore) {
    override fun getItemLayoutId(): Int {
        return R.layout.rv_item_tree_detail_layout
    }

    override fun convert(viewHolder: ViewHolder, data: DatasItem, position: Int) {
        viewHolder.setText(R.id.articleTitleTv, Html.fromHtml(data.title).toString())
        viewHolder.setText(R.id.articleAuthorTv, data.author)
        viewHolder.setText(R.id.articleTimeTv, data.niceDate)

        val collectTv = viewHolder.getView<ImageView>(R.id.treeArticleCollectIv)

        if (data.collect) {
            collectTv.setImageDrawable(mContext.resources.getDrawable(R.drawable.ic_like_fill))
        } else {
            collectTv.setImageDrawable(mContext.resources.getDrawable(R.drawable.ic_like))
        }
    }
}