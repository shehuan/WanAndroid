package com.shehuan.keasymvp.base.net.convert

import com.shehuan.keasymvp.base.net.exception.ExceptionHandler
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.functions.Function

class ExceptionConvert<E> : Function<Throwable, ObservableSource<E>> {
    override fun apply(throwable: Throwable): ObservableSource<E> {
        return Observable.error(ExceptionHandler.handle(throwable))
    }
}