package com.shehuan.wanandroid.adapter

import android.content.Context
import com.othershe.baseadapter.ViewHolder
import com.othershe.baseadapter.base.CommonBaseAdapter
import com.shehuan.wanandroid.R
import com.shehuan.wanandroid.bean.project.DatasItem
import com.shehuan.wanandroid.utils.ImageLoader

class ProjectListAdapter(context: Context?, data: List<DatasItem>?, isOpenLoadMore: Boolean) :
        CommonBaseAdapter<DatasItem>(context, data, isOpenLoadMore) {
    override fun getItemLayoutId(): Int {
        return R.layout.rv_item_project_layout
    }

    override fun convert(viewHolder: ViewHolder, data: DatasItem, position: Int) {
        ImageLoader.load(mContext, data.envelopePic, viewHolder.getView(R.id.projectIv))
        viewHolder.setText(R.id.projectTitleTv, data.title)
        viewHolder.setText(R.id.projectDescTv, data.desc)
        viewHolder.setText(R.id.projectAuthorTv, data.author)
        viewHolder.setText(R.id.projectTimeTv, data.niceDate)
    }
}