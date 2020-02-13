package com.example.madesubmission1.data.entities

import androidx.room.TypeConverter
import com.google.gson.Gson

/**
 * Created by Franz Andel on 2020-02-01.
 * Android Engineer
 */

class Converters {
    @TypeConverter
    fun listStringToJson(value: List<String>?): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun jsonToStringList(value: String): List<String>? {
        val objects = Gson().fromJson(value, Array<String>::class.java) as Array<String>
        return objects.toList()
    }

    @TypeConverter
    fun listIntegerToJson(number: List<Int>): String? {
        return Gson().toJson(number)
    }

    @TypeConverter
    fun jsonToIntegerList(str: String): List<Int> {
        val objects = Gson().fromJson(str, Array<Int>::class.java) as Array<Int>
        return objects.toList()
    }
}