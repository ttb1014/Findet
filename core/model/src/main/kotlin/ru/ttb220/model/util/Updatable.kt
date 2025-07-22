package ru.ttb220.model.util

import kotlinx.datetime.Instant

interface Updatable<T : Timestamped> {

    fun update(instant: Instant): T
}