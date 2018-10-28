package com.shehuan.wanandroid.adapter

import android.content.Context
import android.view.View
import android.widget.TextView
import com.othershe.baseadapter.ViewHolder
import com.othershe.baseadapter.base.CommonBaseAdapter
import com.shehuan.wanandroid.R
import com.shehuan.wanandroid.bean.article.DatasItem

class ArticleListAdapter(context: Context?, data: List<DatasItem>?, isOpenLoadMore: Boolean) :
        CommonBaseAdapter<DatasItem>(context, data, isOpenLoadMore) {
    override fun getItemLayoutId(): Int {
        return R.layout.article_rv_item_layout
    }

    override fun convert(viewHolder: ViewHolder, data: DatasItem, position: Int) {
        viewHolder.setText(R.id.articleTitleTv, data.title)
        val typeTv = viewHolder.getView<TextView>(R.id.articleTypeTv)
        if (data.tags.isNotEmpty()){
            typeTv.text = data.tags[0].name
            typeTv.visibility = View.VISIBLE
        }else{
            typeTv.visibility = View.GONE
        }
        viewHolder.setText(R.id.articleAuthorTv, data.author)
        viewHolder.setText(R.id.articleTimeTv, data.niceDate)
    }
}