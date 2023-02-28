package com.github.sseung416.contentprovidersample.client.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.github.sseung416.contentprovidersample.client.local.dao.ColorDao
import com.github.sseung416.contentprovidersample.client.local.dao.UserDao
import com.github.sseung416.contentprovidersample.client.local.dto.Color
import com.github.sseung416.contentprovidersample.client.local.dto.User
import kotlinx.coroutines.runBlocking
import java.util.concurrent.Executors

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
                ).addCallback(object : Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        Executors.newSingleThreadExecutor().execute {
                            runBlocking {
                                initialData(context)
                            }
                        }
                    }
                }).build()
                INSTANCE!!
            }

        suspend fun initialData(context: Context) {
            getInstance(context).colorDao().apply {
                insert(Color(name = "red", color = 0xff0000))
                insert(Color(name = "orange", color = 0xff9933))
            }
            getInstance(context).userDao().apply {
                insert(User(name = "김철수", age = 12, favoriteColorId = 1))
                insert(User(name = "최응애", age = 15, favoriteColorId = 2))
                insert(User(name = "해퇴사", age = 29, favoriteColorId = 1))
            }
        }
    }

    abstract fun userDao(): UserDao

    abstract fun colorDao(): ColorDao
}