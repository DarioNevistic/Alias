package com.example.darionevistic.alias.ui.teams.mvp

import com.example.darionevistic.alias.R
import com.example.darionevistic.alias.database.dao.TeamDao
import com.example.darionevistic.alias.database.entity.Team
import com.example.darionevistic.alias.ui.teams.TeamsActivity
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by dario.nevistic on 18/03/2018.
 */
class TeamsModel @Inject constructor(private val teamDao: TeamDao, private val teamsActivity: TeamsActivity) {

    private val teamsNames = teamsActivity.resources.getStringArray(R.array.teams_names)

    fun goBackToPreviousActivity() = teamsActivity.finish()

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

    fun loadDefaultTeams(size: Int): MutableList<Team> {
        val teams: MutableList<Team> = mutableListOf()
        for (i in 0 until size) {
            teams.add(Team(teamsNames[i]))
        }
        return teams
    }

    fun addTeam(teamList: MutableList<Team>): MutableList<Team> {
        for (t in 0 until teamsNames.size) {
            if (!teamList.contains(Team(teamsNames[t]))) {
                teamList.add(Team(teamsNames[t]))
                break
            }
        }
        return teamList
    }
}