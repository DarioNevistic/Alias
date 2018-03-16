package com.example.darionevistic.alias.ui.teams.mvp

import com.example.darionevistic.alias.base.BasePresenter
import com.example.darionevistic.alias.ui.teams.TeamsActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import timber.log.Timber

/**
 * Created by dario.nevistic on 16/03/2018.
 */
class TeamsPresenter(var view: TeamsActivity) : BasePresenter, TeamsContract.Presenter {

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()


    override fun onViewCreated() {
        compositeDisposable.addAll(onBackPressed(),
                onSettingsPressed())
    }

    override fun onViewDestroyed() {
        compositeDisposable.clear()
    }

    override fun onBackPressed(): Disposable {
        return view.observeBackBtn()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ view.goBackToPreviousActivity() },
                        { error -> Timber.d(error.localizedMessage) })
    }

    override fun onSettingsPressed(): Disposable {
        return view.observeSettingsBtn()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ view.goToSettingsActivity() },
                        { error -> Timber.d(error.localizedMessage) })
    }
}