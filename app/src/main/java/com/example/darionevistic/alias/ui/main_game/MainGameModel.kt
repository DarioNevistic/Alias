package com.example.darionevistic.alias.ui.main_game

import com.example.darionevistic.alias.database.dao.TeamDao
import com.example.darionevistic.alias.database.entity.Team
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by dario.nevistic on 21/03/2018.
 */
class MainGameModel @Inject constructor(private val teamDao: TeamDao) {

    fun getTeamsFromDB(): Observable<MutableList<Team>> {
        return teamDao.getAllTeams()
                .toObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext { Timber.d("Dispatching ${it.size} teams from DB...") }
    }
}