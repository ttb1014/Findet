package ru.ttb220.data.impl.util

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.retry
import ru.ttb220.network.api.exception.ServerErrorException

private const val DEFAULT_RETRY_ATTEMPTS = 3L
private const val DEFAULT_RETRY_DELAY = 2000L

/**
 * Policy of retrying a call after ServerErrorException (500 code)
 */
private val DEFAULT_RETRY_POLICY = fun(throwable: Throwable): Boolean {
    if (throwable is ServerErrorException)
        return true

    return false
}

suspend fun <T> withRetry(
    block: suspend () -> T,
    shouldRetry: (Throwable) -> Boolean = DEFAULT_RETRY_POLICY,
    retries: Long = DEFAULT_RETRY_ATTEMPTS,
    delayMillis: Long = DEFAULT_RETRY_DELAY,
): T {
    var currentAttempt = 0
    while (true) {
        try {
            return block()
        } catch (e: Exception) {
            currentAttempt++
            if (currentAttempt > retries ||
                !shouldRetry(e)
            ) throw e

            delay(delayMillis)
        }
    }
}

fun <T> Flow<T>.withRetry(
    shouldRetry: (Throwable) -> Boolean = DEFAULT_RETRY_POLICY,
    retries: Long = DEFAULT_RETRY_ATTEMPTS,
    delayMillis: Long = DEFAULT_RETRY_DELAY
): Flow<T> = retry(retries) { cause ->
    if (cause is CancellationException ||
        !shouldRetry(cause)
    ) return@retry false

    delay(delayMillis)
    true
}
