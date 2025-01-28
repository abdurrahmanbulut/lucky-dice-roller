package com.bulut.luckyDiceRoller.navigator

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.systemBars
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.abdurrahmanbulut.sherlock.navigation.Navigation
import com.bulut.luckyDiceRoller.navigator.screen.Screen
import com.bulut.luckyDiceRoller.ui.InsetsViewModel
import org.koin.androidx.compose.koinViewModel

val LocalInsets = compositionLocalOf<InsetsViewModel> { error("No InsetsViewModel provided") }

@Composable
fun AppNavigation(navHostController: NavHostController) {
    val systemBarsPadding = WindowInsets.systemBars.asPaddingValues()
    val insets: InsetsViewModel = koinViewModel()

    insets.statusBarHeight = systemBarsPadding.calculateTopPadding()
    insets.navigationBarHeight = systemBarsPadding.calculateBottomPadding()

    CompositionLocalProvider(LocalInsets provides insets) {
        NavHost(
            navController = navHostController,
            startDestination = Screen.Splash.route,
            enterTransition = Navigation.enterTransition,
            exitTransition = Navigation.exitTransition,
            popEnterTransition = Navigation.popEnterTransition,
            popExitTransition = Navigation.popExitTransition,
        ) {
            navGraph()
        }
    }
}
