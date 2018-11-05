package com.shehuan.wanandroid.adapter

import android.content.Context
import android.text.Html
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
        with(viewHolder){
            setText(R.id.articleTitleTv, Html.fromHtml(data.title).toString())
            setText(R.id.articleAuthorTv, data.author)
            setText(R.id.collectTimeTv, data.niceDate)
        }
    }
}