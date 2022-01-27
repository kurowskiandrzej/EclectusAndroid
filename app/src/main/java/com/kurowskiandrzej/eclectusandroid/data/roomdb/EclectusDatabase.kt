package com.kurowskiandrzej.eclectusandroid.data.roomdb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.kurowskiandrzej.eclectusandroid.data.roomdb.entity.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [User::class], version = 1)
abstract class EclectusDatabase: RoomDatabase() {
    abstract val dao: EclectusDao

    companion object {
        private lateinit var database: EclectusDatabase

        @Volatile
        private var INSTANCE: EclectusDatabase? = null
        fun getInstance(context: Context): EclectusDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = createInstance(context)
                }
                return instance
            }
        }

        private fun createInstance(context: Context): EclectusDatabase {
            database = Room.databaseBuilder(
                context.applicationContext,
                EclectusDatabase::class.java,
                "eclectus-app-database"
            ).addCallback(object : RoomDatabase.Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    CoroutineScope(Dispatchers.IO).launch {
                        // TODO insert initial data to db
                    }
                }
            }).build()
            return database
        }

        fun getTestInstance(context: Context): EclectusDatabase {
            return Room.inMemoryDatabaseBuilder(context, EclectusDatabase::class.java)
                .allowMainThreadQueries()
                .build()
        }
    }
}