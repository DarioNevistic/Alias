package com.example.darionevistic.alias.ui.home

import com.example.darionevistic.alias.R
import com.example.darionevistic.alias.database.dao.TeamDao
import com.example.darionevistic.alias.database.entity.Team
import com.example.darionevistic.alias.ui.teams.TeamsActivity
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

class HomeModel @Inject constructor(private val teamDao: TeamDao, private val homeActivity: HomeActivity) {

    private val teamsNames = homeActivity.resources.getStringArray(R.array.teams_names)

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

    fun deleteTeamsFromDB(teams: MutableList<Team>) {
        Observable.fromCallable { teamDao.deleteTeams(teams) }
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe {
                    Timber.d("Deleted ${teams.size} teams in DB...")
                }
    }

    fun loadDefaultTeams(size: Int): MutableList<Team> {
        val teams: MutableList<Team> = mutableListOf()
        for (i in 0 until size) {
            teams.add(Team(teamsNames[i]))
        }
        Timber.d("Load default teams: ${teams.size}")
        return teams
    }
}