package ru.ttb220.data.util

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

fun <T> Flow<T>.wrapToResult(): Flow<Result<T>> =
    this
        .map {
            Result.success(it)
        }
        .catch { e ->
            emit(Result.failure(e))
        }