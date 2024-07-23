package com.bulut.luckyDiceRoller.navigator.screen

sealed class Screen(val route: String) {
    data object Splash : Screen("splash")

    data object Main : Screen("main") {
        data object Home : Screen("mainHome")
    }

    data object History : Screen("history")
}
