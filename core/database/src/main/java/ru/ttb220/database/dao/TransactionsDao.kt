package ru.ttb220.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.ABORT
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.Instant
import ru.ttb220.database.model.entity.TransactionEntity

@Dao
interface TransactionsDao {

    @Insert(onConflict = ABORT)
    suspend fun insertTransaction(transactionEntity: TransactionEntity)

    @Update(onConflict = ABORT)
    suspend fun updateTransaction(transactionEntity: TransactionEntity)

    @Delete()
    suspend fun deleteTransaction(transactionEntity: TransactionEntity)

    @Query(
        "SELECT * FROM transactions " +
                "WHERE id = :id " +
                "LIMIT 1"
    )
    fun getTransactionById(id: Long): Flow<TransactionEntity?>

    @Query(
        "SELECT * FROM transactions " +
                "WHERE account_id = :accountId " +
                "AND date BETWEEN :startDate AND :endDate"
    )
    fun getAllTransactions(
        accountId: Long,
        startDate: Instant,
        endDate: Instant
    ): Flow<List<TransactionEntity>>
}