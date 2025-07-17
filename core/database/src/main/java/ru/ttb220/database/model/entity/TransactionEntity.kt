package ru.ttb220.database.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.datetime.Instant
import ru.ttb220.model.transaction.Transaction
import ru.ttb220.model.transaction.TransactionBrief

@Entity(
    tableName = "transactions",
    indices = [
        Index("account_id"),
        Index("category_id"),
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
data class TransactionEntity(
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

fun TransactionEntity.toTransaction(): Transaction = Transaction(
    id = id,
    accountId = accountId,
    categoryId = categoryId,
    amount = amount.toString(),
    transactionDate = date,
    comment = comment,
    createdAt = createdAt,
    updatedAt = updatedAt
)

fun TransactionEntity.toTransactionBrief(): TransactionBrief = TransactionBrief(
    accountId = accountId,
    categoryId = categoryId,
    amount = amount.toString(),
    transactionDate = date,
    comment = comment
)