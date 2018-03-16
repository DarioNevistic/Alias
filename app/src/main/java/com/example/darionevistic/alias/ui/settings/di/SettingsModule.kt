package com.example.darionevistic.alias.ui.settings.di

import com.example.darionevistic.alias.database.dao.SettingsDao
import com.example.darionevistic.alias.ui.settings.SettingsActivity
import com.example.darionevistic.alias.ui.settings.mvp.SettingsModel
import com.example.darionevistic.alias.ui.settings.mvp.SettingsPresenter
import dagger.Module
import dagger.Provides

/**
 * Created by dario.nevistic on 08/03/2018.
 */
@Module
class SettingsModule(private val activity: SettingsActivity) {

    @Provides
    @SettingsScope
    fun provideActivity() = activity

    @Provides
    @SettingsScope
    fun providePresenter(settingsDao: SettingsDao) = SettingsPresenter(settingsDao, activity)

    @Provides
    @SettingsScope
    fun provideModel() = SettingsModel()

}