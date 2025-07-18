package ru.ttb220.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.ABORT
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.Instant
import ru.ttb220.database.entity.TransactionEntity

@Dao
interface TransactionsDao {

    @Insert(onConflict = ABORT)
    suspend fun insertTransaction(transactionEntity: TransactionEntity): Long

    @Update(onConflict = ABORT)
    suspend fun updateTransaction(transactionEntity: TransactionEntity): Int

    @Upsert
    suspend fun upsertTransaction(transactionEntity: TransactionEntity): Long

    @Delete
    suspend fun deleteTransaction(transactionEntity: TransactionEntity): Int

    @Query("DELETE FROM transactions WHERE id = :id")
    suspend fun deleteTransactionById(id: Long)

    @Insert(onConflict = ABORT)
    suspend fun insertTransactions(transactions: List<TransactionEntity>)

    @Update(onConflict = ABORT)
    suspend fun updateTransactions(transactions: List<TransactionEntity>)

    @Upsert
    suspend fun upsertTransactions(transactions: List<TransactionEntity>)

    @Delete
    suspend fun deleteTransactions(transactions: List<TransactionEntity>)

    @Query("DELETE FROM transactions")
    suspend fun deleteAll()

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

    @Query("SELECT * FROM transactions")
    fun getAllTransactions(): Flow<List<TransactionEntity>>

    @Query(
        "SELECT * FROM transactions " +
                "WHERE synced = 0"
    )
    suspend fun getAllUnsynced(): List<TransactionEntity>
}