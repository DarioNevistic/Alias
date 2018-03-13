package com.example.darionevistic.alias.ui.home.di

import com.example.darionevistic.alias.database.dao.SettingsDao
import com.example.darionevistic.alias.ui.home.HomeActivity
import com.example.darionevistic.alias.ui.home.mvp.HomePresenter
import dagger.Module
import dagger.Provides

/**
 * Created by dario.nevistic on 07/03/2018.
 */
@Module
class HomeModule(private val activity: HomeActivity) {

    @Provides
    @HomeScope
    fun provideActivity() = activity

    @Provides
    @HomeScope
    fun providePresenter(settingsDao: SettingsDao, homeActivity: HomeActivity) = HomePresenter(settingsDao, homeActivity)
}