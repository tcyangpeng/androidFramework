package com.example.core.common.util

import android.util.Log

/**
 * Utility class for logging with configurable log levels.
 */
object LogUtils {
    
    private const val DEFAULT_TAG = "AndroidFramework"
    
    var isDebugEnabled: Boolean = true
    
    /**
     * Logs a debug message.
     */
    fun d(message: String, tag: String = DEFAULT_TAG) {
        if (isDebugEnabled) {
            Log.d(tag, message)
        }
    }
    
    /**
     * Logs an info message.
     */
    fun i(message: String, tag: String = DEFAULT_TAG) {
        Log.i(tag, message)
    }
    
    /**
     * Logs a warning message.
     */
    fun w(message: String, tag: String = DEFAULT_TAG) {
        Log.w(tag, message)
    }
    
    /**
     * Logs a warning message with throwable.
     */
    fun w(message: String, throwable: Throwable, tag: String = DEFAULT_TAG) {
        Log.w(tag, message, throwable)
    }
    
    /**
     * Logs an error message.
     */
    fun e(message: String, tag: String = DEFAULT_TAG) {
        Log.e(tag, message)
    }
    
    /**
     * Logs an error message with throwable.
     */
    fun e(message: String, throwable: Throwable, tag: String = DEFAULT_TAG) {
        Log.e(tag, message, throwable)
    }
    
    /**
     * Logs a verbose message.
     */
    fun v(message: String, tag: String = DEFAULT_TAG) {
        if (isDebugEnabled) {
            Log.v(tag, message)
        }
    }
}
