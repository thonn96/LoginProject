package com.datn.cookingguideapp.data.remote.interceptor

import android.util.Log
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import nexlsoft.loginsample.data.local.AppPrefKey
import nexlsoft.loginsample.data.local.AppSharedPreferences
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody

class InterceptorImpl(private val preferences: AppSharedPreferences) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = initializeRequestWithHeaders(chain.request())
        val response = chain.proceed(request)
        if (response.code == 401) preferences.clearKey(AppPrefKey.ACCESS_TOKEN)
        val responseBody = response.body
        val responseBodyString = response.body?.string()
        Log.d("====>log_response ${request.url}", responseBodyString ?: "")
        return createNewResponse(response, responseBody, responseBodyString)
    }

    private fun initializeRequestWithHeaders(request: Request): Request {
        var accessToken = preferences.loadToken()
        accessToken?.let { Log.d("====>log_request ${request.url}", it) }
        return request.newBuilder()
            .addHeader("Authorization", "Bearer $accessToken")
            .build()
    }

    private fun createNewResponse(
        response: Response,
        responseBody: ResponseBody?,
        responseBodyString: String?
    ): Response {
        val contentType = responseBody?.contentType()
        return response.newBuilder()
            .body((responseBodyString ?: "").toResponseBody(contentType))
            .build()
    }
}
