package com.example.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.core.database.dao.WeatherDao
import com.example.core.database.entity.WeatherEntity

/**
 * Room Database for the application.
 */
@Database(
    entities = [WeatherEntity::class],
    version = 1,
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {
    
    abstract fun weatherDao(): WeatherDao
    
    companion object {
        const val DATABASE_NAME = "android_framework_db"
    }
}
