package ru.ttb220.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.datetime.Instant
import ru.ttb220.database.util.SyncableEntity

@Entity(
    tableName = "transactions",
    indices = [
        Index("account_id"),
        Index("category_id"),
        Index("synced"),
    ],
    foreignKeys = [
        ForeignKey(
            entity = AccountEntity::class,
            parentColumns = [
                "id"
            ],
            childColumns = [
                "account_id"
            ],
            onDelete = CASCADE,
            onUpdate = CASCADE,
        ),
        ForeignKey(
            entity = CategoryEntity::class,
            parentColumns = [
                "id",
            ],
            childColumns = [
                "category_id"
            ],
            onDelete = CASCADE,
            onUpdate = CASCADE,
        )
    ]
)
data class TransactionEntity(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int = 0,

    @ColumnInfo(name = "account_id")
    val accountId: Int,

    @ColumnInfo(name = "category_id")
    val categoryId: Int,

    @ColumnInfo(name = "amount")
    val amount: Double,

    @ColumnInfo(name = "date")
    val date: Instant,

    @ColumnInfo(name = "comment")
    val comment: String?,

    @ColumnInfo(name = "synced")
    override var synced: Boolean = false,

    @ColumnInfo(name = "created_at")
    override var createdAt: Instant,

    @ColumnInfo(name = "updated_at")
    override var updatedAt: Instant,
) : SyncableEntity
