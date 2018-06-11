package com.example.darionevistic.alias.ui.main_game

import android.app.Dialog
import android.content.Intent
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
import javax.inject.Inject
import android.os.CountDownTimer
import android.os.Handler
import com.example.darionevistic.alias.database.entity.SettingsData
import com.example.darionevistic.alias.ui.home.HomeActivity
import kotlinx.android.synthetic.main.dialog_get_ready.*
import timber.log.Timber
import java.util.*
import android.view.animation.TranslateAnimation
import android.support.v4.view.animation.FastOutSlowInInterpolator
import android.view.Gravity
import com.transitionseverywhere.Slide
import com.transitionseverywhere.TransitionManager
import kotlinx.android.synthetic.main.fragment_main_game.view.*
import android.support.v4.view.animation.FastOutLinearInInterpolator
import android.support.v4.view.animation.LinearOutSlowInInterpolator
import com.transitionseverywhere.Fade
import com.transitionseverywhere.TransitionSet
import com.transitionseverywhere.extra.Scale


/**
 * Created by dario.nevistic on 21/03/2018.
 */
class MainGameFragment : DaggerFragment(), MainGameContract.View {

    @Inject
    lateinit var presenter: MainGamePresenter

    private lateinit var teamsAdapter: InGameTeamsAdapter

    private lateinit var dialog: Dialog

    private lateinit var countDownTimer: CountDownTimer

    private lateinit var words: MutableList<String>

    lateinit var transitionsContainer: ViewGroup


    private var roundTime = 60
    private var numberOfCorrectAnswers = 0
    private var numberOfWrongAnswers = 0
    private var teamPlaying = 0
    private var roundNumber = 1
    private var pointsForVictory = 0
    private var allTeams: ArrayList<Team> = arrayListOf()

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
        loadWords()
        initDialog()
        presenter.onViewCreated()

        transitionsContainer = view.findViewById(R.id.card_view) as ViewGroup
    }

    override fun slideToRight() {
        TransitionManager.beginDelayedTransition(transitionsContainer, Slide(Gravity.END))
        transitionsContainer.card_view.visibility = View.GONE

       slideInCorrect()
    }

    override fun slideToLeft() {
        TransitionManager.beginDelayedTransition(transitionsContainer, Slide(Gravity.START))
        transitionsContainer.card_view.visibility = View.GONE

        slideInWrong()
    }

    fun slideInWrong() {
        Handler().postDelayed({
            setNextWord(getRandomWord())
            val set = TransitionSet()
                    .addTransition(Scale(0.7f))
                    .addTransition(Fade())
                    .setInterpolator(FastOutLinearInInterpolator())

            TransitionManager.beginDelayedTransition(transitionsContainer, set)
            transitionsContainer.card_view.visibility = View.VISIBLE
        }, 400)
        // TODO Move these methods to appropriate place (refactor methods calls from presenter)
    }

    fun slideInCorrect() {
        Handler().postDelayed({
            setNextWord(getRandomWordDeleteOld())
            val set = TransitionSet()
                    .addTransition(Scale(0.7f))
                    .addTransition(Fade())
                    .setInterpolator(FastOutLinearInInterpolator())

            TransitionManager.beginDelayedTransition(transitionsContainer, set)
            transitionsContainer.card_view.visibility = View.VISIBLE
        }, 400)

    }

    private fun initDialog() {
        dialog = Dialog(activity)
        dialog.setCancelable(false)
        dialog.setCanceledOnTouchOutside(false)
        dialog.setContentView(R.layout.dialog_get_ready)
        dialog.window.setBackgroundDrawableResource(android.R.color.transparent)
    }

    private fun loadWords() {
        words = mutableListOf()
        activity?.resources?.getStringArray(R.array.teams_names)?.forEach { words.add(it) }
    }

    override fun getRandomWord(): String {
        return words[Random().nextInt(words.size)]
    }

    override fun getRandomWordDeleteOld(): String {
        var randomWord = ""
        words.first { in_game_word.text.toString() == it }.let { words.remove(it) }

//        words.forEach { if (in_game_word.text.toString() == it) words.remove(it) }
        if (words.size > 0) {
            Timber.d("Get random word size is greater than 0")
            randomWord = words[Random().nextInt(words.size)]
        } else {
            Timber.d("Get random word, load new words")
            loadWords()
            getRandomWord()
        }

        return randomWord
    }

    override fun setNextWord(word: String) {
        in_game_word.text = word
    }

    override fun initTeamsAdapter(teams: ArrayList<Team>) {
        allTeams = teams
        allTeams[teamPlaying].isTeamPlaying = true

        teamsAdapter = InGameTeamsAdapter(allTeams)
        in_game_teams_recyclerview.adapter = teamsAdapter
        in_game_teams_recyclerview.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
    }

    override fun observeStartBtn(): Observable<Any> = RxView.clicks(dialog.findViewById(R.id.start_game_dialog_btn))

    override fun observeCorrectBtn(): Observable<Any> = RxView.clicks(correct_answer_btn)

    override fun observeWrongBtn(): Observable<Any> = RxView.clicks(wrong_answer_btn)

    override fun showStartDialog() {
        dialog.round_number_dialog.text = roundNumber.toString()
        dialog.team_name_dialog.text = allTeams[teamPlaying].teamName
        dialog.show()
    }

    override fun hideStartDialog() {
        if (dialog.isShowing) {
            dialog.dismiss()
            startTimer()
        }
    }

    private fun updateRoundNumber() {
        roundNumber++
    }

    private fun checkPointsForVictory() {
        if (allTeams[teamPlaying].teamPoints >= pointsForVictory) {
            gameEnd()
        }
    }

    private fun gameEnd() {
        Timber.d("Game end, victory team is: ${allTeams[teamPlaying].teamName}")
        startActivity(Intent(activity, HomeActivity::class.java))
        activity?.finish()
    }

    override fun roundEnd() {
        allTeams[teamPlaying].teamPoints = (allTeams[teamPlaying].teamPoints + numberOfCorrectAnswers)
        checkPointsForVictory()

        teamPlaying++
        if (teamPlaying < (allTeams.size)) {
            allTeams[teamPlaying - 1].isTeamPlaying = false
            allTeams[teamPlaying].isTeamPlaying = true
        } else {
            teamPlaying = 0
            allTeams[allTeams.size - 1].isTeamPlaying = false
            allTeams[teamPlaying].isTeamPlaying = true
            updateRoundNumber()
        }

        resetNumberOfCorrectAnswers()
        teamsAdapter.updateTeams(allTeams)
        showStartDialog()
    }

    private fun resetNumberOfCorrectAnswers() {
        numberOfCorrectAnswers = 0
        in_game_team_points.text = numberOfCorrectAnswers.toString()
    }

    override fun onPause() {
        super.onPause()
        hideStartDialog()
        countDownTimer.cancel()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onViewDestroyed()
    }

    override fun startTimer() {
        var secondsLeft = 0

        countDownTimer = object : CountDownTimer(((roundTime * 1000).toLong()), 100) {
            override fun onTick(ms: Long) {
                if (Math.round(ms.toFloat() / 1000.0f) != secondsLeft) {
                    secondsLeft = Math.round(ms.toFloat() / 1000.0f)
                    seconds_remained.text = secondsLeft.toString()
                }
            }

            override fun onFinish() {
                seconds_remained.text = roundTime.toString()
                roundEnd()

            }
        }
        countDownTimer.start()
    }

    override fun stopTimer() {
        countDownTimer.cancel()
    }

    override fun loadSettings(settingsData: SettingsData) {
        pointsForVictory = settingsData.pointsForVictory
        roundTime = settingsData.roundTime
        seconds_remained.text = roundTime.toString()
    }

    override fun onCorrectAnswer() {
        numberOfCorrectAnswers += 1
        in_game_team_points.text = numberOfCorrectAnswers.toString()
    }

    override fun onWrongAnswer() {
        numberOfWrongAnswers += 1
    }
}