package com.example.dario.alias.adapters

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.view.ViewGroup
import com.example.dario.alias.R
import com.example.dario.alias.extensions.inflate
import com.example.dario.alias.models.Team
import kotlinx.android.synthetic.main.item_teams.view.*


/**
 * Created by dario on 11/26/17.
 */
class TeamsAdapter(private val teams: ArrayList<Team>) : RecyclerView.Adapter<TeamsAdapter.Holder>() {

    override fun getItemCount() = teams.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val itemTeam = teams[position]
        holder.bindTeam(itemTeam)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val inflatedView = parent.inflate(R.layout.item_teams)
        return Holder(inflatedView)
    }

    class Holder(v: View) : RecyclerView.ViewHolder(v), View.OnClickListener {
        private var view: View = v
        private var team: Team? = null

        fun bindTeam(team: Team) {
            this.team = team
            view.list_team_name.text = team.teamName
            view.list_player1_name.text = team.player1
            view.list_player2_name.text = team.player2
        }

        init {
            v.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            Log.i("REC VIEW: ", "OnClick")
        }

        companion object {
            private val TEAM_KEY = "TEAM"
        }

    }

}