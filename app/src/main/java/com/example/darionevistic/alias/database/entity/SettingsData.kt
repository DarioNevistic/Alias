package com.example.darionevistic.alias.database.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * Created by dario.nevistic on 12/03/2018.
 */
@Entity(tableName = "settings")
data class SettingsData(
        @PrimaryKey
        var id: Long = 0,
        @ColumnInfo(name = "points_for_victory")
        var pointsForVictory: Int = 100,
        @ColumnInfo(name = "round_time")
        var roundTime: Int = 0,
        @ColumnInfo(name = "final_word_points_worth")
        var finalWordPointsWord: Int = 0,
        @ColumnInfo(name = "missed_word_penalty")
        var missedWordPenalty: Boolean = false,
        @ColumnInfo(name = "common_final_word")
        var commonFinalWord: Boolean = false
) {
    constructor() : this(0, 100, 60, 0, false, false)

}