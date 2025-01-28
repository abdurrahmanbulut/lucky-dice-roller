package com.bulut.luckyDiceRoller.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.abdurrahmanbulut.sherlock.navigation.Navigator
import com.bulut.luckyDiceRoller.navigator.AppNavigation
import com.bulut.luckyDiceRoller.ui.theme.LuckyDiceRollerTheme

val LocalNavigator = staticCompositionLocalOf<Navigator> { error("No Navigator provided") }
internal val LocalMainActivity = compositionLocalOf<MainActivity> { error("need CoreActivity") }
internal val LocalNavHostController =
    compositionLocalOf<NavHostController> { error("need CoreActivity") }

@Composable
@ReadOnlyComposable
internal fun navigator() = LocalNavigator.current

class MainActivity : ComponentActivity() {
    private lateinit var navigator: Navigator
    lateinit var navHostController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            navHostController = rememberNavController()
            navigator = Navigator(navHostController)
            LuckyDiceRollerTheme {
                CompositionLocalProvider(
                    LocalMainActivity provides this,
                    LocalNavigator provides navigator,
                    LocalNavHostController provides navHostController,
                ) {
                    Surface {
                        AppNavigation(navHostController)
                    }
                }
            }
        }
    }
}
