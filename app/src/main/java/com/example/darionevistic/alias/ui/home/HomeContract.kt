package com.example.darionevistic.alias.ui.home

import com.example.darionevistic.alias.database.entity.SettingsData
import io.reactivex.Observable
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

        fun observeContinueGameBtn(): Observable<Any>

        fun observeNewGameBtn(): Observable<Any>

        fun observeRulesGameBtn(): Observable<Any>

        fun observeSettingsGameBtn(): Observable<Any>
    }

    interface Presenter {

        fun onInsertSettings(settingsData: SettingsData)

        fun getAllSettings(): Disposable

        fun onNewGamePressed(): Disposable

        fun getTeamsFromDB(): Disposable
    }
}