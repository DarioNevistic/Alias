package com.example.darionevistic.alias.app

import com.example.darionevistic.alias.BuildConfig
import com.example.darionevistic.alias.app.di.DaggerAppComponent
import com.example.darionevistic.alias.ext.Constants
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import timber.log.Timber


/**
 * Created by dario.nevistic on 07/03/2018.
 */

class AliasApplication : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder().create(this)
    }

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(object : Timber.DebugTree() {
                override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
                    super.log(priority, Constants.LOGTAG, message, t)
                }
            })
        } else {
            // TODO Crashlytics.start(this);
            // TODO Timber.plant(new CrashlyticsTree());
        }
    }
}
