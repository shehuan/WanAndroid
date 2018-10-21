package com.shehuan.keasymvp.mvp.net

import com.shehuan.keasymvp.mvp.BasePresenter
import com.shehuan.keasymvp.mvp.BaseResponse
import com.shehuan.keasymvp.mvp.net.convert.ExceptionConvert
import com.shehuan.keasymvp.mvp.net.convert.ResponseConvert
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

object RequestManager {
    /**
     * 通用网络请求方法
     */
    fun <E> execute(presenter: BasePresenter<*>, observable: Observable<BaseResponse<E>>, observer: BaseObserver<E>): Disposable {
        observable
                .map(ResponseConvert<E>())
                .onErrorResumeNext(ExceptionConvert<E>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer)
        presenter.addDisposable(observer.getDisposable())
        return observer.getDisposable()
    }


}