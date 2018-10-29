package com.shehuan.wanandroid.adapter

import android.content.Context
import com.othershe.baseadapter.ViewHolder
import com.othershe.baseadapter.base.CommonBaseAdapter
import com.shehuan.wanandroid.R
import com.shehuan.wanandroid.bean.navi.ArticlesItem

class NavDetailAdapter(context: Context?, data: List<ArticlesItem>?, isOpenLoadMore: Boolean) :
        CommonBaseAdapter<ArticlesItem>(context, data, isOpenLoadMore) {

    override fun getItemLayoutId(): Int {
        return R.layout.rv_item_nav_detail_layout
    }

    override fun convert(viewHolder: ViewHolder, data: ArticlesItem, position: Int) {
        viewHolder.setText(R.id.naviDetailNameTv, data.title)
    }
}