package com.bulut.luckyDiceRoller.ui.theme

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.bulut.luckyDiceRoller.R.drawable

var drawables by mutableStateOf(Drawables())

open class Drawables {
    open val dice1: Int = 0
    open val dice2: Int = 0
    open val dice3: Int = 0
    open val dice4: Int = 0
    open val dice5: Int = 0
    open val dice6: Int = 0
    open val diceUnknown: Int = 0
    open val history: Int = 0
    open val tr: Int = 0
    open val en: Int = 0
}

class DrawablesLight : Drawables() {
    override val dice1: Int = drawable.dice_1
    override val dice2: Int = drawable.dice_2
    override val dice3: Int = drawable.dice_3
    override val dice4: Int = drawable.dice_4
    override val dice5: Int = drawable.dice_5
    override val dice6: Int = drawable.dice_6
    override val diceUnknown: Int = drawable.dice_unknown
    override val history: Int = drawable.history
    override val tr: Int = drawable.tr
    override val en: Int = drawable.en
}
