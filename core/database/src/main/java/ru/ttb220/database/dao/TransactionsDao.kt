package ru.ttb220.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.ABORT
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import ru.ttb220.database.model.entity.Transaction
import java.time.Instant

@Dao
interface TransactionsDao {

    @Insert(onConflict = ABORT)
    suspend fun insertTransaction(transaction: Transaction)

    @Update(onConflict = ABORT)
    suspend fun updateTransaction(transaction: Transaction)

    @Delete()
    suspend fun deleteTransaction(transaction: Transaction)

    @Query(
        "SELECT * FROM transactions " +
                "WHERE id = :id " +
                "LIMIT 1"
    )
    fun getTransactionById(id: Long): Flow<Transaction?>

    @Query(
        "SELECT * FROM transactions " +
                "WHERE account_id = :accountId " +
                "AND date BETWEEN :startDate AND :endDate"
    )
    fun getAllTransactions(
        accountId: Long,
        startDate: Instant,
        endDate: Instant
    ): Flow<List<Transaction>>
}