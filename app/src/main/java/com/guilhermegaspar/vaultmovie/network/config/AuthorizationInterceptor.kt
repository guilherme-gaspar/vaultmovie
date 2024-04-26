package com.guilhermegaspar.vaultmovie.network.config

import com.guilhermegaspar.vaultmovie.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class AuthorizationInterceptor(
    private val authorizationToken: String = BuildConfig.BEARER_TOKEN
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val newRequest = request.newBuilder()
            .addHeader("Authorization", authorizationToken)
            .build()
        return chain.proceed(newRequest)

    }
}