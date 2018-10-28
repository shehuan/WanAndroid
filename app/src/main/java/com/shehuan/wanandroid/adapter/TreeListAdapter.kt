package com.shehuan.wanandroid.adapter

import android.content.Context
import com.othershe.baseadapter.ViewHolder
import com.othershe.baseadapter.base.CommonBaseAdapter
import com.shehuan.wanandroid.R
import com.shehuan.wanandroid.bean.tree.TreeBean

class TreeListAdapter(context: Context?, data: List<TreeBean>?, isOpenLoadMore: Boolean) :
        CommonBaseAdapter<TreeBean>(context, data, isOpenLoadMore) {
    override fun getItemLayoutId(): Int {
        return R.layout.tree_rv_item_layout
    }

    override fun convert(viewHolder: ViewHolder, data: TreeBean, position: Int) {
        viewHolder.setText(R.id.treeTitleTv, data.name)
    }
}