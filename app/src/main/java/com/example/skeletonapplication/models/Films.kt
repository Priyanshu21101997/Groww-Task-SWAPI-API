package com.example.skeletonapplication.models

import com.google.gson.annotations.SerializedName

data class Films(

    @SerializedName("title") var title: String? = null,
    @SerializedName("episode_id") var episodeId: Int? = null,
    @SerializedName("opening_crawl") var openingCrawl: String? = null,
    @SerializedName("director") var director: String? = null,
    @SerializedName("producer") var producer: String? = null,
    @SerializedName("release_date") var releaseDate: String? = null,
    @SerializedName("characters") var characters: ArrayList<String> = arrayListOf(),
    @SerializedName("planets") var planets: ArrayList<String> = arrayListOf(),
    @SerializedName("starships") var starships: ArrayList<String> = arrayListOf(),
    @SerializedName("vehicles") var vehicles: ArrayList<String> = arrayListOf(),
    @SerializedName("species") var species: ArrayList<String> = arrayListOf(),
    @SerializedName("created") var created: String? = null,
    @SerializedName("edited") var edited: String? = null,
    @SerializedName("url") var url: String? = null

)