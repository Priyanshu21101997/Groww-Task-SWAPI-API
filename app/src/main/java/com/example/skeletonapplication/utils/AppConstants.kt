package com.example.skeletonapplication.utils

object AppConstants {

    const val BASE_URL = "https://swapi.dev/"
    const val HEIGHT = "HEIGHT : "
    const val MASS = "MASS : "
    const val male = "male"
    const val female = "female"
}

enum class Filter {
    NAME,
    CREATION,
    UPDATION,
    MALES,
    FEMALES,
    NONE
}