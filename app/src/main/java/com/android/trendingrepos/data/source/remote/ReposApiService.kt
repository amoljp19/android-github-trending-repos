package com.android.trendingrepos.data.source.remote

import com.android.trendingrepos.data.Repo
import com.android.trendingrepos.data.source.ReposResponse
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ReposApiService {

    @GET("search/repositories")
    fun getRepos(@Query("q") searchTerm: String,
                 @Query("sort") sort: String,
                 @Query("order") order: String,
                 @Query("page") page: Int,
                 @Query("per_page") perPage: Int): Flowable<ReposResponse>

    @GET("repositories/{id}")
    fun getRepoById(@Path("id") repoId: Long): Flowable<Repo?>
}