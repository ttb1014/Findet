package ru.ttb220.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.ABORT
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import ru.ttb220.database.model.entity.AccountEntity

@Dao
interface AccountsDao {

    @Insert(onConflict = ABORT)
    suspend fun insertAccount(accountEntity: AccountEntity): Long

    @Insert(onConflict = ABORT)
    suspend fun insertAccounts(accountEntities: List<AccountEntity>)

    @Update(onConflict = ABORT)
    suspend fun updateAccount(accountEntity: AccountEntity): Int

    @Update
    suspend fun updateAccounts(accountEntities: List<AccountEntity>)

    @Delete
    suspend fun deleteAccount(accountEntity: AccountEntity): Int

    @Delete
    suspend fun deleteAccounts(accountEntities: List<AccountEntity>)

    @Query("SELECT * from accounts")
    fun getAllAccounts(): Flow<List<AccountEntity>>

    @Query(
        "SELECT * from accounts " +
                "WHERE id = :id"
    )
    fun getAccountById(id: Long): Flow<AccountEntity?>
}