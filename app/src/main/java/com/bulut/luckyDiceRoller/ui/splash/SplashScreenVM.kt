package com.bulut.luckyDiceRoller.ui.splash

import androidx.lifecycle.ViewModel
import com.bulut.luckyDiceRoller.network.repository.SplashRepository
import org.koin.core.component.KoinComponent

class SplashViewModel(private val repository: SplashRepository) : ViewModel(), KoinComponent
