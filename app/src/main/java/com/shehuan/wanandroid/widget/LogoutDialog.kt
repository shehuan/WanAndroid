package com.shehuan.wanandroid.widget

import android.widget.TextView
import androidx.fragment.app.FragmentManager
import com.shehuan.nicedialog.BaseNiceDialog
import com.shehuan.nicedialog.ViewHolder
import com.shehuan.wanandroid.R

class LogoutDialog : BaseNiceDialog() {
    override fun convertView(viewHolder: ViewHolder, dialog: BaseNiceDialog) {
        setMargin(60)
        with(viewHolder) {
            getView<TextView>(R.id.logoutCancelBtn).setOnClickListener {
                dialog.dismiss()
            }

            getView<TextView>(R.id.logoutOkBtn).setOnClickListener {
                listener.logout()
                dialog.dismiss()
            }
        }
    }

    override fun intLayoutId(): Int {
        return R.layout.dialog_logout_layout
    }

    companion object {
        fun show(fragmentManager: FragmentManager, listener: OnLogoutListener) {
            LogoutDialog().setOnLogoutListener(listener).show(fragmentManager)
        }
    }

    private lateinit var listener: OnLogoutListener

    fun setOnLogoutListener(listener: OnLogoutListener): LogoutDialog {
        this.listener = listener
        return this
    }

    interface OnLogoutListener {
        fun logout()
    }
}