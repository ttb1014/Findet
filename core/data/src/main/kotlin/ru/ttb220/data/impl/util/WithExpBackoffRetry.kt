package ru.ttb220.data.impl.util

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.retryWhen
import ru.ttb220.network.api.exception.ServerErrorException
import kotlin.coroutines.cancellation.CancellationException
import kotlin.math.min
import kotlin.math.pow

private const val DEFAULT_RETRY_ATTEMPTS = 7L
private const val DEFAULT_RETRY_DELAY = 1000L
private const val DEFAULT_MAX_DELAY = 60_000L

private val DEFAULT_RETRY_POLICY = fun(throwable: Throwable): Boolean {
    if (throwable is ServerErrorException)
        return true

    return false
}

fun <T> Flow<T>.withExpBackoffRetry(
    shouldRetry: (Throwable) -> Boolean = DEFAULT_RETRY_POLICY,
    retries: Long = DEFAULT_RETRY_ATTEMPTS,
    initialDelayMillis: Long = DEFAULT_RETRY_DELAY,
    maxDelayMillis: Long = DEFAULT_MAX_DELAY
): Flow<T> = retryWhen { cause, attempt ->
    if (cause is CancellationException || attempt >= retries || !shouldRetry(cause)) {
        return@retryWhen false
    }

    val delayTime = min(initialDelayMillis * (2.0.pow(attempt.toInt())).toLong(), maxDelayMillis)
    delay(delayTime)
    true
}