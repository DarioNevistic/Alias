package com.example.darionevistic.alias.app.di

import android.content.Context
import com.example.darionevistic.alias.app.AliasApplication
import dagger.Module
import dagger.Provides

/**
 * Created by dario.nevistic on 07/03/2018.
 */
@Module
class AppModule {

    @Provides
    fun provideContext(application: AliasApplication): Context {
        return application.applicationContext
    }
}