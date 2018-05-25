package com.example.darionevistic.alias.ui.home

import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by dario.nevistic on 07/03/2018.
 */
@Module
abstract class HomeModule {

    @ContributesAndroidInjector
    internal abstract fun homeActivity(): HomeActivity

    @Binds
    abstract fun providePresenter(homePresenter: HomePresenter): HomeContract.Presenter
}