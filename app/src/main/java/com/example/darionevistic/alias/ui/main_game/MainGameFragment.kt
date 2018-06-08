package com.example.darionevistic.alias.ui.main_game

import android.app.Dialog
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.darionevistic.alias.R
import com.example.darionevistic.alias.database.entity.Team
import com.jakewharton.rxbinding2.view.RxView
import dagger.android.support.DaggerFragment
import io.reactivex.Observable
import kotlinx.android.synthetic.main.fragment_main_game.*
import timber.log.Timber
import javax.inject.Inject
import android.os.CountDownTimer




/**
 * Created by dario.nevistic on 21/03/2018.
 */
class MainGameFragment : DaggerFragment(), MainGameContract.View {

    @Inject
    lateinit var presenter: MainGamePresenter

    private lateinit var teamsAdapter: InGameTeamsAdapter

    private lateinit var dialog: Dialog

    private lateinit var countDownTimer: CountDownTimer

    companion object {
        fun newInstance() =
                MainGameFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main_game, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.onViewCreated()
    }

    override fun initTeamsAdapter(teams: ArrayList<Team>) {
        teams[0].isTeamPlaying = true

        teamsAdapter = InGameTeamsAdapter(teams)
        in_game_teams_recyclerview.adapter = teamsAdapter
        in_game_teams_recyclerview.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
    }

    override fun observeStartBtn(): Observable<Any> = RxView.clicks(dialog.findViewById(R.id.start_game_dialog_btn))

    override fun showStartDialog() {
        dialog = Dialog(activity)
        dialog.setContentView(R.layout.dialog_get_ready)
        dialog.window.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.show()
    }

    override fun hideStartDialog() {
        if(dialog.isShowing) {
            dialog.dismiss()
            startTimer()
        }
    }

    override fun onPause() {
        super.onPause()
        countDownTimer.cancel()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onViewDestroyed()
    }

    override fun startTimer() {
        var secondsLeft = 0

        countDownTimer = object : CountDownTimer(60000, 100) {
            override fun onTick(ms: Long) {
                if (Math.round(ms.toFloat() / 1000.0f) != secondsLeft) {
                    secondsLeft = Math.round(ms.toFloat() / 1000.0f)
                    seconds_remained.text = secondsLeft.toString()
                }
            }
            override fun onFinish() {
                seconds_remained.text = "0"
            }
        }
        countDownTimer.start()
    }

    override fun stopTimer() {
        countDownTimer.cancel()
        // TODO implement seconds left when pause happened
    }
}