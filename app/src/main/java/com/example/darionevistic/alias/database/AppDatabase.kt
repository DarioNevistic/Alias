package com.example.darionevistic.alias.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.example.darionevistic.alias.database.dao.SettingsDao
import com.example.darionevistic.alias.database.entity.SettingsData

/**
 * Created by dario.nevistic on 12/03/2018.
 */
@Database(entities = [SettingsData::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun settingsDao(): SettingsDao

    companion object {
        private const val DB_NAME = "alias.db"

        fun createPersistentDatabase(context: Context): AppDatabase =
                Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, DB_NAME)
                        .build()
    }

}

/*  companion object {
      lateinit var INSTANCE: AppDatabase

      fun getInstance(context: Context): AppDatabase {
          if (INSTANCE == null) {
              synchronized(AppDatabase::class) {
                  INSTANCE = Room.databaseBuilder(context.applicationContext,
                          AppDatabase::class.java, "alias.db")
                          .allowMainThreadQueries()
                          .build()
              }
          }
          return INSTANCE
      }
  }*/

/* fun destroyInstance() {
     INSTANCE = null
 }*/
