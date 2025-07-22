package ru.ttb220.model.account

import kotlinx.datetime.Instant
import ru.ttb220.model.util.Timestamped
import ru.ttb220.model.util.Updatable

data class Account(
    val id: Int,
    val userId: Int,
    val name: String,
    val balance: Double,
    val currency: String,
    override val createdAt: Instant,
    override val updatedAt: Instant,
) : Timestamped, Updatable<Account> {

    override fun update(instant: Instant) = this.copy(
        updatedAt = instant
    )
}