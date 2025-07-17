package ru.ttb220.database.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Fts4
import androidx.room.PrimaryKey

@Fts4
@Entity(
    tableName = "categories",
)
data class CategoryEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "rowid")
    val id: Int = 0,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "emoji")
    val emoji: String,

    @ColumnInfo(name = "is_income")
    val isIncome: Boolean
) 
