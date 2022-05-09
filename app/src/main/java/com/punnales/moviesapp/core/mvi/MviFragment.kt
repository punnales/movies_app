package com.punnales.moviesapp.core.mvi

interface MviFragment<
        I : MviIntent,
        S : MviViewState,
        E : MviSingleEvent,
        > {
    /**
     * Entry point for the [MviFragment] to render itself based on a [MviViewState].
     */
    fun render(viewState: S)

    /**
     * Entry point for the [MviFragment] to handle single event.
     */
    fun handleSingleEvent(event: E)
}