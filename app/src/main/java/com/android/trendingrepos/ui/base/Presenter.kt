package com.android.trendingrepos.ui.base

interface Presenter<V> {
    fun attachView(view: V)
    fun detachView()
}