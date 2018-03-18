package com.example.darionevistic.alias.app.dagger

import android.content.Context
import com.example.darionevistic.alias.database.AppDatabase
import com.example.darionevistic.alias.database.dao.SettingsDao
import com.example.darionevistic.alias.database.dao.TeamDao
import com.example.darionevistic.alias.database.entity.Team
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


/**
 * Created by dario.nevistic on 12/03/2018.
 */
@Module
class RoomModule {

    @AppScope
    @Provides
    fun provideAppDatabase(context: Context) = AppDatabase.createPersistentDatabase(context)

    @Provides
    @AppScope
    fun provideSettingsDao(appDatabase: AppDatabase): SettingsDao {
        return appDatabase.settingsDao()
    }

    @Provides
    @AppScope
    fun provideTeamnDao(appDatabase: AppDatabase): TeamDao {
        return appDatabase.teamDao()
    }

}