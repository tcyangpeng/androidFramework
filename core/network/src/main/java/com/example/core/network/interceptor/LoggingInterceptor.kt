package com.example.core.network.interceptor

import com.example.core.common.util.LogUtils
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import java.io.IOException
import javax.inject.Inject

/**
 * Interceptor for logging network requests and responses.
 */
class LoggingInterceptor @Inject constructor() : Interceptor {

    companion object {
        private const val TAG = "NetworkLog"
    }

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        
        // Log request
        LogUtils.d("┌────── Request ──────────────────────────────────────────────", TAG)
        LogUtils.d("│ URL: ${request.url}", TAG)
        LogUtils.d("│ Method: ${request.method}", TAG)
        LogUtils.d("│ Headers: ${request.headers}", TAG)
        LogUtils.d("└────────────────────────────────────────────────────────────", TAG)

        val startTime = System.currentTimeMillis()
        val response: Response
        try {
            response = chain.proceed(request)
        } catch (e: Exception) {
            LogUtils.e("┌────── Error ───────────────────────────────────────────────", TAG)
            LogUtils.e("│ ${e.message}", TAG)
            LogUtils.e("└────────────────────────────────────────────────────────────", TAG)
            throw e
        }
        val endTime = System.currentTimeMillis()

        // Log response
        val responseBody = response.body
        val contentType = responseBody?.contentType()
        val content = responseBody?.string() ?: ""
        
        LogUtils.d("┌────── Response (${endTime - startTime}ms) ────────────────────────────────", TAG)
        LogUtils.d("│ URL: ${request.url}", TAG)
        LogUtils.d("│ Code: ${response.code}", TAG)
        LogUtils.d("│ Message: ${response.message}", TAG)
        if (content.length > 500) {
            LogUtils.d("│ Body: ${content.take(500)}...", TAG)
        } else {
            LogUtils.d("│ Body: $content", TAG)
        }
        LogUtils.d("└────────────────────────────────────────────────────────────", TAG)

        // Rebuild response with the consumed body
        return response.newBuilder()
            .body(content.toResponseBody(contentType))
            .build()
    }
}
