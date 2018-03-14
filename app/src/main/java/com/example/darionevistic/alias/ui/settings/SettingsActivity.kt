package com.example.darionevistic.alias.ui.settings

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.example.darionevistic.alias.R
import com.example.darionevistic.alias.app.AliasApplication
import com.example.darionevistic.alias.database.entity.SettingsData
import com.example.darionevistic.alias.ext.Constants
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


/**
 * Created by dario.nevistic on 08/03/2018.
 */
class SettingsActivity : AppCompatActivity(), SettingsContract.View {

    @Inject
    lateinit var presenter: SettingsPresenter

    override fun onCreate(savedInstanceState: Bundle?) {

        val component: SettingsComponent = DaggerSettingsComponent.builder()
                .appComponent(AliasApplication[this].component())
                .settingsModule(SettingsModule(this)).build()

        component.inject(this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        loadSeekBars()

        presenter.onViewCreated()
    }

    override fun observePointsForVictory(): InitialValueObservable<Int> = RxSeekBar.userChanges(points_seek_bar)

    override fun observeRoundTimeInSeconds(): InitialValueObservable<Int> = RxSeekBar.userChanges(round_time_seek_bar)

    override fun observeBackBtn(): Observable<Any> = RxView.clicks(back_button)

    override fun goBackOnPreviousActivity() = finish()

    override fun setMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun getProgress(progress: Int, min: Int): Int = (progress - min) / Constants.SEEKBAR_STEP_SIZE

    override fun showPointsForVictoryValue(value: String) {
        number_of_points_value.text = value
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
                Integer.parseInt(round_time_value.text.toString()),
                Integer.parseInt(final_word_value.text.toString()),
                missed_word_switch.isChecked,
                common_final_word_switch.isChecked
        )
    }

    private fun loadSeekBars() {
        points_seek_bar!!.max = (Constants.POINTS_FOR_VICTORY_MAX - Constants.POINTS_FOR_VICTORY_MIN) / Constants.SEEKBAR_STEP_SIZE
        round_time_seek_bar!!.max = (Constants.ROUND_TIME_MAX - Constants.ROUND_TIME_MIN) / Constants.SEEKBAR_STEP_SIZE
        final_word_points_seek_bar!!.max = (160 - 15) / 5
    }

    override fun setSettings(settingsData: SettingsData) {
        points_seek_bar.progress = getProgress(settingsData.pointsForVictory, Constants.POINTS_FOR_VICTORY_MIN)
        showPointsForVictoryValue(settingsData.pointsForVictory.toString())
        round_time_seek_bar.progress = getProgress(settingsData.roundTime, Constants.ROUND_TIME_MIN)
        showRoundTimeValue(settingsData.roundTime.toString())
        final_word_points_seek_bar.progress = getProgress(settingsData.finalWordPointsWord, 15)
        showFinalWordWorthValue(settingsData.finalWordPointsWord.toString())
        missed_word_switch.isChecked = settingsData.missedWordPenalty
        common_final_word_switch.isChecked = settingsData.commonFinalWord

        changeFinalWordSettings(common_final_word_switch.isChecked)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onViewDestroyed()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        presenter.saveSettings(getSettings())
        goBackOnPreviousActivity()
    }
}