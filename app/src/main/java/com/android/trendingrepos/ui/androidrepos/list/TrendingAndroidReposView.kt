package com.android.trendingrepos.ui.androidrepos.list

import com.android.trendingrepos.data.Repo
import com.android.trendingrepos.ui.base.BaseView

interface TrendingAndroidReposView : BaseView {
    fun showRepos(repos: List<Repo>)
}