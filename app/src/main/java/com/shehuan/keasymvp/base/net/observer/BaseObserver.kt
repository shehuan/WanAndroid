package com.shehuan.keasymvp.base.net.observer

import android.content.Context
import com.shehuan.keasymvp.App
import com.shehuan.keasymvp.base.net.exception.ResponseException
import com.shehuan.keasymvp.test.utils.ToastUtil
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import java.lang.ref.WeakReference

abstract class BaseObserver<E>(private val showErrorTip: Boolean) : Observer<E> {
    private val wrContext: WeakReference<Context> = WeakReference(App.getApp())

    private lateinit var disposable: Disposable

    override fun onSubscribe(d: Disposable) {
        disposable = d
    }

    override fun onNext(data: E) {
        onSuccess(data)
    }

    override fun onError(e: Throwable) {
        val responseException: ResponseException = e as ResponseException
        if (showErrorTip) {
            ToastUtil.showToast(wrContext.get(), responseException.getErrorMessage())
        }
        onError(responseException)
    }

    override fun onComplete() {

    }

    fun getDisposable() = disposable

    abstract fun onSuccess(data: E)

    abstract fun onError(e: ResponseException)
}