package com.example.darionevistic.alias.ui.main_game

import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by dario.nevistic on 21/03/2018.
 */
@Module
abstract class MainGameModule {

    @ContributesAndroidInjector
    internal abstract fun mainGameActivity(): MainGameActivity

    @Binds
    abstract fun provideMainGamePresenter(mainGamePresenter: MainGamePresenter) : MainGameContract.Presenter
}