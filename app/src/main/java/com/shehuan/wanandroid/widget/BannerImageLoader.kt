package com.shehuan.wanandroid.widget

import android.content.Context
import android.widget.ImageView
import com.youth.banner.loader.ImageLoader


class BannerImageLoader : ImageLoader() {
    override fun displayImage(context: Context, path: Any?, imageView: ImageView) {
        com.shehuan.wanandroid.utils.ImageLoader.load(context, path as String, imageView)
    }
}