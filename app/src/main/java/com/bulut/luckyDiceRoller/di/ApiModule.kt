package com.bulut.luckyDiceRoller.di

import com.bulut.luckyDiceRoller.network.api.SplashApi
import com.bulut.luckyDiceRoller.network.api.StockApi
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val baseUrl = "https://www.alphavantage.co/"

fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}

fun splashApi(retrofit: Retrofit): SplashApi {
    return retrofit.create(SplashApi::class.java)
}

fun stockApi(retrofit: Retrofit): StockApi {
    return retrofit.create(StockApi::class.java)
}

val apiModule =
    module {
        single { provideLoggingInterceptor() }
        single { provideOkHttpClient(get()) }
        single { provideRetrofit(get()) }
        single { splashApi(get()) }
        single { stockApi(get()) }
    }

fun provideLoggingInterceptor(): CustomHttpLoggingInterceptor {
    return CustomHttpLoggingInterceptor()
}

fun provideOkHttpClient(loggingInterceptor: CustomHttpLoggingInterceptor): OkHttpClient {
    return OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()
}
