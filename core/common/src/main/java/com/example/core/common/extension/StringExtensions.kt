package com.example.core.common.extension

import android.util.Patterns

/**
 * Extension functions for String.
 */

/**
 * Returns true if the string is a valid email format.
 * Uses Android's built-in Patterns.EMAIL_ADDRESS for comprehensive validation.
 */
fun String.isValidEmail(): Boolean {
    return this.isNotBlank() && Patterns.EMAIL_ADDRESS.matcher(this).matches()
}

/**
 * Returns true if the string is a valid phone number format.
 */
fun String.isValidPhoneNumber(): Boolean {
    val phoneRegex = "^[+]?[0-9]{10,13}$".toRegex()
    return this.replace("-", "").replace(" ", "").matches(phoneRegex)
}

/**
 * Capitalizes the first letter of the string.
 */
fun String.capitalizeFirst(): String {
    return if (isNotEmpty()) {
        this[0].uppercaseChar() + substring(1)
    } else {
        this
    }
}

/**
 * Truncates the string to the specified length and adds ellipsis if necessary.
 */
fun String.truncate(maxLength: Int, ellipsis: String = "..."): String {
    return if (length > maxLength) {
        take(maxLength - ellipsis.length) + ellipsis
    } else {
        this
    }
}

/**
 * Returns null if the string is blank, otherwise returns the string.
 */
fun String?.nullIfBlank(): String? {
    return if (isNullOrBlank()) null else this
}

/**
 * Removes all whitespace from the string.
 */
fun String.removeWhitespace(): String {
    return replace("\\s".toRegex(), "")
}
