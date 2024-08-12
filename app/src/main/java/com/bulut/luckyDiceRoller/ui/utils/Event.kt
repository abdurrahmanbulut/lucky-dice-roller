package com.bulut.luckyDiceRoller.ui.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

@Composable
fun HandleEvent(
    eventHandler: EventHandler<Unit>,
    onTriggered: suspend () -> Unit,
) {
    LaunchedEffect(eventHandler.state.value) {
        if (eventHandler.state.value is Event.NoArgTriggered) {
            onTriggered()
            eventHandler.consume()
        }
    }
}


@Composable
fun <T> HandleEvent(
    eventHandler: EventHandler<T>,
    onTriggered: suspend (T) -> Unit,
) {
    LaunchedEffect(eventHandler.state.value) {
        if (eventHandler.state.value is Event.OneArgTriggered) {
            onTriggered((eventHandler.state.value as Event.OneArgTriggered<T>).arg)
            eventHandler.consume()
        }
    }
}

@Composable
fun <T1, T2> HandleEvent(
    eventHandler: EventHandler<Pair<T1, T2>>,
    onTriggered: suspend (T1, T2) -> Unit,
) {
    LaunchedEffect(eventHandler.state.value) {
        if (eventHandler.state.value is Event.TwoArgTriggered) {
            val event = eventHandler.state.value as Event.TwoArgTriggered<T1, T2>
            onTriggered(event.arg1, event.arg2)
            eventHandler.consume()
        }
    }
}

@Composable
fun <T1, T2, T3> HandleEvent(
    eventHandler: EventHandler<Triple<T1, T2, T3>>,
    onTriggered: suspend (T1, T2, T3) -> Unit,
) {
    LaunchedEffect(eventHandler.state.value) {
        if (eventHandler.state.value is Event.ThreeArgTriggered) {
            val event = eventHandler.state.value as Event.ThreeArgTriggered<T1, T2, T3>
            onTriggered(event.arg1, event.arg2, event.arg3)
            eventHandler.consume()
        }
    }
}

class EventHandler<T> {
    val state: MutableState<Event<T>> = mutableStateOf(Event.Consumed())

    fun trigger() {
        state.value = Event.NoArgTriggered()
    }

    fun trigger(arg: T) {
        state.value = Event.OneArgTriggered(arg)
    }

    fun <T1, T2> trigger(
        arg1: T1,
        arg2: T2,
    ) {
        val event = Event.TwoArgTriggered(arg1, arg2)
        state.value = event as Event<T>
    }

    fun <T1, T2, T3> trigger(
        arg1: T1,
        arg2: T2,
        arg3: T3,
    ) {
        val event = Event.ThreeArgTriggered(arg1, arg2, arg3)
        state.value = event as Event<T>
    }

    fun consume() {
        state.value = Event.Consumed()
    }
}

sealed class Event<T> {
    class NoArgTriggered<T> : Event<T>()

    data class OneArgTriggered<T>(val arg: T) : Event<T>()

    data class TwoArgTriggered<T1, T2>(val arg1: T1, val arg2: T2) : Event<Pair<T1, T2>>()

    data class ThreeArgTriggered<T1, T2, T3>(val arg1: T1, val arg2: T2, val arg3: T3) : Event<Triple<T1, T2, T3>>()

    class Consumed<T> : Event<T>()
}
