package com.example.darionevistic.alias.util

import android.arch.persistence.room.TypeConverter
import com.example.darionevistic.alias.mapper.WordModel
import com.google.gson.Gson
import java.util.*


class Converter {
    @TypeConverter
    fun fromListToString(list: List<WordModel>?): String? {
        return if (list == null) null else Gson().toJson(list)
    }

    @TypeConverter
    fun stringToList(value: String?): List<WordModel>? {
        return Arrays.asList<WordModel>(*Gson().fromJson(value, Array<WordModel>::class.java))
    }
}