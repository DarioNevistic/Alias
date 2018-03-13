package com.example.darionevistic.alias.ui.settings

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.example.darionevistic.alias.R
import com.example.darionevistic.alias.app.AliasApplication
import com.example.darionevistic.alias.database.entity.SettingsData
import com.example.darionevistic.alias.ui.settings.di.DaggerSettingsComponent
import com.example.darionevistic.alias.ui.settings.di.SettingsComponent
import com.example.darionevistic.alias.ui.settings.di.SettingsModule
import com.example.darionevistic.alias.ui.settings.mvp.SettingsContract
import com.example.darionevistic.alias.ui.settings.mvp.SettingsPresenter
import com.jakewharton.rxbinding2.InitialValueObservable
import com.jakewharton.rxbinding2.view.RxView
import kotlinx.android.synthetic.main.activity_settings.*
import javax.inject.Inject
import com.jakewharton.rxbinding2.widget.*
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import java.util.concurrent.TimeUnit


/**
 * Created by dario.nevistic on 08/03/2018.
 */
class SettingsActivity : AppCompatActivity(), SettingsContract.View {

    @Inject
    lateinit var presenter: SettingsPresenter

    var disposables: CompositeDisposable = CompositeDisposable()


    override fun onCreate(savedInstanceState: Bundle?) {

        val component: SettingsComponent = DaggerSettingsComponent.builder()
                .appComponent(AliasApplication[this].component())
                .settingsModule(SettingsModule(this)).build()

        component.inject(this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        presenter.onViewCreated()

        observePointsForVictory()
    }

    override fun observePointsForVictory() {
        var isSeeking = false
        val MIN = 15
        val MAX = 160
        val STEP = 5
        var progressCustom = 17
        points_seek_bar!!.max = (MAX - MIN) / STEP

        // progressCustom = MIN + (progress * STEP)

        points_seek_bar.progress = progressCustom

        val sharedSeekBarEvents = RxSeekBar.changeEvents(points_seek_bar)
                .ofType(SeekBarChangeEvent::class.java)
                .observeOn(AndroidSchedulers.mainThread()).share()

        disposables.add(sharedSeekBarEvents
                .subscribe {
                    if (it is SeekBarStartChangeEvent) {
                        isSeeking = true
                    } else if (it is SeekBarStopChangeEvent) {
                        isSeeking = false
                    }
                })

        disposables.add(sharedSeekBarEvents
                .ofType(SeekBarProgressChangeEvent::class.java)
                .filter({ it.fromUser() })
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext { showPointsForVictortValue((MIN + (it.progress() * STEP)).toString()) }
                .debounce(1000, TimeUnit.MILLISECONDS)
                .subscribe({
                    if (!isSeeking) {

                    }
                }, { error -> System.out.println("Error receiving seek bar progress" + error) }))
    }


    override fun observeBackBtn(): Observable<Any> = RxView.clicks(back_button)

    override fun goBackOnPreviousActivity() {
        finish()
    }

    override fun setMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun showPointsForVictortValue(value: String) {
        number_of_points_value.text = value
    }

    override fun showNumberOfWordsPerRoundValue(value: String) {
        number_of_words_value.text = value
    }

    override fun showTotalRoundsValue(value: String) {
        number_of_rounds_value.text = value
    }

    override fun showRoundTimeValue(value: String) {
        round_time_value.text = value
    }

    override fun showFinalWordWorthValue(value: String) {
        final_word_value.text = value
    }

    override fun changeFinalWordSettings(state: Boolean) {
        if (state) {
            final_word_layout_holder.isEnabled = true
            final_word_layout_holder.alpha = 1.0f

        } else {
            final_word_layout_holder.isEnabled = false
            final_word_layout_holder.alpha = 0.2f
        }
    }

    override fun getSettings(): SettingsData {
        return SettingsData(0,
                Integer.parseInt(number_of_points_value.text.toString()),
                Integer.parseInt(number_of_words_value.text.toString()),
                Integer.parseInt(number_of_rounds_value.text.toString()),
                Integer.parseInt(round_time_value.text.toString()),
                Integer.parseInt(final_word_value.text.toString()),
                missed_word_switch.isChecked,
                common_final_word_switch.isChecked
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onViewDestroyed()
    }
}