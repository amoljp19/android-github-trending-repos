package com.android.trendingrepos.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Owner {
    @SerializedName("avatar_url")
    @Expose
    var avatarUrl: String? = null
}