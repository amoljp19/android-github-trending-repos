package com.android.trendingrepos.data.source

import com.android.trendingrepos.data.Repo
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ReposResponse {
    @SerializedName("items")
    @Expose
    var repos: List<Repo> = listOf()
}