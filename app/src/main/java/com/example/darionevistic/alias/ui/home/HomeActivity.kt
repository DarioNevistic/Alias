package com.example.darionevistic.alias.ui.home


import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.example.darionevistic.alias.R
import com.example.darionevistic.alias.app.AliasApplication
import com.example.darionevistic.alias.ui.home.di.DaggerHomeComponent
import com.example.darionevistic.alias.ui.home.di.HomeComponent
import com.example.darionevistic.alias.ui.home.di.HomeModule
import com.example.darionevistic.alias.ui.home.mvp.HomeContract
import com.example.darionevistic.alias.ui.home.mvp.HomePresenter
import com.example.darionevistic.alias.ui.main_game.MainGameActivity
import com.example.darionevistic.alias.ui.rules.RulesActivity
import com.example.darionevistic.alias.ui.settings.SettingsActivity
import com.example.darionevistic.alias.ui.teams.TeamsActivity
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_home.*
import javax.inject.Inject


/**
 * Created by dario.nevistic on 07/03/2018.
 */
class HomeActivity : AppCompatActivity(), HomeContract.View {

    @Inject
    lateinit var homePresenter: HomePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initDagger()

        setContentView(R.layout.activity_home)
        homePresenter.onViewCreated()

        loadSettings()
    }

    private fun initDagger() {
        DaggerHomeComponent.builder()
                .homeModule(HomeModule(this))
                .appComponent(AliasApplication.get(this).component())
                .build()
                .inject(this)
    }

    override fun setMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun showLastGameTime(time: String) {
        last_game_textview.text = time + " " + getString(R.string.last_game_minutes)
    }

    override fun isContinueGameEnabled(enabled: Boolean) {
        if (!enabled) {
            continue_game_layout_holder.alpha = 0.2f
            continue_game_layout_holder.isEnabled = false
        } else {
            continue_game_layout_holder.alpha = 1.0f
            continue_game_layout_holder.isEnabled = true
        }
    }

    override fun openNewGameActivity() {
        startActivity(Intent(this@HomeActivity, TeamsActivity::class.java))
    }

    override fun openRulesActivity() {
        startActivity(Intent(this@HomeActivity, RulesActivity::class.java))
    }

    override fun openSettingsActivity() {
        startActivity(Intent(this@HomeActivity, SettingsActivity::class.java))
    }

    override fun loadSettings() {
        homePresenter.getAllSettings()
    }

    fun observeContinueGameBtn(): Observable<Any> = RxView.clicks(continue_game_layout_holder)

    fun observeNewGameBtn(): Observable<Any> = RxView.clicks(new_game_layout_holder)

    fun observeRulesGameBtn(): Observable<Any> = RxView.clicks(rules_layout_holder)

    fun observeSettingsGameBtn(): Observable<Any> = RxView.clicks(home_settings_button)

    override fun onDestroy() {
        super.onDestroy()
        homePresenter.onViewDestroyed()
    }


}