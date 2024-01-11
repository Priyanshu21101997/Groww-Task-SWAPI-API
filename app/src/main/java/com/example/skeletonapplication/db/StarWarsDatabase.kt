package com.example.skeletonapplication.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.skeletonapplication.models.Results
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


@Database(entities = [Results::class], version = 1, exportSchema = false)
@TypeConverters(ArrayListConverter::class)
abstract class StarWarsDatabase : RoomDatabase() {

    abstract fun starWarsDao(): StarWarsDao

}

object ArrayListConverter {
    @TypeConverter
    fun fromString(value: String?): ArrayList<String> {
        val listType: Type = object : TypeToken<ArrayList<String?>?>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromArrayList(list: ArrayList<String?>?): String {
        val gson = Gson()
        return gson.toJson(list)
    }
}