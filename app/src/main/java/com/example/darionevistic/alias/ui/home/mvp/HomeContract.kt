package com.example.darionevistic.alias.ui.home.mvp

import com.example.darionevistic.alias.database.entity.SettingsData
import com.example.darionevistic.alias.database.entity.Team
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * Created by dario.nevistic on 07/03/2018.
 */
interface HomeContract {

    interface View {

        fun setMessage(message: String)

        fun showLastGameTime(time: String)

        fun isContinueGameEnabled(enabled: Boolean)

        fun openNewGameActivity()

        fun openRulesActivity()

        fun openSettingsActivity()

        fun loadSettings()
    }

    interface Presenter {

        fun onInsertSettings(settingsData: SettingsData)

        fun getAllSettings(): Disposable

        fun onNewGamePressed(): Disposable

    }
}