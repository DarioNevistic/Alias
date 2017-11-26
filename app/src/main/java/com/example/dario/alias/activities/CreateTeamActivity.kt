package com.example.dario.alias.activities

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.Window
import com.example.dario.alias.adapters.TeamsAdapter
import com.example.dario.alias.models.Team
import kotlinx.android.synthetic.main.activity_create_teams.*
import kotlinx.android.synthetic.main.dialog_new_team.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import com.example.dario.alias.R


/**
 * Created by dario on 11/15/17.
 */
class CreateTeamActivity : AppCompatActivity() {

    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var teamsAdapter: TeamsAdapter

    companion object {
        var teamList: ArrayList<Team> = ArrayList()
        fun getTeamsList() : ArrayList<Team> = teamList
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_teams)

        setRecyclerView()
        setOnClickListeners()
    }

    fun setRecyclerView() {
        linearLayoutManager = LinearLayoutManager(this)
        teams_recyclerview.layoutManager = linearLayoutManager

        teamsAdapter = TeamsAdapter(teamList)
        teams_recyclerview.adapter = teamsAdapter
    }

    fun setOnClickListeners() {
        create_team_btn.onClick { createTeamDialog() }

        play_btn.onClick {
            val intent = Intent(this@CreateTeamActivity, PlayActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    fun createTeamDialog() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_new_team)

        dialog.cancel_team_dialog_btn.onClick {
            dialog.hide()
        }

        dialog.save_team_dialog_btn.onClick {
            val teamName = dialog.team_name_edittext.text.toString()
            val player1 = dialog.player1_edittext.text.toString()
            val player2 = dialog.player2_edittext.text.toString()
            val team = Team(teamName, player1, player2, 0)
            println(team)
            teamList.add(team)
            teamsAdapter.notifyDataSetChanged()
            dialog.hide()
        }
        dialog.show()
    }
}