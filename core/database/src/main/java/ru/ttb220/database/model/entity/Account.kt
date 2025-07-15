package ru.ttb220.database.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.time.Instant

@Entity(
    tableName = "accounts",
    indices = [
        Index(value = ["name"])
    ],
)
data class Account(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "balance")
    val balance: Double,

    @ColumnInfo(name = "currency")
    val currency: String,

    @ColumnInfo(name = "created_at")
    override val createdAt: Instant,

    @ColumnInfo(name = "updated_at")
    override val updatedAt: Instant,
) : Timestamped
