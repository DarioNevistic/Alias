package com.example.darionevistic.alias.ui.home.mvp

import com.example.darionevistic.alias.base.BasePresenter
import com.example.darionevistic.alias.database.AppDatabase
import com.example.darionevistic.alias.database.dao.SettingsDao
import com.example.darionevistic.alias.database.entity.SettingsData
import com.example.darionevistic.alias.ui.home.HomeActivity
import io.reactivex.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by dario.nevistic on 07/03/2018.
 */
class HomePresenter @Inject constructor(private val settingsDao: SettingsDao, var view: HomeActivity) : BasePresenter, HomeContract.Presenter {

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun onViewCreated() {
        view.isContinueGameEnabled(false)

        compositeDisposable.add(view.observeContinueGameBtn()
                .doOnNext { view.setMessage("Continue") }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe())
        compositeDisposable.add(view.observeNewGameBtn()
                .doOnNext { view.setMessage("New Game") }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe())
        compositeDisposable.add(view.observeRulesGameBtn()
                .doOnNext { view.setMessage("Rules") }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe())
        compositeDisposable.add(view.observeSettingsGameBtn()
                .doOnNext { view.openSettingsActivity() }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe())

        compositeDisposable.add(getAllSettings())

    }

    override fun onViewDestroyed() {
        compositeDisposable.clear()
    }

    override fun onInsertSettings(settingsData: SettingsData) {
        compositeDisposable.add(Observable.just(settingsData)
                .observeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe({ settingsDao.insertSettings(it) }
                        , { throwable -> Timber.d(throwable.localizedMessage) }))

    }

    override fun getAllSettings(): Disposable {
        return settingsDao.getAllSettings()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it.isEmpty()) {
                        onInsertSettings(SettingsData())
                    }
                }, { throwable -> Timber.d(throwable.localizedMessage) })
    }

}