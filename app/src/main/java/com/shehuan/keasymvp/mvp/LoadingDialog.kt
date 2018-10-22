package com.shehuan.keasymvp.mvp

import com.othershe.nicedialog.BaseNiceDialog
import com.othershe.nicedialog.ViewHolder
import com.shehuan.keasymvp.R

class LoadingDialog : BaseNiceDialog() {
    override fun convertView(p0: ViewHolder?, p1: BaseNiceDialog?) {
        setDimAmount(0f)
        setOutCancel(false)
    }

    override fun intLayoutId(): Int {
        return R.layout.loading_dialog_layout
    }

    companion object {
        fun newInstance(): LoadingDialog {
            return LoadingDialog()
        }
    }
}