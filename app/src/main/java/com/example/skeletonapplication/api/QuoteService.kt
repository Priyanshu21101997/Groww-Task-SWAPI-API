package com.example.skeletonapplication.api

import com.example.skeletonapplication.models.QuoteList
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface QuoteService {

    @GET("/quotes")
    fun getQuotes(@Query("page") page: Int) : Single<QuoteList>

}