package com.bulut.luckyDiceRoller.ui.main

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layoutId
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.abdurrahmanbulut.sherlock.navigation.Navigation
import com.abdurrahmanbulut.sherlock.navigation.Navigator
import com.bulut.luckyDiceRoller.navigator.screen
import com.bulut.luckyDiceRoller.navigator.screen.Screen
import com.bulut.luckyDiceRoller.ui.main.home.HomeScreen
import org.koin.androidx.compose.koinViewModel

fun NavGraphBuilder.mainScreen(
    route: String,
    content: @Composable AnimatedContentScope.(NavBackStackEntry) -> Unit,
) {
    screen(
        route = route,
        enterTransition = { fadeIn() },
        exitTransition = { fadeOut() },
        popEnterTransition = { fadeIn() },
        popExitTransition = { fadeOut() },
        content = content,
    )
}

fun NavGraphBuilder.mainNavGraph() {
    mainScreen(Screen.Main.Home.route) { HomeScreen() }
}

@Composable
fun MainScreen() {
    val viewModel = koinViewModel<MainScreenVM>()
    val navController = rememberNavController()
    ConstraintLayout(
        modifier = Modifier.fillMaxSize(),
        constraintSet =
            ConstraintSet {
                val navHost = createRefFor("navHost")
                val navigationBar = createRefFor("navigationBar")

                constrain(navHost) {
                    top.linkTo(parent.top)
                    bottom.linkTo(navigationBar.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                    height = Dimension.fillToConstraints
                }
                constrain(navigationBar) {
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                }
            },
    ) {
        NavHost(
            navController = navController,
            startDestination = Screen.Main.Home.route,
            enterTransition = Navigation.enterTransition,
            exitTransition = Navigation.exitTransition,
            popEnterTransition = Navigation.popEnterTransition,
            popExitTransition = Navigation.popExitTransition,
            modifier = Modifier.layoutId("navHost"),
        ) {
            viewModel.navigator = Navigator(navController)
            mainNavGraph()
        }
    }
}
