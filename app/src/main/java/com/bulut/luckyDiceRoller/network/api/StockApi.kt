package com.bulut.luckyDiceRoller.network.api

import com.bulut.luckyDiceRoller.model.StockResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface StockApi {
    @GET("query")
    suspend fun getStockPrice(
        @Query("function") function: String,
        @Query("symbol") symbol: String,
        @Query("apikey") apiKey: String,
    ): StockResponse
}
