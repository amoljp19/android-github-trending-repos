package com.android.trendingrepos.ui.androidrepos.detail

import com.android.trendingrepos.data.Repo
import com.android.trendingrepos.ui.base.BaseView

interface RepoDetailView : BaseView {
    fun showRepo(repo: Repo)
    fun showNoRepo()
}