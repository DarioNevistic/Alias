package com.example.darionevistic.alias.ui.teams

import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by dario.nevistic on 16/03/2018.
 */
@Module
abstract class TeamsModule {

    @ContributesAndroidInjector
    internal abstract fun teamsActivity(): TeamsActivity

    @Binds
    abstract fun providePresenter(presenter: TeamsPresenter): TeamsContract.Presenter
}