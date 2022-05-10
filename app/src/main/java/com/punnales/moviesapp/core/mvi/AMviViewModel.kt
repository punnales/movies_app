package com.punnales.moviesapp.core.mvi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

abstract class AMviViewModel<I : MviIntent, S : MviViewState, E : MviSingleEvent> :
    MviViewModel<I, S, E>,
    ViewModel() {

    /**
     * Unique [Flow] used by the [MviViewModel] to listen to the [MviFragment].
     * All the [MviFragment]'s [MviIntent]s must go through this [Flow].
     */
    private val userIntentChannel = Channel<I>(Channel.UNLIMITED)
    override val userIntent: Flow<I>
        get() = userIntentChannel.receiveAsFlow()

    private val singleEventChannel = Channel<E>(Channel.UNLIMITED)
    override val singleEvent: Flow<E>
        get() = singleEventChannel.receiveAsFlow()

    init {
        subscribeIntentChannel()
    }

    private fun subscribeIntentChannel() {
        viewModelScope.launch {
            userIntent.collect(this@AMviViewModel::handleIntent)
        }
    }

    suspend fun sendUserIntent(intent: I) {
        userIntentChannel.send(intent)
    }

    suspend fun sendEvent(event: E) {
        singleEventChannel.send(event)
    }

    override fun onCleared() {
        super.onCleared()
        singleEventChannel.close()
        userIntentChannel.close()
    }
}