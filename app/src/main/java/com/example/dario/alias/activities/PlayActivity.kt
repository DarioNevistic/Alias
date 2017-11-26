package com.example.dario.alias.activities

import android.opengl.Visibility
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.example.dario.alias.R
import com.example.dario.alias.models.Team
import kotlinx.android.synthetic.main.dialog_team_reading.*
import org.jetbrains.anko.sdk25.coroutines.onClick

/**
 * Created by dario on 11/26/17.
 */
class PlayActivity : AppCompatActivity() {

    val teamsList: ArrayList<Team> = CreateTeamActivity.getTeamsList()
    val teamsNumber: Int = CreateTeamActivity.getTeamsList().size
    var teamPlaying: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play)

        loadPreGameDialog()

    }

    private fun loadPreGameDialog() {
        if (dialog_team_reading_layout.visibility == View.GONE) {
            dialog_team_reading_layout.visibility = View.VISIBLE
        }

        loadTeamInfo()

        start_game_button.onClick {
            dialog_team_reading_layout.visibility = View.GONE
            startGame()
        }
    }

    private fun loadTeamInfo() {
        if (teamPlaying < teamsNumber) {
            team_name_pregame.text = teamsList[teamPlaying].teamName
            player_reading.text = teamsList[teamPlaying].player1
            player_guessing.text = teamsList[teamPlaying].player2
            team_score.text = teamsList[teamPlaying].teamScore.toString()
        } else {
            for (team in teamsList) {
                val pom = team.player1
                team.player1 = team.player2
                team.player2 = pom

                teamPlaying = 0
                loadTeamInfo()
            }
        }
    }

    private fun startGame() {
        if (dialog_team_reading_layout.visibility == View.VISIBLE) {
            dialog_team_reading_layout.visibility = View.GONE
        }

        // TODO write play-game xml, collect all words, write game algorithm
    }
}