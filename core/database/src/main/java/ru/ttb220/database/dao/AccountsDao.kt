package ru.ttb220.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.ABORT
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import ru.ttb220.database.model.entity.Account

@Dao
interface AccountsDao {

    @Insert(onConflict = ABORT)
    suspend fun insertAccount(account: Account)

    @Update(onConflict = ABORT)
    suspend fun updateAccount(account: Account)

    @Delete()
    suspend fun deleteAccount(account: Account)

    @Query("SELECT * from accounts " +
            "WHERE id = :id")
    fun getAccountById(id: Long): Flow<Account?>
}