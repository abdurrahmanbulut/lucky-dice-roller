package com.bulut.luckyDiceRoller.ui.splash

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.bulut.luckyDiceRoller.R
import com.bulut.luckyDiceRoller.navigator.screen.Screen
import com.bulut.luckyDiceRoller.ui.navigator
import kotlinx.coroutines.delay

@Composable
fun SplashScreen() {
    val navigator = navigator()

    LaunchedEffect(Unit) {
        delay(1500)
        navigator.pop()
        navigator.navigate(Screen.Main.route)
    }
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Content()
    }
}

@Composable
fun Content() {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.splash))
    LottieAnimation(
        modifier =
            Modifier
                .fillMaxSize()
                .padding(horizontal = 60.dp),
        composition = composition,
    )
}
