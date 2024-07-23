package com.bulut.luckyDiceRoller.navigator

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.abdurrahmanbulut.sherlock.navigation.EnterTransitionCallback
import com.abdurrahmanbulut.sherlock.navigation.ExitTransitionCallback
import com.abdurrahmanbulut.sherlock.navigation.Navigation
import com.bulut.luckyDiceRoller.navigator.screen.Screen
import com.bulut.luckyDiceRoller.ui.history.HistoryScreen
import com.bulut.luckyDiceRoller.ui.main.MainScreen
import com.bulut.luckyDiceRoller.ui.splash.SplashScreen

fun NavGraphBuilder.navGraph() {
    screen(Screen.Splash.route) { SplashScreen() }
    screen(Screen.Main.route) { MainScreen() }
    screen(Screen.History.route) { HistoryScreen() }
}

fun NavGraphBuilder.screen(
    route: String,
    enterTransition: EnterTransitionCallback? = Navigation.enterTransition,
    exitTransition: ExitTransitionCallback? = Navigation.exitTransition,
    popEnterTransition: EnterTransitionCallback? = Navigation.popEnterTransition,
    popExitTransition: ExitTransitionCallback? = Navigation.popExitTransition,
    content: @Composable AnimatedContentScope.(NavBackStackEntry) -> Unit,
) {
    composable(
        route = "$route?navArg={navArg}",
        arguments = listOf(navArgument("navArg") { nullable = true }),
        enterTransition = enterTransition,
        exitTransition = exitTransition,
        popEnterTransition = popEnterTransition,
        popExitTransition = popExitTransition,
        content = content,
    )
}
