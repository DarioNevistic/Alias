package com.example.darionevistic.alias.ui.teams.di

import com.example.darionevistic.alias.app.dagger.AppComponent
import com.example.darionevistic.alias.ui.teams.TeamsActivity
import dagger.Component

/**
 * Created by dario.nevistic on 16/03/2018.
 */
@TeamsScope
@Component(modules = [TeamsModule::class], dependencies = [AppComponent::class])
interface TeamsComponent {

    fun inject(teamsActivity: TeamsActivity)

}