package com.example.core.network.interceptor

import com.example.core.common.util.LogUtils
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import java.io.IOException
import javax.inject.Inject

/**
 * Interceptor for logging network requests and responses.
 * Only logs in debug mode to protect sensitive data in production.
 */
class LoggingInterceptor @Inject constructor() : Interceptor {

    companion object {
        private const val TAG = "NetworkLog"
        private const val MAX_BODY_LOG_LENGTH = 500
    }

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        
        // Only log in debug mode
        if (LogUtils.isDebugEnabled) {
            LogUtils.d("┌────── Request ──────────────────────────────────────────────", TAG)
            LogUtils.d("│ URL: ${request.url}", TAG)
            LogUtils.d("│ Method: ${request.method}", TAG)
            // Avoid logging sensitive headers like Authorization
            LogUtils.d("│ Headers: [${request.headers.size} headers]", TAG)
            LogUtils.d("└────────────────────────────────────────────────────────────", TAG)
        }

        val startTime = System.currentTimeMillis()
        val response: Response
        try {
            response = chain.proceed(request)
        } catch (e: Exception) {
            if (LogUtils.isDebugEnabled) {
                LogUtils.e("┌────── Error ───────────────────────────────────────────────", TAG)
                LogUtils.e("│ ${e.message}", TAG)
                LogUtils.e("└────────────────────────────────────────────────────────────", TAG)
            }
            throw e
        }
        val endTime = System.currentTimeMillis()

        // Log response only in debug mode
        val responseBody = response.body
        val contentType = responseBody?.contentType()
        val content = responseBody?.string() ?: ""
        
        if (LogUtils.isDebugEnabled) {
            LogUtils.d("┌────── Response (${endTime - startTime}ms) ────────────────────────────────", TAG)
            LogUtils.d("│ URL: ${request.url}", TAG)
            LogUtils.d("│ Code: ${response.code}", TAG)
            LogUtils.d("│ Message: ${response.message}", TAG)
            if (content.length > MAX_BODY_LOG_LENGTH) {
                LogUtils.d("│ Body: ${content.take(MAX_BODY_LOG_LENGTH)}...", TAG)
            } else {
                LogUtils.d("│ Body: $content", TAG)
            }
            LogUtils.d("└────────────────────────────────────────────────────────────", TAG)
        }

        // Rebuild response with the consumed body
        return response.newBuilder()
            .body(content.toResponseBody(contentType))
            .build()
    }
}
