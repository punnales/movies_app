package com.punnales.moviesapp.presentation.splash

import androidx.datastore.core.DataStore
import androidx.lifecycle.viewModelScope
import com.punnales.moviesapp.UserProto
import com.punnales.moviesapp.core.mvi.AMviViewModel
import com.punnales.moviesapp.presentation.splash.SplashFragment.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val userDatastore: DataStore<UserProto>,
) :
    AMviViewModel<UserIntent, ViewState, SingleEvent>() {

    //Todo analyze MVI Arch
    //Exposing immutable state
    private val _viewState =
        MutableStateFlow<ViewState>(ViewState.Idle)
    override val viewState: StateFlow<ViewState> get() = _viewState

    init {
        loadAppStatus()
    }

    private fun loadAppStatus() {
        viewModelScope.launch {
            userDatastore.data.collect {
                if (it.accessToken.isNullOrEmpty())
                    sendEvent(SingleEvent.NavigateToLogin)
                else
                    sendEvent(SingleEvent.NavigateToProfileFragment)
            }
        }
    }

    override suspend fun handleIntent(intent: UserIntent) {

    }

}