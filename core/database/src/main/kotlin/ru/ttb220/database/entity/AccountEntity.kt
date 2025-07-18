package ru.ttb220.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.datetime.Instant
import ru.ttb220.database.util.SyncableEntity

@Entity(
    tableName = "accounts",
    indices = [
        Index("name"),
        Index("synced"),
    ],
)
data class AccountEntity(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int = 0,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "user_id")
    val userId: Int,

    @ColumnInfo(name = "balance")
    val balance: Double,

    @ColumnInfo(name = "currency")
    val currency: String,

    @ColumnInfo(name = "synced")
    override var synced: Boolean = false,

    @ColumnInfo(name = "created_at")
    override var createdAt: Instant,

    @ColumnInfo(name = "updated_at")
    override var updatedAt: Instant,
) : SyncableEntity