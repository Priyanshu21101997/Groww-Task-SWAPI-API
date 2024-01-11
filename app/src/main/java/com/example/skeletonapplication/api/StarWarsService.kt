package com.example.skeletonapplication.api

import com.example.skeletonapplication.models.CharactersList
import com.example.skeletonapplication.models.Films
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface StarWarsService {

    @GET("/api/people")
    fun getCharacters(@Query("page") page: Int): Single<CharactersList>

    @GET
    fun getFilm(@Url url: String): Single<Films>

}