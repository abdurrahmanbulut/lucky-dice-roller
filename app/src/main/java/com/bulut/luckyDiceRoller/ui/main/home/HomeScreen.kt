package com.bulut.luckyDiceRoller.ui.main.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorProducer
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.LottieProperty
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieClipSpec
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieAnimatable
import com.airbnb.lottie.compose.rememberLottieComposition
import com.airbnb.lottie.compose.rememberLottieDynamicProperties
import com.airbnb.lottie.compose.rememberLottieDynamicProperty
import com.bulut.luckyDiceRoller.R
import com.bulut.luckyDiceRoller.constants.DataStoreHelper
import com.bulut.luckyDiceRoller.constants.LocalizationKeys
import com.bulut.luckyDiceRoller.constants.get
import com.bulut.luckyDiceRoller.constants.language
import com.bulut.luckyDiceRoller.constants.switchLanguage
import com.bulut.luckyDiceRoller.navigator.LocalInsets
import com.bulut.luckyDiceRoller.navigator.screen.Screen
import com.bulut.luckyDiceRoller.ui.main.MainScreenVM
import com.bulut.luckyDiceRoller.ui.navigator
import com.bulut.luckyDiceRoller.ui.theme.drawables
import com.bulut.luckyDiceRoller.ui.utils.HandleEvent
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject
import kotlin.random.Random

@Composable
fun HomeScreen(parentViewModel: MainScreenVM) {
    val viewModel: HomeScreenVM = koinViewModel()
    val insets = LocalInsets.current
    val datastore: DataStoreHelper = koinInject()

    Observe(viewModel)
    Box(
        modifier =
        Modifier
            .fillMaxSize()
            .padding(top = insets.statusBarHeight + 16.dp),
    ) {
        Content(viewModel)
    }
    LaunchedEffect(Unit) {
        viewModel.started = false
    }

    LaunchedEffect(viewModel.isRolling) {
        if (viewModel.isRolling) {
            repeat(40) {
                viewModel.rotation.snapTo(viewModel.rotation.value)
                viewModel.diceResults =
                    List(viewModel.diceCount) { Random.nextInt(1, viewModel.diceSides + 1) }
                viewModel.rotation.animateTo(
                    targetValue = viewModel.rotation.value + 360f,
                    animationSpec = tween(durationMillis = 400),
                )
            }
        } else {
            if (viewModel.started) {
                datastore.saveResults(viewModel.diceResults)
            }
            viewModel.rotation.snapTo(0f)
        }
    }
    LaunchedEffect(viewModel.diceCount) {
        viewModel.diceResults = List(viewModel.diceCount) { 1 }
    }
}

@Composable
fun Observe(viewModel: HomeScreenVM) {
    val coroutineScope = rememberCoroutineScope()
    val navigator = navigator()
    HandleEvent(viewModel.onButtonClicked) {
        if (!viewModel.isRolling) {
            viewModel.isRolling = true
            coroutineScope.launch {
                delay(2000)
                viewModel.isRolling = false
                viewModel.started = true
                viewModel.diceResults =
                    List(viewModel.diceCount) { Random.nextInt(1, viewModel.diceSides + 1) }
            }
        }
    }
    HandleEvent(viewModel.navigateToHistory) {
        navigator.navigate(Screen.History.route)
    }
}

@Composable
fun Content(
    viewModel: HomeScreenVM,
    dataStoreHelper: DataStoreHelper = koinInject(),
) {
    val isSystemInDarkTheme = isSystemInDarkTheme()
    val boxBackgroundColor = if (isSystemInDarkTheme) Color.White else Color.Black

    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.menu))
    val animationState = rememberLottieAnimatable()
    var expanded by remember { mutableStateOf(false) }
    LaunchedEffect(composition) {
        animationState.animate(
            composition = composition,
        )
    }
    LaunchedEffect(viewModel.openMenu) {
        if (viewModel.openMenu) {
            animationState.animate(
                composition = composition,
                clipSpec = LottieClipSpec.Progress(0f, 0.4f),
                reverseOnRepeat = true,
            )
        } else {
            animationState.animate(
                composition = composition,
                clipSpec = LottieClipSpec.Progress(0.5f, 1f),
            )
        }
    }
    Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        Row(
            Modifier
                .fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Box {
                Image(
                    painter = painterResource(id = if (language == "tr") drawables.tr else drawables.en),
                    contentDescription = null,
                    modifier =
                    Modifier
                        .padding(start = 24.dp)
                        .size(32.dp)
                        .clickable { expanded = expanded.not() },
                )
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                ) {
                    DropdownMenuItem(
                        text = {
                            Text("English")
                        },
                        onClick = {
                            MainScope().launch {
                                switchLanguage("en", dataStoreHelper)
                            }
                            expanded = false
                        },
                    )
                    DropdownMenuItem(
                        {
                            Text("Türkçe")
                        },
                        onClick = {
                            MainScope().launch {
                                switchLanguage("tr", dataStoreHelper)
                            }
                            expanded = false
                        },
                    )
                }
            }
            Row(
                Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.Top,
            ) {

                if (viewModel.openMenu.not()) {
                    Spacer(modifier = Modifier.height(100.dp))
                }

                LottieAnimation(
                    modifier =
                    Modifier
                        .padding(horizontal = 16.dp)
                        .size(46.dp)
                        .clickable { viewModel.openMenu = viewModel.openMenu.not() },
                    composition = composition,
                    progress = { animationState.progress },
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
            }
        }
        AnimatedVisibility(visible = viewModel.openMenu) {
            CoolStyledNumberBox(Modifier, viewModel)
        }

        DiceRoll(viewModel)
    }
}

@Composable
fun CoolStyledNumberBox(
    modifier: Modifier,
    viewModel: HomeScreenVM,
) {
    val numbers = listOf(1, 2, 3, 4)

    val isSystemInDarkTheme = isSystemInDarkTheme()
    val boxBackgroundColor = if (isSystemInDarkTheme) Color.White else Color.Black
    val textColor = if (isSystemInDarkTheme) Color.Black else Color.White

    Column(
        modifier =
        modifier
            .height(100.dp)
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            numbers.forEach { number ->
                Box(
                    modifier =
                    Modifier
                        .padding(horizontal = 6.dp)
                        .size(48.dp)
                        .background(color = boxBackgroundColor, shape = RoundedCornerShape(12.dp))
                        .clickable { viewModel.chooseNumberOfDice(number) },
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = number.toString(),
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = textColor,
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = get(LocalizationKeys.NUM_OF_DICE))
    }
}

@Composable
fun CoolStyledNumberBox(
    numbers: List<Int>,
    viewModel: HomeScreenVM,
) {
    val isSystemInDarkTheme = isSystemInDarkTheme()
    val textColor = if (isSystemInDarkTheme) Color.Black else Color.White
    val boxBackgroundColor = if (isSystemInDarkTheme) Color.White else Color.Black

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
    ) {
        numbers.forEach { number ->
            Box(
                modifier =
                Modifier
                    .padding(horizontal = 12.dp)
                    .size(36.dp)
                    .background(color = boxBackgroundColor, shape = RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = if (viewModel.isRolling || viewModel.started.not()) "?" else number.toString(),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = textColor,
                )
            }
        }
    }
}

@Composable
fun DiceRoll(viewModel: HomeScreenVM) {

    val isSystemInDarkTheme = isSystemInDarkTheme()
    val textColor = if (isSystemInDarkTheme) Color.Black else Color.White
    val boxBackgroundColor = if (isSystemInDarkTheme) Color.White else Color.Black

    LaunchedEffect(viewModel.isRolling) {
        if (viewModel.isRolling) {
            viewModel.rotation.snapTo(viewModel.rotation.value)
            repeat(10) {
                viewModel.rotation.animateTo(
                    targetValue = viewModel.rotation.value + 360f,
                    animationSpec = tween(durationMillis = 300),
                )
                delay(300)
            }
            viewModel.isRolling = false
        } else {
            viewModel.rotation.snapTo(0f)
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.height(120.dp))
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            for (i in 1..viewModel.diceCount) {
                Dice(
                    result = viewModel.diceResults.getOrElse(i - 1) { 1 },
                    diceResources = viewModel.dices,
                    viewModel,
                )
                Spacer(modifier = Modifier.width(8.dp))
            }
        }
        Spacer(modifier = Modifier.height(150.dp))
        Row(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
        ) {
            Button(
                onClick = viewModel::onButtonClicked,
                modifier =
                Modifier
                    .weight(1f)
                    .height(60.dp),
                colors =
                    ButtonColors(
                        contentColor = textColor,
                        containerColor = boxBackgroundColor,
                        disabledContentColor = Color.White,
                        disabledContainerColor = Color.DarkGray,
                    ),
                shape = RoundedCornerShape(24.dp),
            ) {
                BasicText(
                    text =
                        if (viewModel.isRolling) {
                            get(LocalizationKeys.ROLLING)
                        } else if (viewModel.started) {
                            get(LocalizationKeys.ROLL_AGAIN)
                        } else {
                            get(LocalizationKeys.ROLL_DICE)
                        },
                    color = ColorProducer { textColor},
                    style = TextStyle.Default.copy(fontSize = 24.sp),
                )
            }
            AnimatedVisibility(visible = viewModel.started) {
                Row(Modifier) {
                    Spacer(modifier = Modifier.width(12.dp))
                    Button(
                        onClick = viewModel::onDelete,
                        modifier = Modifier.height(60.dp),
                        colors =
                            ButtonColors(
                                contentColor = textColor,
                                containerColor = boxBackgroundColor,
                                disabledContentColor = Color.White,
                                disabledContainerColor = Color.DarkGray,
                            ),
                        shape = RoundedCornerShape(24.dp),
                    ) {
                        BasicText(
                            text = get(LocalizationKeys.DELETE),
                            color = ColorProducer { textColor },
                            style = TextStyle.Default.copy(fontSize = 24.sp),
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
        CoolStyledNumberBox(viewModel.diceResults, viewModel)
        Spacer(modifier = Modifier.weight(1f))
        Row(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            horizontalArrangement = Arrangement.End,
        ) {
            Image(
                painter = painterResource(id = drawables.history),
                contentDescription = null,
                modifier =
                Modifier
                    .size(32.dp)
                    .clickable { viewModel.onHistory() },
                colorFilter = ColorFilter.tint(boxBackgroundColor),
            )
        }
        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Composable
fun Dice(
    result: Int,
    diceResources: List<Int>,
    viewModel: HomeScreenVM,
) {
    Box(
        modifier = Modifier.size(if (viewModel.diceCount > 3) 76.dp else if (viewModel.diceCount > 2) 100.dp else 140.dp),
        contentAlignment = Alignment.Center,
    ) {
        val resourceId = diceResources.getOrNull(result - 1) ?: diceResources.first()
        Image(
            painter = painterResource(id = if (viewModel.started.not() && viewModel.isRolling.not()) drawables.diceUnknown else resourceId),
            contentDescription = null,
            modifier =
            Modifier
                .size(if (viewModel.diceCount > 3) 76.dp else if (viewModel.diceCount > 2) 80.dp else 120.dp)
                .graphicsLayer(
                    rotationZ = if (viewModel.isRolling) viewModel.rotation.value else 0f,
                    transformOrigin = TransformOrigin.Center,
                ),
        )
    }
}
