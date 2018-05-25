package com.example.darionevistic.alias.ui.settings

import com.example.darionevistic.alias.database.entity.SettingsData
import com.jakewharton.rxbinding2.InitialValueObservable
import io.reactivex.Observable
import io.reactivex.disposables.Disposable

/**
 * Created by dario.nevistic on 08/03/2018.
 */
interface SettingsContract {

    interface View {

        fun changeFinalWordSettings(state: Boolean)

        fun showPointsForVictoryValue(value: String)

        fun showRoundTimeValue(value: String)

        fun showFinalWordWorthValue(value: String)

        fun goBackOnPreviousActivity()

        fun setMessage(message: String)

        fun getSettings(): SettingsData

        fun setSettings(settingsData: SettingsData)

        fun observePointsForVictory(): InitialValueObservable<Int>

        fun observeRoundTimeInSeconds(): InitialValueObservable<Int>
        //
        fun observeCommonFinalWord(): InitialValueObservable<Boolean>
//
//        fun observeFinalWordPointsWorth()

        fun observeBackBtn(): Observable<Any>
    }

    interface Presenter {

        fun saveSettings(settingsData: SettingsData)

        fun onPointsForVictoryChange(): Disposable

        fun onRoundTimeSecondsPerRoundChange(): Disposable

        fun onCommonFinalWordChange(): Disposable

//        fun onFinalWordPointsWorthChange()

        fun loadSettingsFromDB(): Disposable

        fun onBackPressed(): Disposable
    }
}