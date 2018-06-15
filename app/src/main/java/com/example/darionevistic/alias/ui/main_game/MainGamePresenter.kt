package com.example.darionevistic.alias.ui.main_game

import com.example.darionevistic.alias.base.BasePresenter
import com.example.darionevistic.alias.database.entity.Team
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * Created by dario.nevistic on 21/03/2018.
 */
class MainGamePresenter @Inject constructor(private val model: MainGameModel,
                                            private val view: MainGameFragment) : BasePresenter, MainGameContract.Presenter {

    private val compositeDisposable = CompositeDisposable()

    override fun onViewCreated() {
        compositeDisposable.addAll(getTeams(),
                getSettings(),
                onCorrectAnswerPressed(),
                onWrongAnswerPressed(),
                onStartRoundPressed())
    }

    override fun onViewDestroyed() {
        compositeDisposable.clear()
    }

    override fun onStartRoundPressed(): Disposable {
        return view.observeStartBtn()
                .subscribe {
                    Timber.d("Start round")
                    view.setNextWord(view.getRandomWord())
                    view.hideStartDialog()
                }
    }

    override fun getTeams(): Disposable {
        return model.getTeamsFromDB()
                .subscribe {
                    Timber.d("Teams from DB: ${it.size}")
                    view.initTeamsAdapter(it as ArrayList<Team>)
                    view.showStartDialog()
                }
    }

    override fun getSettings(): Disposable {
        return model.getSettingsFromDB()
                .subscribe {
                    Timber.d("Round time: ${it.first()}")
                    view.loadSettings(it.first())
                }
    }

    override fun onCorrectAnswerPressed(): Disposable {
        return view.observeCorrectBtn()
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .doOnNext {
                    Timber.d("Correct answer")
                    view.slideToRight()
                    view.onCorrectAnswer()
                }
                .doOnError { Timber.d("Errooooooor 1 ${it.localizedMessage}") }
                .subscribe()
    }

    override fun onWrongAnswerPressed(): Disposable {
        return view.observeWrongBtn()
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .doOnNext {
                    Timber.d("Wrong answer")
                    view.slideToLeft()
                    view.onWrongAnswer()
                }
                .doOnError { Timber.d("Errooooooor 2 ${it.localizedMessage}") }
                .subscribe()
    }

    override fun storeTeamsToDB(teams: MutableList<Team>) {
        model.storeTeamsInDB(teams)
    }
}