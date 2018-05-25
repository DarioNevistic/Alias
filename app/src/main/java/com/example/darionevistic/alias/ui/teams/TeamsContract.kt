package com.example.darionevistic.alias.ui.teams

import com.example.darionevistic.alias.database.entity.Team
import io.reactivex.Observable
import io.reactivex.disposables.Disposable

/**
 * Created by dario.nevistic on 16/03/2018.
 */
interface TeamsContract {

    interface View {
        fun observeBackBtn(): Observable<Any>

        fun observeSettingsBtn(): Observable<Any>

        fun observeAddTeamBtn(): Observable<Any>

        fun observePlayBtn(): Observable<Any>

        fun showTeamsListView(teams: MutableList<Team>)
    }

    interface Presenter {

        fun onBackPressed(): Disposable

        fun onSettingsPressed(): Disposable

        fun onAddTeamPressed(): Disposable

        fun onPlayPressed(): Disposable

        fun getTeams(): Disposable
    }
}