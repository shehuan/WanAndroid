package com.shehuan.wanandroid.base

import android.content.Context
import com.shehuan.wanandroid.base.fragment.BaseFragment
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BasePresenter<V : BaseView>(val view: V) {
    protected val context: Context = if (view is BaseFragment) {
        view.activity
    } else {
        view as Context
    }

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    fun addDisposable(disposable: Disposable) {
        if (!compositeDisposable.isDisposed) {
            compositeDisposable.add(disposable)
        }
    }

    fun removeDisposable(disposable: Disposable) {
        if (!compositeDisposable.isDisposed) {
            compositeDisposable.remove(disposable)
        }
    }

    fun detach() {
        if (!compositeDisposable.isDisposed) {
            compositeDisposable.clear()
        }
    }

}