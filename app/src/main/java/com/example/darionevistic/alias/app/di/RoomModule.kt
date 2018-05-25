package com.example.darionevistic.alias.app.di

import android.content.Context
import com.example.darionevistic.alias.database.AppDatabase
import com.example.darionevistic.alias.database.dao.SettingsDao
import com.example.darionevistic.alias.database.dao.TeamDao
import dagger.Module
import dagger.Provides
import dagger.Reusable


/**
 * Created by dario.nevistic on 12/03/2018.
 */
@Module
object RoomModule {

    @Provides
    @Reusable
    @JvmStatic
    fun provideAppDatabase(context: Context) = AppDatabase.createPersistentDatabase(context)

    @Provides
    @Reusable
    @JvmStatic
    fun provideSettingsDao(appDatabase: AppDatabase): SettingsDao {
        return appDatabase.settingsDao()
    }

    @Provides
    @Reusable
    @JvmStatic
    fun provideTeamnDao(appDatabase: AppDatabase): TeamDao {
        return appDatabase.teamDao()
    }

}