package com.example.darionevistic.alias.ui.teams

import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.darionevistic.alias.R
import com.example.darionevistic.alias.database.entity.Team
import com.example.darionevistic.alias.util.RecyclerViewClickListener
import kotlinx.android.synthetic.main.item_team.view.*

/**
 * Created by dario.nevistic on 16/03/2018.
 */
class TeamsAdapter(val presenter: TeamsPresenter,
                   private var teamsList: MutableList<Team>,
                   private val itemClick: RecyclerViewClickListener) : RecyclerView.Adapter<TeamsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_team, parent, false)
        return ViewHolder(v, itemClick)
    }

    override fun getItemCount(): Int = teamsList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (teamsList.size > 2) {
            holder.itemView.remove_team_btn.visibility = View.VISIBLE
        } else {
            holder.itemView.remove_team_btn.visibility = View.GONE
        }
        holder.bindItems(teamsList[position])
    }

    class ViewHolder(itemView: View, private val itemClick: RecyclerViewClickListener) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        fun bindItems(team: Team) {
            with(team) {
                val pos = layoutPosition + 1
                itemView.team_name_edittext.setText(team.teamName)
                itemView.team_no_title.text = "Team No$pos"
                itemView.remove_team_btn.setOnClickListener(this@ViewHolder)
            }

            itemView.team_name_edittext.addTextChangedListener(object: TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    team.teamName = s.toString()
                }

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                }
            })
        }

        override fun onClick(v: View?) {
            itemClick.onClick(itemView, layoutPosition)
        }
    }

    fun getItems(): MutableList<Team> = teamsList

    fun removeTeam(position: Int) {
        this.teamsList.removeAt(position)
        notifyDataSetChanged()
    }

    fun updateTeams(teams: ArrayList<Team>) {
        teamsList = teams.toMutableList()
        notifyDataSetChanged()
    }

}