package com.android.trendingrepos.data.source.remote

object ApiUtils {

    private const val BASE_URL = "https://api.github.com/"

    val reposApiService: ReposApiService
        get() = RetrofitClient.getClient(BASE_URL).create(ReposApiService::class.java)
}