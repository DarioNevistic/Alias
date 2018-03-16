package com.example.darionevistic.alias.ui.teams

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.darionevistic.alias.R
import com.example.darionevistic.alias.app.AliasApplication
import com.example.darionevistic.alias.ui.settings.SettingsActivity
import com.example.darionevistic.alias.ui.teams.di.DaggerTeamsComponent
import com.example.darionevistic.alias.ui.teams.di.TeamsComponent
import com.example.darionevistic.alias.ui.teams.di.TeamsModule
import com.example.darionevistic.alias.ui.teams.mvp.TeamsContract
import com.example.darionevistic.alias.ui.teams.mvp.TeamsPresenter
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_teams.*
import javax.inject.Inject

/**
 * Created by dario.nevistic on 16/03/2018.
 */
class TeamsActivity: AppCompatActivity(), TeamsContract.View {

    @Inject
    lateinit var presenter: TeamsPresenter

    override fun onCreate(savedInstanceState: Bundle?) {

        val component: TeamsComponent = DaggerTeamsComponent.builder()
                .appComponent(AliasApplication[this].component())
                .teamsModule(TeamsModule(this))
                .build()

        component.inject(this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_teams)

        presenter.onViewCreated()
    }

    fun goBackToPreviousActivity() = finish()

    fun goToSettingsActivity() = startActivity(Intent(this@TeamsActivity, SettingsActivity::class.java))

    override fun observeBackBtn(): Observable<Any> = RxView.clicks(back_button)

    override fun observeSettingsBtn(): Observable<Any> = RxView.clicks(teams_settings_button)

    override fun onDestroy() {
        super.onDestroy()
        presenter.onViewDestroyed()
    }
}