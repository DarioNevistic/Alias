package com.example.darionevistic.alias.ui.main_game

import com.example.darionevistic.alias.base.BasePresenter
import com.example.darionevistic.alias.database.entity.Team
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by dario.nevistic on 21/03/2018.
 */
class MainGamePresenter @Inject constructor(private val model: MainGameModel,
                                            private val view: MainGameFragment) : BasePresenter, MainGameContract.Presenter {

    private val compositeDisposable = CompositeDisposable()

    override fun onViewCreated() {
        view.showStartDialog()
        compositeDisposable.addAll(getTeams())

        compositeDisposable.add(view.observeStartBtn()
                .subscribe { view.hideStartDialog() })
    }

    override fun onViewDestroyed() {
        compositeDisposable.clear()
    }

    override fun getTeams(): Disposable {
        return model.getTeamsFromDB()
                .subscribe {
                    Timber.d("Teams from DB: ${it.size}")
                    view.initTeamsAdapter(it as ArrayList<Team>) }
    }
}