package com.example.darionevistic.alias.ui.settings.mvp

import com.example.darionevistic.alias.base.BasePresenter
import com.example.darionevistic.alias.database.dao.SettingsDao
import com.example.darionevistic.alias.database.entity.SettingsData
import com.example.darionevistic.alias.ext.Constants
import com.example.darionevistic.alias.ui.settings.SettingsActivity
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by dario.nevistic on 08/03/2018.
 */

class SettingsPresenter @Inject constructor(private val settingsDao: SettingsDao, var view: SettingsActivity) : BasePresenter, SettingsContract.Presenter {

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun onViewCreated() {

        compositeDisposable.addAll(onBackPressed(),
                onPointsForVictoryChange(),
                onRoundTimeSecondsPerRoundChange(),
                loadSettingsFromDB(),
                onCommonFinalWordChange())
    }

    override fun onBackPressed(): Disposable {
        return view.observeBackBtn()
                .subscribe({
                    view.goBackOnPreviousActivity()
                    saveSettings(view.getSettings())
                })
    }

    override fun onViewDestroyed() {
        compositeDisposable.clear()
    }

    override fun saveSettings(settingsData: SettingsData) {
        compositeDisposable.add(Observable.just(settingsData)
                .observeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe({ settingsDao.insertSettings(it) }
                        , { error -> Timber.d(error.localizedMessage) }))

    }

    override fun onPointsForVictoryChange(): Disposable {
        return view.observePointsForVictory()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ progress ->
                    view.showPointsForVictoryValue((Constants.POINTS_FOR_VICTORY_MIN + progress * Constants.SEEKBAR_STEP_SIZE).toString())
                }, { error -> Timber.d(error.localizedMessage) })
    }

    override fun onCommonFinalWordChange(): Disposable {
        return view.observeCommonFinalWord()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ s -> view.changeFinalWordSettings(s) }
                        , { error -> Timber.d(error.localizedMessage) })
    }

    override fun onRoundTimeSecondsPerRoundChange(): Disposable {
        return view.observeRoundTimeInSeconds()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ progress ->
                    view.showRoundTimeValue((Constants.ROUND_TIME_MIN + progress * Constants.SEEKBAR_STEP_SIZE).toString())
                }, { error -> Timber.d(error.localizedMessage) })
    }

    override fun loadSettingsFromDB(): Disposable {
        return settingsDao.getAllSettings()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    view.setSettings(it.first())
                }, { error -> Timber.d(error.localizedMessage) })
    }

}
