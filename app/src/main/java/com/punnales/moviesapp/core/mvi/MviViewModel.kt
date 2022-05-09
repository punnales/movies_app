package com.punnales.moviesapp.core.mvi

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface MviViewModel<I : MviIntent, S : MviViewState, E : MviSingleEvent> {

    /**
     * Expose only the immutable state for this members
     */
    val viewState: StateFlow<S>

    val userIntent: Flow<I>

    val singleEvent: Flow<E>

    suspend fun handleIntent(intent: I)
}