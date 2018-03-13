package com.example.darionevistic.alias.app.dagger

import com.example.darionevistic.alias.app.AliasApplication
import com.example.darionevistic.alias.database.AppDatabase
import com.example.darionevistic.alias.database.dao.SettingsDao
import com.example.darionevistic.alias.ui.SplashActivity
import dagger.Component

/**
 * Created by dario.nevistic on 07/03/2018.
 */
@AppScope
@Component(modules = [(AppModule::class), (RoomModule::class)])
interface AppComponent {

    fun provideAppDatabase(): AppDatabase

    fun provideQuestionDao(): SettingsDao

    fun inject(app: AliasApplication)
}
