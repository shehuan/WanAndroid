package com.shehuan.wanandroid.utils

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import com.google.android.flexbox.FlexboxLayout

fun FlexboxLayout.addCommonView(context: Context,
                                name: String, // 名称
                                @ColorRes textColor: Int, // 字体颜色
                                @DrawableRes drawable: Int, // 背景
                                append: Boolean = true, // 添加方式
                                viewClick: (View) -> Unit) { // 点击事件
    val view = TextView(context)
    view.setOnClickListener(viewClick)
    view.text = name
    view.setTextColor(context.resources.getColor(textColor))
    view.background = context.resources.getDrawable(drawable)
    val padding1 = CommonUtil.dp2px(context, 10)
    val padding2 = CommonUtil.dp2px(context, 5)
    view.setPadding(padding1, padding2, padding1, padding2)
    val params = FlexboxLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    val margin1 = CommonUtil.dp2px(context, 6)
    val margin2 = CommonUtil.dp2px(context, 10)
    params.setMargins(margin2, margin1, margin2, margin1)
    view.layoutParams = params
    if (append) {
        this.addView(view)
    } else {
        this.addView(view, 0)
    }
}

fun FlexboxLayout.childName(index: Int): String {
    val view: TextView = this.getChildAt(index) as TextView
    return view.text.toString()
}