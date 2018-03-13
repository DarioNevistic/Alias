package com.example.darionevistic.alias.app.dagger

import android.app.Application
import android.content.Context
import com.example.darionevistic.alias.app.AliasApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton



/**
 * Created by dario.nevistic on 07/03/2018.
 */
@Module
class AppModule(context: AliasApplication) {
    private val context: Context

    init {
        this.context = context
    }

    @Provides
    @AppScope
    fun provideContext(): Context {
        return context
    }
}