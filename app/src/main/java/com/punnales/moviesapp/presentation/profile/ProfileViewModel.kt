package com.punnales.moviesapp.presentation.profile

import androidx.datastore.core.DataStore
import androidx.lifecycle.viewModelScope
import com.punnales.moviesapp.UserProto
import com.punnales.moviesapp.core.interactors.user.FetchUserProfile
import com.punnales.moviesapp.core.mvi.AMviViewModel
import com.punnales.moviesapp.data.local.datastore.mapper.fomDatastore
import com.punnales.moviesapp.presentation.profile.ProfileFragment.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userDataStore: DataStore<UserProto>,
    private val fetchUserProfile: FetchUserProfile,
) : AMviViewModel<UserIntent, ViewState, SingleEvent>() {

    private val _viewState =
        MutableStateFlow<ViewState>(ViewState.Idle)
    override val viewState: StateFlow<ViewState>
        get() = _viewState

    init {
        observeLocalUserProfile()
    }

    private fun observeLocalUserProfile() {
        viewModelScope.launch {
            userDataStore.data.collect { userProto ->
                _viewState.update { ViewState.Loaded(userProto.fomDatastore()) }
            }
        }
    }

    override suspend fun handleIntent(intent: UserIntent) {
        when (intent) {
            is UserIntent.UpdateUserProfile -> {
                updateUserProfile()
            }
        }
    }

    private suspend fun updateUserProfile() {
        viewModelScope.launch {
            userDataStore.data.firstOrNull()?.let { user ->
                fetchUserProfile("${user.tokenType} ${user.accessToken}").collect { result ->
                    when (result) {
                        FetchUserProfile.FetchUserProfileResult.ConnectionError -> {
                            _viewState.update { ViewState.Idle }
                            sendEvent(SingleEvent.ShowConnectionError)
                        }
                        FetchUserProfile.FetchUserProfileResult.Loading -> {
                            if (user.email.isNullOrEmpty())
                                _viewState.update { ViewState.Loading }
                        }
                        is FetchUserProfile.FetchUserProfileResult.Success -> {
                            userDataStore.updateData {
                                with(result.user) {
                                    it.toBuilder()
                                        .setEmail(email)
                                        .setFirstName(firstName)
                                        .setLastName(lastName)
                                        .setPhoneNumber(phoneNumber)
                                        .setProfilePicture(profilePicture)
                                        .setCardNumber(cardNumber)
                                        .build()
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}