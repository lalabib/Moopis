package com.latihan.lalabib.moopis.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.latihan.lalabib.moopis.data.local.entity.MoviesEntity
import com.latihan.lalabib.moopis.data.local.entity.RemoteKeys

@Database(entities = [MoviesEntity::class, RemoteKeys::class], version = 2, exportSchema = false)
abstract class MoopisDatabase: RoomDatabase() {

    abstract fun movieDao(): MoopisDao
    abstract fun remoteKeysDao(): RemoteKeysDao

    companion object {
        @Volatile
        private var instance: MoopisDatabase? = null

        fun getInstance(context: Context): MoopisDatabase = instance ?: synchronized(this) {
            instance ?: Room.databaseBuilder(
                context.applicationContext,
                MoopisDatabase::class.java, "moopis.db"
            )
                .fallbackToDestructiveMigration()
                .build()
                .also { instance = it }
        }
    }
}