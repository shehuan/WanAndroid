package com.shehuan.keasymvp.base.net.observer

import android.content.Context
import android.support.v7.app.AppCompatActivity
import com.othershe.nicedialog.BaseNiceDialog
import com.shehuan.keasymvp.base.LoadingDialog
import io.reactivex.disposables.Disposable
import java.lang.ref.WeakReference

abstract class LoadingObserver<E>(context: Context, showLoading: Boolean, showErrorTip: Boolean) : BaseObserver<E>(showErrorTip) {
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