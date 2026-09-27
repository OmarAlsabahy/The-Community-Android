package com.tawajood.the_community_user.data.remote

import com.tawajood.the_community_user.data.app_pref.AppPreferences
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val appPreferences: AppPreferences
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()

        requestBuilder.addHeader(LANG_KEY, appPreferences.language)
        requestBuilder.addHeader(ACCEPT_KEY, ACCEPT)

        if (appPreferences.token != null) {
            requestBuilder.addHeader(AUTHORIZATION, "Bearer ${appPreferences.token}")
        }

        return chain.proceed(requestBuilder.build())
    }

    companion object {
        private const val ACCEPT_KEY = "accept"
        private const val LANG_KEY = "Accept-Language"
        private const val ACCEPT = "application/json"
        private const val AUTHORIZATION = "Authorization"
    }
}
