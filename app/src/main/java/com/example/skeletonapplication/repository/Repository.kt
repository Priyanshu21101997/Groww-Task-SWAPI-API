package com.example.skeletonapplication.repository

import com.example.skeletonapplication.api.StarWarsService
import com.example.skeletonapplication.db.StarWarsDatabase
import com.example.skeletonapplication.models.CharactersList
import com.example.skeletonapplication.models.Films
import com.example.skeletonapplication.models.Results
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class Repository @Inject constructor(
    private val starWarsService: StarWarsService,
    private val starWarsDatabase: StarWarsDatabase,
) {


    fun getCharactersFromApi(page: Int): Single<CharactersList> {
        return starWarsService.getCharacters(page)
    }

    fun storeCharactersInDB(result: List<Results>): Completable {
        return starWarsDatabase.starWarsDao().addCharacters(result)
    }

    fun getCharactersFromDB(pageNo: Int): Single<List<Results>> {
        return starWarsDatabase.starWarsDao().getCharacters(pageNo)
    }

    fun getFilmsFromApi(film: String): Single<Films> {
        return starWarsService.getFilm(film)
    }

}



