package com.example.core.common

import com.example.core.common.result.Result
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class ResultTest {

    @Test
    fun `Success result should return correct data`() {
        val data = "test data"
        val result = Result.Success(data)

        assertTrue(result.isSuccess)
        assertFalse(result.isError)
        assertFalse(result.isLoading)
        assertEquals(data, result.getOrNull())
    }

    @Test
    fun `Error result should return exception`() {
        val exception = Exception("test error")
        val result = Result.Error(exception = exception, message = "test error")

        assertFalse(result.isSuccess)
        assertTrue(result.isError)
        assertFalse(result.isLoading)
        assertNull(result.getOrNull())
        assertEquals(exception, result.exceptionOrNull())
    }

    @Test
    fun `Loading result should be loading`() {
        val result = Result.Loading

        assertFalse(result.isSuccess)
        assertFalse(result.isError)
        assertTrue(result.isLoading)
    }

    @Test
    fun `map should transform success data`() {
        val result = Result.Success(10)
        val mapped = result.map { it * 2 }

        assertTrue(mapped.isSuccess)
        assertEquals(20, (mapped as Result.Success).data)
    }

    @Test
    fun `map should pass through error`() {
        val result: Result<Int> = Result.Error(message = "error")
        val mapped = result.map { it * 2 }

        assertTrue(mapped.isError)
    }

    @Test
    fun `getOrDefault should return default on error`() {
        val result: Result<Int> = Result.Error(message = "error")
        val value = result.getOrDefault(42)

        assertEquals(42, value)
    }

    @Test
    fun `getOrDefault should return data on success`() {
        val result = Result.Success(10)
        val value = result.getOrDefault(42)

        assertEquals(10, value)
    }
}
