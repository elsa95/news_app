package com.example.cde.network

class API {
    companion object {
        private const val BASE_URL = "https://s3.amazonaws.com/"
        private const val BASE_API_URL = BASE_URL +"lpolepeddi/projects/gcp-fe/"

        fun url(endpoint: String): String {
            return "$BASE_API_URL$endpoint"
        }
    }
}