package com.example.darionevistic.alias.app.di

import com.example.darionevistic.alias.app.AliasApplication
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

/**
 * Created by dario.nevistic on 07/03/2018.
 */
@Singleton
@Component(modules = [(AppModule::class)
    , (RoomModule::class),
    AndroidSupportInjectionModule::class,
    ActivityBindingModule::class,
    FragmentBindingModule::class])
interface AppComponent : AndroidInjector<AliasApplication> {

    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<AliasApplication>()
}
