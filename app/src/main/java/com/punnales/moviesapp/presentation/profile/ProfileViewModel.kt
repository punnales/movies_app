package com.punnales.moviesapp.presentation.profile

import androidx.datastore.core.DataStore
import androidx.lifecycle.viewModelScope
import com.punnales.moviesapp.UserProto
import com.punnales.moviesapp.core.interactors.user.FetchTransactionList
import com.punnales.moviesapp.core.interactors.user.FetchUserProfile
import com.punnales.moviesapp.core.mvi.AMviViewModel
import com.punnales.moviesapp.data.local.datastore.mapper.fromDatastore
import com.punnales.moviesapp.presentation.profile.ProfileFragment.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userDataStore: DataStore<UserProto>,
    private val fetchUserProfile: FetchUserProfile,
    private val fetchTransactionList: FetchTransactionList,
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
                _viewState.update { ViewState.Loaded(userProto.fromDatastore()) }
            }
        }
    }

    override suspend fun handleIntent(intent: UserIntent) {
        when (intent) {
            is UserIntent.UpdateUserProfile -> {
                updateUserProfile()
            }
            is UserIntent.ShowTransactions -> handleShowTransactionIntent(intent.cardNumber)
        }
    }

    private fun handleShowTransactionIntent(cardNumber: String?) {
        viewModelScope.launch {
            if (cardNumber.isNullOrEmpty())
                sendEvent(SingleEvent.EmptyCardNumberError)
            else
                loadTransactionList(cardNumber)
        }
    }

    private fun loadTransactionList(cardNumber: String) {
        viewModelScope.launch {
            fetchTransactionList(cardNumber).collect {
                if (it !is FetchTransactionList.FetchTransactions.Loading)
                    _viewState.update { ViewState.Idle }

                when (it) {
                    FetchTransactionList.FetchTransactions.ConnectionError -> {
                        sendEvent(SingleEvent.ShowConnectionError)
                    }
                    FetchTransactionList.FetchTransactions.Loading -> _viewState.update { ViewState.Loading }
                    is FetchTransactionList.FetchTransactions.Success -> {
                        sendEvent(SingleEvent.ShowTransactionBottomSheet(it.listTransactions))
                    }
                    FetchTransactionList.FetchTransactions.UserNotFoundError -> {
                        sendEvent(SingleEvent.ShowUserNotFoundError)
                    }
                }
            }
        }
    }

    private suspend fun updateUserProfile() {
        viewModelScope.launch {
            userDataStore.data.firstOrNull()?.let { user ->
                fetchUserProfile("${user.tokenType} ${user.accessToken}").collect { result ->
                    when (result) {
                        FetchUserProfile.FetchUserProfileResult.ConnectionError -> {
                            userDataStore.data.firstOrNull()?.let { userProto ->
                                _viewState.update { ViewState.Loaded(userProto.fromDatastore()) }
                            }
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