package ru.ttb220.database.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.Index
import androidx.room.PrimaryKey
import java.time.Instant

@Entity(
    tableName = "transactions",
    indices = [
        Index("account_id"),
        Index("category_id"),
    ],
    foreignKeys = [
        ForeignKey(
            entity = Account::class,
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
            entity = Category::class,
            parentColumns = [
                "rowid",
            ],
            childColumns = [
                "category_id"
            ],
            onDelete = CASCADE,
            onUpdate = CASCADE,
        )
    ]
)
data class Transaction(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int,

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

    @ColumnInfo(name = "created_at")
    override val createdAt: Instant,

    @ColumnInfo(name = "updated_at")
    override val updatedAt: Instant
) : Timestamped
