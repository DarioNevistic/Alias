package com.example.darionevistic.alias.app

import android.app.Activity
import android.app.Application
import com.example.darionevistic.alias.BuildConfig
import com.example.darionevistic.alias.app.dagger.AppComponent
import com.example.darionevistic.alias.app.dagger.AppModule
import com.example.darionevistic.alias.app.dagger.DaggerAppComponent
import com.example.darionevistic.alias.ext.Constants
import timber.log.Timber


/**
 * Created by dario.nevistic on 07/03/2018.
 */

class AliasApplication : Application() {

    companion object {
        lateinit var component: AppComponent

        operator fun get(activity: Activity): AliasApplication {
            return activity.application as AliasApplication
        }

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

        initializeDependencies();
    }

    private fun initializeDependencies() {
        component = DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .build()
    }

    fun component(): AppComponent {
        return component
    }
}
