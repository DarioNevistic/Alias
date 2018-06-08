package com.example.darionevistic.alias.ui.main_game

import android.os.Bundle
import android.view.WindowManager
import com.example.darionevistic.alias.R
import com.example.darionevistic.alias.util.replaceFragment
import dagger.android.support.DaggerAppCompatActivity

/**
 * Created by dario.nevistic on 08/03/2018.
 */
class MainGameActivity : DaggerAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main_game)

        replaceFragment(MainGameFragment.newInstance(), R.id.main_fragment_holder)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()
    }
}