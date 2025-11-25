package com.example.core.common.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

/**
 * Utility class for date and time operations.
 */
object DateUtils {
    
    private const val DEFAULT_DATE_FORMAT = "yyyy-MM-dd"
    private const val DEFAULT_DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss"
    private const val DISPLAY_DATE_FORMAT = "MMM dd, yyyy"
    private const val DISPLAY_TIME_FORMAT = "HH:mm"
    
    /**
     * Formats a timestamp to a date string.
     */
    fun formatDate(timestamp: Long, pattern: String = DEFAULT_DATE_FORMAT): String {
        val sdf = SimpleDateFormat(pattern, Locale.getDefault())
        return sdf.format(Date(timestamp))
    }
    
    /**
     * Formats a timestamp to a datetime string.
     */
    fun formatDateTime(timestamp: Long, pattern: String = DEFAULT_DATETIME_FORMAT): String {
        val sdf = SimpleDateFormat(pattern, Locale.getDefault())
        return sdf.format(Date(timestamp))
    }
    
    /**
     * Formats a timestamp to a display-friendly date string.
     */
    fun formatDisplayDate(timestamp: Long): String {
        val sdf = SimpleDateFormat(DISPLAY_DATE_FORMAT, Locale.getDefault())
        return sdf.format(Date(timestamp))
    }
    
    /**
     * Formats a timestamp to a display-friendly time string.
     */
    fun formatDisplayTime(timestamp: Long): String {
        val sdf = SimpleDateFormat(DISPLAY_TIME_FORMAT, Locale.getDefault())
        return sdf.format(Date(timestamp))
    }
    
    /**
     * Parses a date string to a timestamp.
     */
    fun parseDate(dateString: String, pattern: String = DEFAULT_DATE_FORMAT): Long? {
        return try {
            val sdf = SimpleDateFormat(pattern, Locale.getDefault())
            sdf.parse(dateString)?.time
        } catch (e: Exception) {
            null
        }
    }
    
    /**
     * Gets the current timestamp.
     */
    fun getCurrentTimestamp(): Long = System.currentTimeMillis()
    
    /**
     * Checks if two timestamps are on the same day.
     */
    fun isSameDay(timestamp1: Long, timestamp2: Long): Boolean {
        val sdf = SimpleDateFormat(DEFAULT_DATE_FORMAT, Locale.getDefault())
        return sdf.format(Date(timestamp1)) == sdf.format(Date(timestamp2))
    }
    
    /**
     * Converts a UTC timestamp to local timezone.
     */
    fun utcToLocal(utcTimestamp: Long): Long {
        val offset = TimeZone.getDefault().getOffset(utcTimestamp)
        return utcTimestamp + offset
    }
    
    /**
     * Converts a local timestamp to UTC.
     */
    fun localToUtc(localTimestamp: Long): Long {
        val offset = TimeZone.getDefault().getOffset(localTimestamp)
        return localTimestamp - offset
    }
}
