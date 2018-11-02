package com.shehuan.wanandroid.widget

import com.shehuan.nicedialog.BaseNiceDialog
import com.shehuan.nicedialog.ViewHolder
import com.shehuan.wanandroid.R

class LoadingDialog : BaseNiceDialog() {
    override fun convertView(p0: ViewHolder?, p1: BaseNiceDialog?) {
        setDimAmount(0f)
        setOutCancel(false)
    }

    override fun intLayoutId(): Int {
        return R.layout.dialog_loading_layout
    }

    override fun initTheme(): Int {
        return R.style.MyDialog
    }

    companion object {
        fun newInstance(): LoadingDialog {
            return LoadingDialog()
        }
    }
}