package com.example.darionevistic.alias.ui.main_game

import com.example.darionevistic.alias.database.dao.SettingsDao
import com.example.darionevistic.alias.database.dao.TeamDao
import com.example.darionevistic.alias.database.entity.SettingsData
import com.example.darionevistic.alias.database.entity.Team
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by dario.nevistic on 21/03/2018.
 */
class MainGameModel @Inject constructor(private val teamDao: TeamDao,
                                        private val settingsDao: SettingsDao) {

    fun getTeamsFromDB(): Observable<MutableList<Team>> {
        return teamDao.getAllTeams()
                .toObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext { Timber.d("Dispatching ${it.size} teams from DB...") }
    }

    fun storeTeamsInDB(teams: MutableList<Team>) {
        Observable.fromCallable { teamDao.insertTeams(teams) }
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe {
                    Timber.d("Inserted ${teams.size} teams in DB...")
                }
    }

    fun getSettingsFromDB(): Flowable<List<SettingsData>> {
        return settingsDao.getAllSettings()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext { Timber.d("Dispatching settings from DB...") }
    }
}