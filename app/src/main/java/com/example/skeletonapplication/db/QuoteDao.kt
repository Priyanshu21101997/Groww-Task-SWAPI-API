package com.example.skeletonapplication.db

import android.graphics.pdf.PdfDocument.Page
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.skeletonapplication.models.Result
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface QuoteDao {

    @Insert
    fun addQuotes(quotes: List<Result>): Completable

    @Query("SELECT * FROM quote LIMIT 20 OFFSET :pageNo*20")
    fun getQuotes(pageNo : Int) : Single<List<Result>>

}