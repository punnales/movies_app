package com.punnales.moviesapp.presentation.login

import androidx.datastore.core.DataStore
import androidx.lifecycle.viewModelScope
import com.punnales.moviesapp.UserProto
import com.punnales.moviesapp.core.domain.User
import com.punnales.moviesapp.core.interactors.LoginUser
import com.punnales.moviesapp.core.mvi.AMviViewModel
import com.punnales.moviesapp.presentation.login.LoginFragment.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val loginUser: LoginUser, private val userDatastore: DataStore<UserProto>) : AMviViewModel<UserIntent, ViewState, SingleEvent>() {

    private val _viewState =
        MutableStateFlow<ViewState>(ViewState.Idle)
    override val viewState: StateFlow<ViewState>
        get() = _viewState

    override suspend fun handleIntent(intent: UserIntent) {
        when (intent) {
            is UserIntent.Login -> handleLoginIntent(intent)
        }
    }

    private fun handleLoginIntent(intent: UserIntent.Login) {
        viewModelScope.launch {
            loginUser(intent.user, intent.password).collect {
                when (it) {
                    LoginUser.LoginUserResult.ConnectionError -> {
                        _viewState.update { ViewState.Idle }
                        sendEvent(SingleEvent.ShowConnectionError)
                    }
                    LoginUser.LoginUserResult.WrongCredentialsError -> {
                        _viewState.update { ViewState.Idle }
                        sendEvent(SingleEvent.ShowWrongCredentialsError)
                    }
                    LoginUser.LoginUserResult.Loading -> _viewState.update { ViewState.Loading }
                    is LoginUser.LoginUserResult.Success -> {
                        storeUserData(it.user)
                        _viewState.update { ViewState.Idle }
                        sendEvent(SingleEvent.NavigateToHomeFragment)
                    }
                }
            }
        }
    }

    private suspend fun storeUserData(user: User) {
        userDatastore.updateData {
            it.toBuilder()
                .setAccessToken(user.accessToken)
                .setTokenType(user.tokenType)
                .setUserName(user.userName)
                .build()
        }
    }
}