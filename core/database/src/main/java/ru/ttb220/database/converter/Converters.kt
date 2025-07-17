package ru.ttb220.database.converter

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.TypeConverter
import kotlinx.datetime.Instant

class Converters {
    @RequiresApi(Build.VERSION_CODES.O)
    @TypeConverter
    fun fromInstant(instant: Instant): Long = instant.toEpochMilliseconds()

    @RequiresApi(Build.VERSION_CODES.O)
    @TypeConverter
    fun toInstant(millis: Long): Instant = millis.let { Instant.fromEpochMilliseconds(it) }
}