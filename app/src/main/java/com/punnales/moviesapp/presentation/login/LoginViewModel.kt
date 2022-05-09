package com.punnales.moviesapp.presentation.login

import com.punnales.moviesapp.core.mvi.AMviViewModel
import com.punnales.moviesapp.presentation.login.LoginFragment.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor() : AMviViewModel<UserIntent, ViewState, SingleEvent>() {

    private val _viewState =
        MutableStateFlow<ViewState>(ViewState.Loading)
    override val viewState: StateFlow<ViewState>
        get() = _viewState

    override suspend fun handleIntent(intent: UserIntent) {
        when (intent) {
            is UserIntent.Login -> TODO()
        }
    }
}