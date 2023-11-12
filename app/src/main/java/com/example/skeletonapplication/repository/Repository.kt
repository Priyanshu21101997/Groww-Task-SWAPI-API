package com.example.skeletonapplication.repository

import androidx.lifecycle.LiveData
import com.example.skeletonapplication.api.QuoteService
import com.example.skeletonapplication.db.QuoteDatabase
import com.example.skeletonapplication.models.QuoteList
import com.example.skeletonapplication.models.Result
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class Repository @Inject constructor(
    private val quoteService: QuoteService,
    private val quoteDatabase: QuoteDatabase,
) {



    fun getQuotesFromApi(page: Int): Single<QuoteList> {
        return quoteService.getQuotes(page)
    }

    fun storeQuotesInDB(result: List<Result>): Completable {
        return quoteDatabase.quoteDao().addQuotes(result)
    }

    fun getQuotesFromDB(pageNo: Int): Single<List<Result>> {
      return quoteDatabase.quoteDao().getQuotes(pageNo)
    }

}



