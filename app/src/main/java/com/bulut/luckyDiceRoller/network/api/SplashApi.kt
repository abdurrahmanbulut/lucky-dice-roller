package com.bulut.luckyDiceRoller.network.api

import com.bulut.luckyDiceRoller.model.Breeds
import com.bulut.luckyDiceRoller.model.CatFact
import retrofit2.http.GET

interface SplashApi {
    @GET("fact")
    suspend fun getFacts(): CatFact

    @GET("breeds")
    suspend fun getBreeds(): Breeds
}
