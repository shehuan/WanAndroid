package com.shehuan.wanandroid.adapter

import android.content.Context
import com.othershe.baseadapter.ViewHolder
import com.othershe.baseadapter.base.CommonBaseAdapter
import com.shehuan.wanandroid.R
import com.shehuan.wanandroid.bean.article.DatasItem

class CollectionListAdapter(context: Context?, data: List<DatasItem>?, isOpenLoadMore: Boolean) :
        CommonBaseAdapter<DatasItem>(context, data, isOpenLoadMore) {
    override fun getItemLayoutId(): Int {
        return R.layout.rv_item_collection_layout
    }

    override fun convert(viewHolder: ViewHolder, data: DatasItem, position: Int) {
        viewHolder.setText(R.id.articleTitleTv, data.title)
        viewHolder.setText(R.id.articleAuthorTv, data.author)
        viewHolder.setText(R.id.collectTimeTv, data.niceDate)
    }
}