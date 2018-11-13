package com.shehuan.wanandroid.base.net.observer

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.shehuan.nicedialog.BaseNiceDialog
import com.shehuan.wanandroid.widget.LoadingDialog
import io.reactivex.disposables.Disposable
import java.lang.ref.WeakReference

abstract class LoadingObserver<E>(context: Context, showLoading: Boolean = true, showErrorTip: Boolean = true) : BaseObserver<E>(showErrorTip) {
    private val wrContext: WeakReference<Context> = WeakReference(context)

    private var loadingDialog: BaseNiceDialog? = null

    init {
        if (showLoading)
            loadingDialog = initLoading()
    }

    override fun onSubscribe(d: Disposable) {
        showLoading()
        super.onSubscribe(d)
    }

    override fun onError(e: Throwable) {
        dismissLoading()
        super.onError(e)
    }

    override fun onNext(data: E) {
        dismissLoading()
        super.onNext(data)
    }

    /**
     * 初始化loading
     */
    private fun initLoading(): BaseNiceDialog {
        return LoadingDialog.newInstance()
    }

    /**
     * 显示loading
     */
    private fun showLoading() {
        loadingDialog?.show((wrContext.get() as AppCompatActivity).supportFragmentManager)
    }

    /**
     * 取消loading
     */
    private fun dismissLoading() {
        loadingDialog?.dismiss()

        wrContext.clear()
    }
}