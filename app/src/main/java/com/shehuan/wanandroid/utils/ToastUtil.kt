package com.shehuan.wanandroid.utils

import android.content.Context
import android.widget.Toast

class ToastUtil {
    companion object {
        fun showToast(context: Context?, content: String?) {
            Toast.makeText(context, content, Toast.LENGTH_SHORT).show()
        }
    }
}