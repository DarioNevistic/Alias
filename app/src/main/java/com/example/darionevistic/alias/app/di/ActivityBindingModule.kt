package com.example.darionevistic.alias.app.di

import com.example.darionevistic.alias.ui.home.HomeActivity
import com.example.darionevistic.alias.ui.home.HomeModule
import com.example.darionevistic.alias.ui.main_game.MainGameActivity
import com.example.darionevistic.alias.ui.main_game.MainGameModule
import com.example.darionevistic.alias.ui.settings.SettingsActivity
import com.example.darionevistic.alias.ui.settings.SettingsModule
import com.example.darionevistic.alias.ui.teams.TeamsActivity
import com.example.darionevistic.alias.ui.teams.TeamsModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindingModule {

    @ActivityScoped
    @ContributesAndroidInjector(modules = [HomeModule::class])
    abstract fun bindHomeActivity(): HomeActivity

    @ActivityScoped
    @ContributesAndroidInjector(modules = [MainGameModule::class])
    abstract fun bindMainActivity(): MainGameActivity

    @ActivityScoped
    @ContributesAndroidInjector(modules = [SettingsModule::class])
    abstract fun bindSettingsActivity(): SettingsActivity

    @ActivityScoped
    @ContributesAndroidInjector(modules = [TeamsModule::class])
    abstract fun bindTeamsActivity(): TeamsActivity
}