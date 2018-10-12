package com.android.trendingrepos.ui.androidrepos.list

import com.android.trendingrepos.data.source.ReposRepository
import com.android.trendingrepos.ui.base.BasePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers


class TrendingAndroidReposPresenter(private val repository: ReposRepository)
    : BasePresenter<TrendingAndroidReposView>() {

    companion object {
        const val PAGE_SIZE = 50
    }

    private var firstLoad = true
    private var disposable: Disposable? = null
    private var page = 1
    private var isLoading = false
	private var isLastPage = false

    override fun detachView() {
        super.detachView()
        disposable?.dispose()
    }

    fun loadRepos(forceUpdate: Boolean) {
        checkViewAttached()
        loadRepos(forceUpdate || firstLoad, true)
        firstLoad = false
    }

    fun loadMoreRepos() {
        if (canLoadMoreItems()) {
            checkViewAttached()
            page++
            loadRepos(false)
        }
    }

    private fun loadRepos(forceUpdate: Boolean, showLoadingUI: Boolean) {
        if (showLoadingUI) {
            view?.showProgress(true)
        }
        if (forceUpdate) {
            page = 1
            repository.refreshRepos()
        }

        disposable?.dispose()
        isLoading = true
        disposable = repository.getTrendingAndroidRepos(page, PAGE_SIZE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        // onNext
                        { repos ->
                            view?.showProgress(false)
                            view?.showRepos(repos)
                            isLoading = false
                            isLastPage = repos.size < PAGE_SIZE
                        },
                        // onError
                        { throwable ->
                            view?.showProgress(false)
                            view?.showError(throwable.localizedMessage)
                            isLoading = false})
    }

    private fun canLoadMoreItems() = !isLoading && !isLastPage
}