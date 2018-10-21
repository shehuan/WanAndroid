package com.shehuan.keasymvp.mvp.net

import android.content.Context
import android.widget.Toast
import com.shehuan.keasymvp.App
import com.shehuan.keasymvp.mvp.net.exception.ResponseException
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import java.lang.ref.WeakReference

abstract class BaseObserver<E>() : Observer<E> {
    private lateinit var wrContext: WeakReference<Context>

    private lateinit var disposable: Disposable

    var showErrorTip: Boolean = true

    constructor(showErrorTip: Boolean) : this() {
        this.showErrorTip = showErrorTip
        wrContext = WeakReference(App.getApp())
    }

    override fun onSubscribe(d: Disposable) {
        disposable = d
    }

    override fun onNext(data: E) {
        onSuccess(data)
    }

    override fun onError(e: Throwable) {
        val responseException: ResponseException = e as ResponseException
        if (showErrorTip) {
            Toast.makeText(wrContext.get(), responseException.getErrorMessage(), Toast.LENGTH_SHORT).show()
        }
        onError(responseException)
    }

    override fun onComplete() {

    }

    fun getDisposable() = disposable

    abstract fun onSuccess(data: E)

    abstract fun onError(e: ResponseException)
}