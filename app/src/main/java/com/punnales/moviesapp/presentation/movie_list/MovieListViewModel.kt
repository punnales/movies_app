package com.punnales.moviesapp.presentation.movie_list

import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.punnales.moviesapp.core.domain.Movie
import com.punnales.moviesapp.core.domain.ResourceRoute
import com.punnales.moviesapp.core.interactors.movie.*
import com.punnales.moviesapp.core.mvi.AMviViewModel
import com.punnales.moviesapp.presentation.movie_list.MovieListFragment.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val fetchMovieList: FetchMovieList,
    private val addMovieList: AddMovieList,
    private val getPagedMovieList: GetPagedMovieList,
    private val addRouteResourceList: AddResourceRouteList,
    private val getResourceRouteList: GetResourceRouteList,
) : AMviViewModel<UserIntent, ViewState, SingleEvent>() {

    private val _viewState =
        MutableStateFlow<ViewState>(ViewState.Idle)
    override val viewState: StateFlow<ViewState>
        get() = _viewState

    val pagedMoviesFlow = getPagedMovieList().flow.cachedIn(viewModelScope)

    init {
        updateMovieList()
    }

    suspend fun loadResourceRoutesList() = getResourceRouteList()

    private fun updateMovieList() {
        viewModelScope.launch {
            fetchMovieList().collect {
                when (it) {
                    FetchMovieList.FetchMovieListResult.ConnectionError -> {
                        _viewState.update { ViewState.Idle }
                    }
                    FetchMovieList.FetchMovieListResult.Loading -> {
                        _viewState.update { ViewState.Loading }
                    }
                    is FetchMovieList.FetchMovieListResult.Success -> {
                        _viewState.update { ViewState.Idle }
                        saveLocalData(it.movieListPair)
                    }
                }
            }
        }
    }

    private fun saveLocalData(movieListPair: Pair<List<Movie>, List<ResourceRoute>>) {
        saveMovieList(movieListPair.first)
        saveResourceRouteList(movieListPair.second)
    }

    private fun saveResourceRouteList(resourceRouteList: List<ResourceRoute>) {
        viewModelScope.launch(Dispatchers.IO) {
            addRouteResourceList(resourceRouteList).collect()
        }
    }

    private fun saveMovieList(moviesList: List<Movie>) {
        viewModelScope.launch(Dispatchers.IO) {
            addMovieList(moviesList).collect()
        }
    }

    override suspend fun handleIntent(intent: UserIntent) {
        when (intent) {
            UserIntent.UpdateMovieList -> updateMovieList()
            is UserIntent.SelectMovie -> sendEvent(SingleEvent.NavigateToMovieDetails(intent.movieId, intent.imageUrl, intent.image))
        }
    }

}