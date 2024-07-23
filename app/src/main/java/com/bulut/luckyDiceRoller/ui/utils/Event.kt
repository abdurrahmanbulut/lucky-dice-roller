package com.bulut.luckyDiceRoller.ui.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.runtime.mutableStateOf

@Immutable
sealed interface StateEvent {
    @Immutable
    data object Triggered : StateEvent {
        override fun toString(): String = "triggered"
    }

    @Immutable
    data object Consumed : StateEvent {
        override fun toString(): String = "consumed"
    }
}

val triggered = StateEvent.Triggered

val consumed = StateEvent.Consumed

fun createEvent() = mutableStateOf<StateEvent>(consumed)

fun <T> createEventWithContent() = mutableStateOf<StateEventWithContent<T>>(consumed())

fun <T1, T2> createEventWithContent2() = mutableStateOf<StateEventWithContent2<T1, T2>>(consumed2())

fun <T1, T2, T3> createEventWithContent3() = mutableStateOf<StateEventWithContent3<T1, T2, T3>>(consumed3())

fun <T1, T2, T3, T4> createEventWithContent4() = mutableStateOf<StateEventWithContent4<T1, T2, T3, T4>>(consumed4())

@Immutable
sealed interface StateEventWithContent<out T>

@Immutable
sealed interface StateEventWithContent2<out T1, out T2>

@Immutable
sealed interface StateEventWithContent3<out T1, out T2, out T3>

@Immutable
sealed interface StateEventWithContent4<out T1, out T2, out T3, out T4>

@Immutable
class StateEventWithContentTriggered<T>(
    val content: T,
) : StateEventWithContent<T>

@Immutable
class StateEventWithContentTriggered2<T1, T2>(
    val content1: T1,
    val content2: T2,
) : StateEventWithContent2<T1, T2>

@Immutable
class StateEventWithContentTriggered3<T1, T2, T3>(
    val content1: T1,
    val content2: T2,
    val content3: T3,
) : StateEventWithContent3<T1, T2, T3>

@Immutable
class StateEventWithContentTriggered4<T1, T2, T3, T4>(
    val content1: T1,
    val content2: T2,
    val content3: T3,
    val content4: T4,
) : StateEventWithContent4<T1, T2, T3, T4>

@Immutable
class StateEventWithContentConsumed<T> : StateEventWithContent<T>

@Immutable
class StateEventWithContentConsumed2<T1, T2> : StateEventWithContent2<T1, T2>

@Immutable
class StateEventWithContentConsumed3<T1, T2, T3> : StateEventWithContent3<T1, T2, T3>

@Immutable
class StateEventWithContentConsumed4<T1, T2, T3, T4> : StateEventWithContent4<T1, T2, T3, T4>

fun <T> triggered(content: T) = StateEventWithContentTriggered(content)

fun <T1, T2> triggered(
    content1: T1,
    content2: T2,
) = StateEventWithContentTriggered2(content1, content2)

fun <T1, T2, T3> triggered(
    content1: T1,
    content2: T2,
    content3: T3,
) = StateEventWithContentTriggered3(content1, content2, content3)

fun <T1, T2, T3, T4> triggered(
    content1: T1,
    content2: T2,
    content3: T3,
    content4: T4,
) = StateEventWithContentTriggered4(content1, content2, content3, content4)

fun <T> consumed() = StateEventWithContentConsumed<T>()

fun <T1, T2> consumed2() = StateEventWithContentConsumed2<T1, T2>()

fun <T1, T2, T3> consumed3() = StateEventWithContentConsumed3<T1, T2, T3>()

fun <T1, T2, T3, T4> consumed4() = StateEventWithContentConsumed4<T1, T2, T3, T4>()

@Composable
@NonRestartableComposable
fun SingleEvent(
    event: MutableState<StateEvent>,
    action: suspend () -> Unit,
) {
    LaunchedEffect(key1 = event.value) {
        if (event.value is StateEvent.Triggered) {
            action()
            event.value = consumed
        }
    }
}

@Composable
@NonRestartableComposable
fun <T> SingleEvent(
    event: MutableState<StateEventWithContent<T>>,
    action: suspend (T) -> Unit,
) {
    LaunchedEffect(key1 = event.value) {
        if (event.value is StateEventWithContentTriggered<T>) {
            val value = event.value as StateEventWithContentTriggered<T>
            action(value.content)
            event.value = consumed()
        }
    }
}

@Composable
@NonRestartableComposable
fun <T1, T2> SingleEvent(
    event: MutableState<StateEventWithContent2<T1, T2>>,
    action: suspend (T1, T2) -> Unit,
) {
    LaunchedEffect(key1 = event.value) {
        if (event.value is StateEventWithContentTriggered2<T1, T2>) {
            val value = event.value as StateEventWithContentTriggered2<T1, T2>
            action(value.content1, value.content2)
            event.value = consumed2()
        }
    }
}

@Composable
@NonRestartableComposable
fun <T1, T2, T3> SingleEvent(
    event: MutableState<StateEventWithContent3<T1, T2, T3>>,
    action: suspend (T1, T2, T3) -> Unit,
) {
    LaunchedEffect(key1 = event.value) {
        if (event.value is StateEventWithContentTriggered3<T1, T2, T3>) {
            val value = event.value as StateEventWithContentTriggered3<T1, T2, T3>
            action(value.content1, value.content2, value.content3)
            event.value = consumed3()
        }
    }
}

@Composable
@NonRestartableComposable
fun <T1, T2, T3, T4> SingleEvent(
    event: MutableState<StateEventWithContent4<T1, T2, T3, T4>>,
    action: suspend (T1, T2, T3, T4) -> Unit,
) {
    LaunchedEffect(key1 = event.value) {
        if (event.value is StateEventWithContentTriggered4<T1, T2, T3, T4>) {
            val value = event.value as StateEventWithContentTriggered4<T1, T2, T3, T4>
            action(value.content1, value.content2, value.content3, value.content4)
            event.value = consumed4()
        }
    }
}
