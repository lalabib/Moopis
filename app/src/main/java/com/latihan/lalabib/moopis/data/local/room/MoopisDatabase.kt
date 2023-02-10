package com.latihan.lalabib.moopis.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.latihan.lalabib.moopis.data.local.entity.MoviesEntity

@Database(entities = [MoviesEntity::class], version = 1, exportSchema = false)
abstract class MoopisDatabase: RoomDatabase() {

    companion object {
        @Volatile
        private var instance: MoopisDatabase? = null

        fun getInstance(context: Context): MoopisDatabase = instance ?: synchronized(this) {
            instance ?: Room.databaseBuilder(
                context.applicationContext,
                MoopisDatabase::class.java,
                "moopis.db"
            ).build()
        }
    }

    abstract fun MoopisDao(): MoopisDao
}