package com.bulut.luckyDiceRoller.di

import com.bulut.luckyDiceRoller.ui.InsetsViewModel
import com.bulut.luckyDiceRoller.ui.history.HistoryScreenVM
import com.bulut.luckyDiceRoller.ui.main.MainScreenVM
import com.bulut.luckyDiceRoller.ui.main.home.HomeScreenVM
import com.bulut.luckyDiceRoller.ui.splash.SplashViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewmodelModule =
    module {
        viewModel { SplashViewModel(get()) }
        viewModel { HomeScreenVM(get()) }
        viewModel { InsetsViewModel() }
        viewModel { MainScreenVM() }
        viewModel { HistoryScreenVM() }
    }
