package com.example.darionevistic.alias.ui.teams

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.example.darionevistic.alias.R
import com.example.darionevistic.alias.database.entity.Team
import com.example.darionevistic.alias.ui.main_game.MainGameActivity
import com.example.darionevistic.alias.ui.settings.SettingsActivity
import com.example.darionevistic.alias.util.RecyclerViewClickListener
import com.jakewharton.rxbinding2.view.RxView
import dagger.android.support.DaggerAppCompatActivity
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_teams.*
import javax.inject.Inject

/**
 * Created by dario.nevistic on 16/03/2018.
 */
class TeamsActivity : DaggerAppCompatActivity(), TeamsContract.View, RecyclerViewClickListener {

    @Inject
    lateinit var presenter: TeamsPresenter

    private lateinit var teamsAdapter: TeamsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_teams)

        presenter.onViewCreated()
    }

    fun goToSettingsActivity() = startActivity(Intent(this@TeamsActivity, SettingsActivity::class.java))

    fun goToMainGameActivity() = startActivity(Intent(this@TeamsActivity, MainGameActivity::class.java))

    override fun observeBackBtn(): Observable<Any> = RxView.clicks(back_button)

    override fun observeSettingsBtn(): Observable<Any> = RxView.clicks(teams_settings_button)

    override fun observeAddTeamBtn(): Observable<Any> = RxView.clicks(add_team_btn)

    override fun observePlayBtn(): Observable<Any> = RxView.clicks(play_btn)

    override fun onDestroy() {
        super.onDestroy()
        presenter.onViewDestroyed()
    }

    override fun showTeamsListView(teams: MutableList<Team>) {
        teamsAdapter = TeamsAdapter(presenter, teams, this)
        teams_recyclerView.adapter = teamsAdapter
        teams_recyclerView.layoutManager = LinearLayoutManager(this)
        teams_recyclerView.smoothScrollToPosition(teamsAdapter.itemCount)
    }

    override fun onClick(view: View, position: Int) {
        teamsAdapter.removeTeam(position)
    }

    fun getTeamList(): MutableList<Team> = teamsAdapter.getItems()
}