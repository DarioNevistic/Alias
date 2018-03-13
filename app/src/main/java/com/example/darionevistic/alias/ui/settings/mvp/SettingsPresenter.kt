package com.example.darionevistic.alias.ui.settings.mvp

import com.example.darionevistic.alias.base.BasePresenter
import com.example.darionevistic.alias.database.dao.SettingsDao
import com.example.darionevistic.alias.database.entity.SettingsData
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

        compositeDisposable.add(onBackPressed())
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
        Observable.just(settingsData)
                .observeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe({ settingsDao.insertSettings(it) }
                        , { error -> Timber.d(error.localizedMessage) })
    }
}
