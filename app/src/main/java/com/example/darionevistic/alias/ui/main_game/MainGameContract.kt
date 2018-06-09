package com.example.darionevistic.alias.ui.main_game

import com.example.darionevistic.alias.database.entity.Team
import io.reactivex.Observable
import io.reactivex.disposables.Disposable

/**
 * Created by dario.nevistic on 21/03/2018.
 */
interface MainGameContract {

    interface View {
        fun showStartDialog()

        fun hideStartDialog()

        fun observeStartBtn(): Observable<Any>

        fun observeCorrectBtn(): Observable<Any>

        fun observeWrongBtn(): Observable<Any>

        fun initTeamsAdapter(teams: ArrayList<Team>)

        fun startTimer()

        fun stopTimer()

        fun setRoundTime(seconds: Int)
    }

    interface Presenter {
        fun getTeams(): Disposable

        fun getSettings(): Disposable

        fun onCorrectAnswerPressed(): Disposable

        fun onWrongAnswerPressed(): Disposable
    }
}