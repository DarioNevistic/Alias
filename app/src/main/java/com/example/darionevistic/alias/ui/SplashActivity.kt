package com.example.darionevistic.alias.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import com.example.darionevistic.alias.R
import com.example.darionevistic.alias.ui.home.HomeActivity


class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed({
            val i = Intent(this, HomeActivity::class.java)
            startActivity(i)
            finish()
        }, 500)
    }
}
