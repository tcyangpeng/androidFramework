package com.example.core.common.extension

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Extension functions for Coroutines.
 */

/**
 * Launches a coroutine with a delay.
 */
fun CoroutineScope.launchDelayed(delayMillis: Long, block: suspend () -> Unit): Job {
    return launch {
        delay(delayMillis)
        block()
    }
}

/**
 * Creates a debounced function that delays invoking the action.
 */
fun <T> debounce(
    waitMs: Long = 300L,
    scope: CoroutineScope,
    action: (T) -> Unit
): (T) -> Unit {
    var debounceJob: Job? = null
    return { param: T ->
        debounceJob?.cancel()
        debounceJob = scope.launch {
            delay(waitMs)
            action(param)
        }
    }
}

/**
 * Creates a throttled function that only invokes the action at most once per wait period.
 */
fun <T> throttleFirst(
    waitMs: Long = 300L,
    scope: CoroutineScope,
    action: (T) -> Unit
): (T) -> Unit {
    var throttleJob: Job? = null
    var latestParam: T? = null
    return { param: T ->
        latestParam = param
        if (throttleJob?.isActive != true) {
            throttleJob = scope.launch {
                latestParam?.let { action(it) }
                delay(waitMs)
            }
        }
    }
}
