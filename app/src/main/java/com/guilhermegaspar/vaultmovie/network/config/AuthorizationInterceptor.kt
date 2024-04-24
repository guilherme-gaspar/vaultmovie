package com.guilhermegaspar.vaultmovie.network.config

import okhttp3.Interceptor
import okhttp3.Response

class AuthorizationInterceptor(
    private val authorizationToken: String = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJmZDMwYmE2ZDdkNGU5NjAzMDhhYTkxOGQ4NTQ0ZDA4ZSIsInN1YiI6IjY2MWQ0MTk0MGU1YWJhMDE4NmY1NmNiMyIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.TBK3XOu2WzQnUQqi5xjIeDELDm-DtAusLLZkowxgI4g"
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val newRequest = request.newBuilder()
            .addHeader("Authorization", authorizationToken)
            .build()
        return chain.proceed(newRequest)

    }
}