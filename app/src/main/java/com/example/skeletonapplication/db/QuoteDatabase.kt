package com.example.skeletonapplication.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.skeletonapplication.models.Result

@Database(entities = [Result::class], version = 1, exportSchema = false)
abstract class QuoteDatabase : RoomDatabase() {

    abstract fun quoteDao() : QuoteDao

}