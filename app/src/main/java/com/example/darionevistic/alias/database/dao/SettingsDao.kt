package com.example.darionevistic.alias.database.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import android.arch.persistence.room.Query
import com.example.darionevistic.alias.database.entity.SettingsData
import io.reactivex.Flowable
import io.reactivex.Maybe

/**
 * Created by dario.nevistic on 12/03/2018.
 */
@Dao
interface SettingsDao {

    @Insert(onConflict = REPLACE)
    fun insertSettings(settings: SettingsData)


    @Query("SELECT * FROM settings")
    fun getAllSettings(): Flowable<List<SettingsData>>
}