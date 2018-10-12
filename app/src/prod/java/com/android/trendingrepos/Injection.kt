package com.android.trendingrepos

import com.android.trendingrepos.data.source.ReposRepository
import com.android.trendingrepos.data.source.remote.ApiUtils

object Injection {
    fun provideReposRepository(): ReposRepository {
        return ReposRepository.getInstance(ApiUtils.reposApiService)
    }
}