package com.part.sportpartapp.sportList.data.local.sport

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [SportEntity::class],
    version = 1,
    exportSchema = false
)
abstract class SportDatabase : RoomDatabase() {
    abstract val sportDao: SportDao
}