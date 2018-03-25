package com.jollypanda.petrsudoors.utils.extension

import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers.mainThread
import io.reactivex.schedulers.Schedulers.io
import retrofit2.Response

/**
 * @author Yamushev Igor
 * @since  25.03.18
 */
fun <T> Single<Response<T>>.asRetrofitResponse() = this
    .map {
        if (!it.isSuccessful)
            throw Exception()
        it
    }
    .subscribeOn(io())
    .observeOn(mainThread())

fun <T> Single<Response<T>>.asRetrofitBody() =
        this.asRetrofitResponse().map { it.body()!! }!!