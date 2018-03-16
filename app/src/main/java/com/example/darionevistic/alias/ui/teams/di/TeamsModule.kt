package com.example.darionevistic.alias.ui.teams.di

import com.example.darionevistic.alias.ui.teams.TeamsActivity
import com.example.darionevistic.alias.ui.teams.mvp.TeamsPresenter
import dagger.Module
import dagger.Provides

/**
 * Created by dario.nevistic on 16/03/2018.
 */
@Module
class TeamsModule(private val activity: TeamsActivity) {

    @Provides
    @TeamsScope
    fun provideActivity() = activity

    @Provides
    @TeamsScope
    fun providePresenter() = TeamsPresenter(activity)
}