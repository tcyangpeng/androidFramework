package com.example.core.common.result

/**
 * A sealed class representing a Result that can be either Success, Error, or Loading.
 * This is used to implement unidirectional data flow pattern.
 */
sealed class Result<out T> {
    
    /**
     * Represents a successful result with data.
     */
    data class Success<T>(val data: T) : Result<T>()
    
    /**
     * Represents an error result with an exception and optional message.
     */
    data class Error(
        val exception: Throwable? = null,
        val message: String? = null
    ) : Result<Nothing>()
    
    /**
     * Represents a loading state.
     */
    data object Loading : Result<Nothing>()
    
    /**
     * Returns true if the result is Success.
     */
    val isSuccess: Boolean
        get() = this is Success
    
    /**
     * Returns true if the result is Error.
     */
    val isError: Boolean
        get() = this is Error
    
    /**
     * Returns true if the result is Loading.
     */
    val isLoading: Boolean
        get() = this is Loading
    
    /**
     * Returns the data if this is a Success, or null otherwise.
     */
    fun getOrNull(): T? = when (this) {
        is Success -> data
        else -> null
    }
    
    /**
     * Returns the data if this is a Success, or the default value otherwise.
     */
    fun getOrDefault(default: T): T = when (this) {
        is Success -> data
        else -> default
    }
    
    /**
     * Returns the exception if this is an Error, or null otherwise.
     */
    fun exceptionOrNull(): Throwable? = when (this) {
        is Error -> exception
        else -> null
    }
    
    /**
     * Maps the data if this is a Success using the given transform function.
     */
    inline fun <R> map(transform: (T) -> R): Result<R> = when (this) {
        is Success -> Success(transform(data))
        is Error -> this
        is Loading -> this
    }
    
    /**
     * Executes the given action if this is a Success.
     */
    inline fun onSuccess(action: (T) -> Unit): Result<T> {
        if (this is Success) action(data)
        return this
    }
    
    /**
     * Executes the given action if this is an Error.
     */
    inline fun onError(action: (Throwable?, String?) -> Unit): Result<T> {
        if (this is Error) action(exception, message)
        return this
    }
    
    /**
     * Executes the given action if this is Loading.
     */
    inline fun onLoading(action: () -> Unit): Result<T> {
        if (this is Loading) action()
        return this
    }
}

/**
 * Converts a suspend function to a Result.
 */
suspend fun <T> runCatching(block: suspend () -> T): Result<T> {
    return try {
        Result.Success(block())
    } catch (e: Exception) {
        Result.Error(exception = e, message = e.message)
    }
}
