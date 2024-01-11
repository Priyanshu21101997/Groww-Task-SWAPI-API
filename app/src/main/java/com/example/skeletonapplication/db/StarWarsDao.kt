package com.example.skeletonapplication.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.skeletonapplication.models.Results
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface StarWarsDao {

    @Insert
    fun addCharacters(charactersList: List<Results>): Completable

    @Query("SELECT * FROM characters LIMIT :limit OFFSET :pageNo * :limit")
    fun getCharacters(pageNo: Int, limit: Int = 10): Single<List<Results>>

}