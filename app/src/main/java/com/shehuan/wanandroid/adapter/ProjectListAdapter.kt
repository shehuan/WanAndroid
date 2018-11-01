package com.shehuan.wanandroid.adapter

import android.content.Context
import android.text.Html
import android.widget.ImageView
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
        viewHolder.setText(R.id.projectTitleTv, Html.fromHtml(data.title).toString())
        viewHolder.setText(R.id.projectDescTv, data.desc)
        viewHolder.setText(R.id.projectAuthorTv, data.author)
        viewHolder.setText(R.id.projectTimeTv, data.niceDate)

        val collectTv = viewHolder.getView<ImageView>(R.id.projectCollectIv)

        if (data.collect) {
            collectTv.setImageDrawable(mContext.resources.getDrawable(R.drawable.ic_like_fill))
        } else {
            collectTv.setImageDrawable(mContext.resources.getDrawable(R.drawable.ic_like))
        }
    }
}