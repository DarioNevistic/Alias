package com.example.darionevistic.alias.ui.teams.mvp

import io.reactivex.Observable
import io.reactivex.disposables.Disposable

/**
 * Created by dario.nevistic on 16/03/2018.
 */
interface TeamsContract {

    interface View {
        fun observeBackBtn(): Observable<Any>

        fun observeSettingsBtn(): Observable<Any>
    }

    interface Presenter {

        fun onBackPressed(): Disposable

        fun onSettingsPressed(): Disposable
    }
}