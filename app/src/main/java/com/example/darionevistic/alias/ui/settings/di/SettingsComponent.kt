package com.example.darionevistic.alias.ui.settings.di

import com.example.darionevistic.alias.app.dagger.AppComponent
import com.example.darionevistic.alias.ui.settings.SettingsActivity
import dagger.Component

/**
 * Created by dario.nevistic on 08/03/2018.
 */
@SettingsScope
@Component(modules = [SettingsModule::class], dependencies = [AppComponent::class])
interface SettingsComponent {

    fun inject(settingsActivity: SettingsActivity)
}