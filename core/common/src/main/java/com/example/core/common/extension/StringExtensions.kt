package com.example.core.common.extension

/**
 * Extension functions for String.
 */

/**
 * Returns true if the string is a valid email format.
 */
fun String.isValidEmail(): Boolean {
    val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$".toRegex()
    return this.matches(emailRegex)
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
