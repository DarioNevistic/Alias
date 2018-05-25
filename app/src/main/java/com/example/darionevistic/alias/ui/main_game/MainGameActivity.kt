package com.example.darionevistic.alias.ui.main_game

import android.os.Bundle
import com.example.darionevistic.alias.R
import dagger.android.support.DaggerAppCompatActivity

/**
 * Created by dario.nevistic on 08/03/2018.
 */
class MainGameActivity : DaggerAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main_game)

        val mainFragment = MainGameFragment.newInstance()
        fragmentManager.beginTransaction()
                .replace(R.id.main_fragment_holder, mainFragment)
                .addToBackStack(null)
                .commit()


    }
}