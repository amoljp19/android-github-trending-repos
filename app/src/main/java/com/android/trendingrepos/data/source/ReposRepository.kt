package com.android.trendingrepos.data.source

import com.android.trendingrepos.data.Repo
import com.android.trendingrepos.data.source.remote.ReposApiService
import com.android.trendingrepos.data.source.remote.params.Order
import com.android.trendingrepos.data.source.remote.params.ReposParam
import com.android.trendingrepos.data.source.remote.params.Sort
import io.reactivex.Flowable
import java.lang.ref.SoftReference
import java.util.*


class ReposRepository(private val reposApi: ReposApiService) {

    private var cachedRepos: SoftReference<LinkedHashMap<Long, Repo>> = SoftReference(LinkedHashMap())

    fun getTrendingAndroidRepos(page: Int, perPage: Int): Flowable<List<Repo>> {
        if (cachedRepos.get() == null) {
            cachedRepos = SoftReference(LinkedHashMap())
        }

        return getAndCacheRemoteRepos(ReposParam("android",
                Sort.STARS, Order.DESCENDING, page, perPage))
    }

    fun getRepo(repoId: Long): Flowable<Repo?> {
        val cachedRepo = getCachedRepoWithId(repoId)

        if (cachedRepo != null) {
            return Flowable.just(cachedRepo)
        }

        return reposApi.getRepoById(repoId)
    }

    fun refreshRepos() {
        cachedRepos.clear()
    }

    private fun getAndCacheRemoteRepos(param: ReposParam): Flowable<List<Repo>> {
        return reposApi
                .getRepos(param.searchTerm, param.sort.value, param.order.value, param.page, param.perPage)
                .flatMap { response ->
                    Flowable.fromIterable(response.repos).doOnNext { repo ->
                        cachedRepos.get()?.put(repo.id, repo)
                    }.toList().toFlowable()
                }
    }

    private fun getCachedRepoWithId(id: Long) = cachedRepos.get()?.get(id)


    companion object {

        private var INSTANCE: ReposRepository? = null

        @JvmStatic fun getInstance(reposApi: ReposApiService): ReposRepository {
            return INSTANCE ?: ReposRepository(reposApi)
                    .apply { INSTANCE = this }
        }
    }
}