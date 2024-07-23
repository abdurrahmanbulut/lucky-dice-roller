package com.bulut.luckyDiceRoller.di

import com.bulut.luckyDiceRoller.constants.DataStoreHelper
import com.bulut.luckyDiceRoller.network.repository.SplashRepository
import com.bulut.luckyDiceRoller.network.repository.StockRepository
import org.koin.dsl.module

val repositoryModule =
    module {
        single { SplashRepository(get()) }
        single { StockRepository(get()) }
        single { DataStoreHelper(get()) }
    }
