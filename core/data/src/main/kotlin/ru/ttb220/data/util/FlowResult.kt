package ru.ttb220.data.util

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.flow

/**
 * Не поддерживает retry, т.к. оборачивает результат в Result
 */
@Deprecated("Use wrapToResult")
inline fun <T> flowResult(
    crossinline block: suspend FlowCollector<Result<T>>.() -> T
): Flow<Result<T>> = flow {
    val result = try {
        val value = block(this)
        Result.success(value)
    } catch (e: Throwable) {
        Result.failure(e)
    }

    emit(result)
}