package com.example.cde.network

import okhttp3.OkHttpClient
import okhttp3.Request

class Request {
    companion object {
        private val mHttpClient: OkHttpClient = OkHttpClient()
    }

    fun getArticles(callback: Callback) {
        val request = Request.Builder()
            .url(API.url("data.json"))
            .build()

        mHttpClient.newCall(request).enqueue(callback)
    }
}