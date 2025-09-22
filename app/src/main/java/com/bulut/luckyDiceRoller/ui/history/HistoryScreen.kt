package com.bulut.luckyDiceRoller.ui.history

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.airbnb.lottie.LottieProperty
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.airbnb.lottie.compose.rememberLottieDynamicProperties
import com.airbnb.lottie.compose.rememberLottieDynamicProperty
import com.bulut.luckyDiceRoller.R
import com.bulut.luckyDiceRoller.constants.DataStoreHelper
import com.bulut.luckyDiceRoller.constants.LocalizationKeys
import com.bulut.luckyDiceRoller.constants.get
import com.bulut.luckyDiceRoller.navigator.LocalInsets
import com.bulut.luckyDiceRoller.ui.navigator
import com.bulut.luckyDiceRoller.ui.utils.HandleEvent
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject

@Composable
fun HistoryScreen() {
    val viewModel: HistoryScreenVM = koinViewModel()
    val dataStore: DataStoreHelper = koinInject()
    val results by dataStore.resultsFlow.collectAsState(initial = emptyList())
    Observe(viewModel, dataStore)
    Content(viewModel, results)
    MyPopup(viewModel)
}

@Composable
fun Observe(
    viewModel: HistoryScreenVM,
    dataStore: DataStoreHelper,
) {
    val navigator = navigator()
    HandleEvent(viewModel.onBack) {
        navigator.pop()
    }
    HandleEvent(viewModel.onPopup) {
        viewModel.showDialog = true
    }
    HandleEvent(viewModel.onClear) {
        viewModel.showDialog = false
        dataStore.clearResults()
    }
}

@Composable
fun Content(
    viewModel: HistoryScreenVM,
    results: List<List<Int>>,
) {
    val insets = LocalInsets.current
    val composition2 by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.delete))
    val composition3 by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.empty))
    val isDarkTheme = isSystemInDarkTheme()
    val boxBackgroundColor = if (isDarkTheme) Color.White else Color.Black

    Column(
        Modifier
            .fillMaxSize()
            .padding(top = insets.statusBarHeight + 16.dp, start = 16.dp, end = 16.dp),
    ) {
        Row(
            Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
        ) {

            IconButton(
                onClick = { viewModel.onBack() },
                modifier = Modifier.size(46.dp)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = boxBackgroundColor,
                    modifier = Modifier.size(32.dp)
                )
            }

            Row(Modifier.weight(1f), horizontalArrangement = Arrangement.Center) {
                Text(text = get(LocalizationKeys.DICE_HISTORY), style = TextStyle.Default.copy(fontSize = 24.sp))
            }
            if (results.isNotEmpty()) {
                LottieAnimation(
                    modifier =
                        Modifier
                            .size(46.dp)
                            .clickable { viewModel.onDelete() },
                    composition = composition2,
                    dynamicProperties = rememberLottieDynamicProperties(
                        properties = arrayOf(
                            rememberLottieDynamicProperty(
                                property = LottieProperty.COLOR,
                                value = boxBackgroundColor.toArgb(),
                                keyPath = arrayOf("**")
                            )
                        )
                    )
                )
            } else {
                Box(
                    modifier =
                        Modifier
                            .size(46.dp)
                            .background(Color.Transparent),
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        if (results.isEmpty()) {
            LottieAnimation(
                modifier =
                    Modifier
                        .fillMaxSize()
                        .padding(horizontal = 24.dp, vertical = 36.dp),
                composition = composition3,
            )
        } else {
            Column(
                Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState()),
            ) {
                results.reversed().forEachIndexed { index, result ->
                    Row(
                        modifier = Modifier.padding(horizontal = 12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                    ) {
                        result.forEach { number ->
                            Box(
                                modifier =
                                    Modifier
                                        .background(
                                            color = if (isDarkTheme) Color.White else Color.Black,
                                            shape = RoundedCornerShape(4.dp),
                                        )
                                        .padding(8.dp),
                            ) {
                                Text(text = number.toString(), fontWeight = FontWeight.Bold, color = if (isDarkTheme) Color.Black else Color.White)
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                        if (index == 0) {
                            Spacer(modifier = Modifier.weight(1f))
                            Text(
                                text = get(LocalizationKeys.LAST_DICE),
                                style = TextStyle.Default.copy(fontSize = 16.sp),
                                fontFamily = FontFamily.Monospace,
                                fontWeight = FontWeight.Bold,
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
        }
    }
}

@Composable
fun MyPopup(viewModel: HistoryScreenVM) {

    val isDarkTheme = isSystemInDarkTheme()
    val textColor = if (isDarkTheme) Color.Black else Color.White
    val boxBackgroundColor = if (isDarkTheme) Color.White else Color.Black

    if (viewModel.showDialog) {
        Dialog(onDismissRequest = { viewModel.showDialog = false }) {
            Surface(
                shape = MaterialTheme.shapes.medium,
                color = boxBackgroundColor
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(text = get(LocalizationKeys.DELETE_POPUP_INFO), color = textColor)
                    Spacer(modifier = Modifier.height(24.dp))
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                    ) {
                        Button(
                            onClick = viewModel::delete,
                            colors =
                                ButtonColors(
                                    contentColor = textColor,
                                    containerColor = boxBackgroundColor,
                                    disabledContentColor = Color.White,
                                    disabledContainerColor = Color.DarkGray,
                                ),
                        ) {
                            Text(text = get(LocalizationKeys.DELETE_POPUP_APPROVE_BUTTON))
                        }
                        Button(
                            onClick = { viewModel.showDialog = false },
                            colors =
                                ButtonColors(
                                    contentColor = textColor,
                                    containerColor = boxBackgroundColor,
                                    disabledContentColor = Color.White,
                                    disabledContainerColor = Color.DarkGray,
                                ),
                        ) {
                            Text(text = get(LocalizationKeys.DELETE_POPUP_CANCEL_BUTTON))
                        }
                    }
                }
            }
        }
    }
}
