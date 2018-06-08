package com.example.darionevistic.alias.app.di

import com.example.darionevistic.alias.ui.main_game.MainGameFragment
import com.example.darionevistic.alias.ui.main_game.MainGameModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBindingModule {

    @FragmentScoped
    @ContributesAndroidInjector(modules = [MainGameModule::class])
    abstract fun bindMainFragment(): MainGameFragment
}