package com.android.trendingrepos.ui.androidrepos.detail

import com.android.trendingrepos.data.source.ReposRepository
import com.android.trendingrepos.ui.base.BasePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers


class RepoDetailPresenter(private val repository: ReposRepository)
    : BasePresenter<RepoDetailView>() {

    private var disposable: Disposable? = null

    override fun detachView() {
        super.detachView()
        disposable?.dispose()
    }

    fun loadRepo(repoId: Long) {
        view?.showProgress(true)
        disposable?.dispose()
        disposable = repository.getRepo(repoId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        // onNext
                        { repo ->
                            view?.showProgress(false)
                            if (repo == null) {
                                view?.showNoRepo()
                            } else {
                                view?.showRepo(repo)
                            }
                        },
                        // onError
                        { throwable ->
                            view?.showProgress(false)
                            view?.showError(throwable.localizedMessage)
                        })
    }
}