package com.github.sseung416.contentprovidersample.client.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.github.sseung416.contentprovidersample.client.local.dao.ColorDao
import com.github.sseung416.contentprovidersample.client.local.dao.UserDao
import com.github.sseung416.contentprovidersample.client.local.dto.Color
import com.github.sseung416.contentprovidersample.client.local.dto.User

@Database(entities = [User::class, Color::class], version = 1, exportSchema = false)
abstract class LocalDatabase : RoomDatabase() {

    companion object {
        private var INSTANCE: LocalDatabase? = null

        @Synchronized
        fun getInstance(context: Context): LocalDatabase =
            INSTANCE ?: synchronized(LocalDatabase::class) {
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    LocalDatabase::class.java,
                    "test.db"
                ).build()

                INSTANCE!!
            }
    }

    abstract fun userDao(): UserDao

    abstract fun colorDao(): ColorDao
}