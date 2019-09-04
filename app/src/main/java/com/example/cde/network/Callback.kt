package com.example.cde.network

import android.os.Handler
import android.os.Looper
import okhttp3.Call
import okhttp3.Response
import java.io.IOException

// Custom Callback class to handle Failure and Response Cases of the network request
abstract class Callback: okhttp3.Callback {

    override fun onFailure(call: Call, e: IOException) {
        Handler(Looper.getMainLooper()).post {
            onResponse(null, null, e)
        }
    }

    override fun onResponse(call: Call, response: Response) {
        val responseBody = getResponseBody(response)
        Handler(Looper.getMainLooper()).post {
            onResponse(response, responseBody, null)
        }
    }

    abstract fun onResponse(response: Response?, stringBody: String?, exception: IOException?)

    private fun getResponseBody(response: Response): String {
        return try {
            response.body()!!.string()
        } catch (e: IOException) {
            ""
        }
    }
}