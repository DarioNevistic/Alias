package com.example.darionevistic.alias.ui.home.di

import com.example.darionevistic.alias.app.AliasApplication
import com.example.darionevistic.alias.app.dagger.AppComponent
import com.example.darionevistic.alias.database.dao.SettingsDao
import com.example.darionevistic.alias.ui.home.HomeActivity
import dagger.Component

/**
 * Created by dario.nevistic on 07/03/2018.
 */
@HomeScope
@Component(modules = [HomeModule::class], dependencies = [(AppComponent::class)])
interface HomeComponent {

    fun inject(homeActivity: HomeActivity)
}