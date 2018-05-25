package com.example.darionevistic.alias.ui.settings

import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by dario.nevistic on 08/03/2018.
 */
@Module
abstract class SettingsModule {

    @ContributesAndroidInjector
    internal abstract fun settingsActivity(): SettingsActivity

    @Binds
    abstract fun providePresenter(settingsPresenter: SettingsPresenter): SettingsContract.Presenter

}