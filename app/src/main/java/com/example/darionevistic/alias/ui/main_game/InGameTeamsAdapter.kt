package com.example.darionevistic.alias.ui.main_game

import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.darionevistic.alias.R
import com.example.darionevistic.alias.database.entity.Team
import kotlinx.android.synthetic.main.item_in_game_team.view.*
import timber.log.Timber

class InGameTeamsAdapter(private var teamsList: ArrayList<Team>) : RecyclerView.Adapter<InGameTeamsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_in_game_team, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return teamsList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(teamsList[position])
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindItems(team: Team) {
            with(team) {
                Timber.d("Is team playing: ${team.isTeamPlaying}")
                if(team.isTeamPlaying) {
                    itemView.team_name.setTextColor(Color.parseColor("#313131"))
                    itemView.team_points.setTextColor(Color.parseColor("#313131"))
                } else {
                    itemView.team_name.setTextColor(Color.parseColor("#c9cad2"))
                    itemView.team_points.setTextColor(Color.parseColor("#c9cad2"))
                }
                itemView.team_name.text = team.teamName
                itemView.team_points.text = team.teamPoints.toString()
            }
        }
    }

    fun updateTeams(teams: ArrayList<Team>) {
        this.teamsList = teams
        notifyDataSetChanged()
    }
}