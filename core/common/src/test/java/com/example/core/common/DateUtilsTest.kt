package com.example.core.common

import com.example.core.common.util.DateUtils
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import java.util.Calendar

class DateUtilsTest {

    @Test
    fun `formatDate should format timestamp correctly`() {
        // 2024-01-15 in milliseconds (using a fixed timestamp)
        val timestamp = 1705276800000L // 2024-01-15 00:00:00 UTC
        val formatted = DateUtils.formatDate(timestamp, "yyyy-MM-dd")
        
        assertTrue(formatted.matches(Regex("\\d{4}-\\d{2}-\\d{2}")))
    }

    @Test
    fun `formatDisplayDate should return readable date`() {
        val timestamp = System.currentTimeMillis()
        val formatted = DateUtils.formatDisplayDate(timestamp)
        
        assertNotNull(formatted)
        assertTrue(formatted.isNotEmpty())
    }

    @Test
    fun `formatDisplayTime should return time format`() {
        val timestamp = System.currentTimeMillis()
        val formatted = DateUtils.formatDisplayTime(timestamp)
        
        assertTrue(formatted.matches(Regex("\\d{2}:\\d{2}")))
    }

    @Test
    fun `parseDate should parse date string correctly`() {
        val dateString = "2024-01-15"
        val timestamp = DateUtils.parseDate(dateString)
        
        assertNotNull(timestamp)
    }

    @Test
    fun `getCurrentTimestamp should return current time`() {
        val before = System.currentTimeMillis()
        val timestamp = DateUtils.getCurrentTimestamp()
        val after = System.currentTimeMillis()
        
        assertTrue(timestamp in before..after)
    }

    @Test
    fun `isSameDay should return true for same day`() {
        val timestamp1 = System.currentTimeMillis()
        val timestamp2 = timestamp1 + 1000 // 1 second later
        
        assertTrue(DateUtils.isSameDay(timestamp1, timestamp2))
    }

    @Test
    fun `isSameDay should return false for different days`() {
        val timestamp1 = System.currentTimeMillis()
        val timestamp2 = timestamp1 + (25 * 60 * 60 * 1000) // 25 hours later
        
        assertFalse(DateUtils.isSameDay(timestamp1, timestamp2))
    }
}
