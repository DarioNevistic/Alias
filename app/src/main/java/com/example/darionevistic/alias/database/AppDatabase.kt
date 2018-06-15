package com.example.darionevistic.alias.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import android.content.Context
import com.example.darionevistic.alias.database.dao.SettingsDao
import com.example.darionevistic.alias.database.dao.TeamDao
import com.example.darionevistic.alias.database.entity.SettingsData
import com.example.darionevistic.alias.database.entity.Team
import com.example.darionevistic.alias.util.Converter

/**
 * Created by dario.nevistic on 12/03/2018.
 */
@Database(entities = [SettingsData::class, Team::class], version = 1)
@TypeConverters(Converter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun settingsDao(): SettingsDao

    abstract fun teamDao(): TeamDao

    companion object {
        private const val DB_NAME = "alias.db"

        fun createPersistentDatabase(context: Context): AppDatabase =
                Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, DB_NAME)
                        .build()
    }

}
