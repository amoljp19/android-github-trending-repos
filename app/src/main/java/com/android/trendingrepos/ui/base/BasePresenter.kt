package com.android.trendingrepos.ui.base

abstract class BasePresenter<V> : Presenter<V> {
    protected var view: V? = null

    override fun attachView(view: V) {
        this.view = view
    }

    override fun detachView() {
        view = null
    }

    fun checkViewAttached() {
        if (!isViewAttached()) throw ViewNotAttachedException()
    }

    private fun isViewAttached(): Boolean {
        return view != null
    }

    private class ViewNotAttachedException internal constructor()
        : RuntimeException("Please call Presenter.attachView(view) before requesting data to the Presenter")
}