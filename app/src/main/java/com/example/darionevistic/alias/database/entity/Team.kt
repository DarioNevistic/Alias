package com.example.darionevistic.alias.database.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * Created by dario.nevistic on 17/03/2018.
 */
@Entity(tableName = "team")
data class Team(
        @ColumnInfo(name = "team_name")
        var teamName: String? = null

) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

}