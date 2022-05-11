package com.punnales.moviesapp.presentation.movie_details

import androidx.lifecycle.viewModelScope
import com.punnales.moviesapp.core.interactors.movie.GetMovie
import com.punnales.moviesapp.core.interactors.movie.GetResourceRouteList
import com.punnales.moviesapp.core.mvi.AMviViewModel
import com.punnales.moviesapp.presentation.movie_details.MovieDetailsFragment.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val getMovie: GetMovie,
    private val getResourceRouteList: GetResourceRouteList,
) : AMviViewModel<UserIntent, ViewState, SingleEvent>() {

    private val _viewState =
        MutableStateFlow<ViewState>(ViewState.Loading)
    override val viewState: StateFlow<ViewState>
        get() = _viewState

    fun loadMovieDetails(movieId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            getMovie(movieId).collect {
                when (it) {
                    GetMovie.GetMovieResult.Loading -> _viewState.emit(ViewState.Loading)
                    is GetMovie.GetMovieResult.Success -> {
                        _viewState.emit(ViewState.Loaded(it.movie, getResourceRouteList().firstOrNull() ?: emptyList()))
                    }
                }
            }
        }
    }

    override suspend fun handleIntent(intent: UserIntent) {
        when (intent) {
            is UserIntent.PlayTrailer -> sendEvent(SingleEvent.PlayTrailer)
        }
    }

}