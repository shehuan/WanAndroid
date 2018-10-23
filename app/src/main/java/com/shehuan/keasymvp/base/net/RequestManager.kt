package com.shehuan.keasymvp.base.net

import com.shehuan.keasymvp.base.BasePresenter
import com.shehuan.keasymvp.base.BaseResponse
import com.shehuan.keasymvp.base.net.convert.ExceptionConvert
import com.shehuan.keasymvp.base.net.convert.ResponseConvert
import com.shehuan.keasymvp.base.net.observer.BaseObserver
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
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

    /**
     * 通用耗时任务方法
     */
    fun <E> execute(presenter: BasePresenter<*>, listener: ExecuteListener<E>, observer: BaseObserver<E>): Disposable {
        Observable.create(ObservableOnSubscribe<E> { emitter ->
            emitter.onNext(listener.onExecute())
            emitter.onComplete()
        }).onErrorResumeNext(ExceptionConvert<E>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer)
        presenter.addDisposable(observer.getDisposable())
        return observer.getDisposable()
    }

    interface ExecuteListener<E> {
        fun onExecute(): E
    }

}