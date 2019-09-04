package com.example.cde.network

import com.google.gson.annotations.SerializedName

data class Article(
    @SerializedName("date")
    val date: String?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("tag")
    val tag: String?,
    @SerializedName("thumbnail")
    val thumbnail: String?,
    @SerializedName("title")
    val title: String?
)