package com.android.trendingrepos.data.source.remote.params

data class ReposParam(val searchTerm: String, val sort: Sort, val order: Order,
                      val page: Int, val perPage: Int) {
}