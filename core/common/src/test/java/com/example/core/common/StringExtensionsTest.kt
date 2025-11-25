package com.example.core.common

import com.example.core.common.extension.capitalizeFirst
import com.example.core.common.extension.isValidPhoneNumber
import com.example.core.common.extension.nullIfBlank
import com.example.core.common.extension.removeWhitespace
import com.example.core.common.extension.truncate
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class StringExtensionsTest {

    // Note: isValidEmail tests are in androidTest as they require Android Patterns class

    @Test
    fun `isValidPhoneNumber should return true for valid phone`() {
        assertTrue("1234567890".isValidPhoneNumber())
        assertTrue("+1234567890".isValidPhoneNumber())
        assertTrue("123-456-7890".isValidPhoneNumber())
    }

    @Test
    fun `isValidPhoneNumber should return false for invalid phone`() {
        assertFalse("123".isValidPhoneNumber())
        assertFalse("abcdefghij".isValidPhoneNumber())
    }

    @Test
    fun `capitalizeFirst should capitalize first character`() {
        assertEquals("Hello", "hello".capitalizeFirst())
        assertEquals("World", "world".capitalizeFirst())
        assertEquals("", "".capitalizeFirst())
    }

    @Test
    fun `truncate should truncate long strings`() {
        assertEquals("Hello...", "Hello World".truncate(8))
        assertEquals("Short", "Short".truncate(10))
    }

    @Test
    fun `nullIfBlank should return null for blank strings`() {
        assertNull("".nullIfBlank())
        assertNull("   ".nullIfBlank())
        assertEquals("test", "test".nullIfBlank())
    }

    @Test
    fun `removeWhitespace should remove all whitespace`() {
        assertEquals("HelloWorld", "Hello World".removeWhitespace())
        assertEquals("NoSpaces", "  No  Spaces  ".removeWhitespace())
    }
}
