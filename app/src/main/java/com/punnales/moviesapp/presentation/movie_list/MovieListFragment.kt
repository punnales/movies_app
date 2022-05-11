package com.punnales.moviesapp.presentation.movie_list

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.paging.map
import com.google.android.material.snackbar.Snackbar
import com.punnales.moviesapp.R
import com.punnales.moviesapp.core.mvi.AMviFragment
import com.punnales.moviesapp.core.mvi.MviIntent
import com.punnales.moviesapp.core.mvi.MviSingleEvent
import com.punnales.moviesapp.core.mvi.MviViewState
import com.punnales.moviesapp.data.local.room.dto.MovieDTO
import com.punnales.moviesapp.data.local.room.mappers.fromDatabase
import com.punnales.moviesapp.databinding.FragmentMovieListBinding
import com.punnales.moviesapp.presentation.movie_list.adapter.MovieListAdapter
import com.punnales.moviesapp.presentation.utils.GridRecyclerViewDecorator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MovieListFragment : AMviFragment<MovieListFragment.UserIntent, MovieListFragment.ViewState, MovieListFragment.SingleEvent, MovieListViewModel>(R.layout.fragment_movie_list) {

    override val viewModel: MovieListViewModel by viewModels()

    private lateinit var binding: FragmentMovieListBinding
    private var movieListAdapter: MovieListAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentMovieListBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)
    }

    override fun setupViews() {
        setupMovieList()
        binding.swipeToRefresh.setOnRefreshListener {
            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.sendUserIntent(UserIntent.UpdateMovieList)
            }
        }
    }

    private fun setupMovieList() {
        movieListAdapter = MovieListAdapter { movieId, imageUrl, image ->
            lifecycleScope.launch { viewModel.sendUserIntent(UserIntent.SelectMovie(movieId, imageUrl, image)) }
        }
        binding.rvMovieList.apply {
            adapter = movieListAdapter
            addItemDecoration(GridRecyclerViewDecorator(requireContext(), 10, 3))
            postponeEnterTransition()
            viewTreeObserver.addOnPreDrawListener {
                startPostponedEnterTransition()
                true
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.pagedMoviesFlow.collectLatest { pagingData ->
                movieListAdapter?.let { adapter ->
                    if (adapter.resourceRouteList.isNullOrEmpty())
                        adapter.resourceRouteList = viewModel.loadResourceRoutesList().firstOrNull() ?: emptyList()
                    adapter.submitData(pagingData.map(MovieDTO::fromDatabase))
                }
            }
        }
    }

    override fun render(viewState: ViewState) {
        when (viewState) {
            ViewState.Idle -> hideProgressLoader()
            ViewState.Loading -> showProgressLoader()
        }
    }

    override fun handleSingleEvent(event: SingleEvent) {
        when (event) {
            SingleEvent.ShowConnectionError -> showAlert(R.string.alert_connection_error)
            is SingleEvent.NavigateToMovieDetails -> {
                val directions = MovieListFragmentDirections.actionMovieListFragmentToMovieDetailsFragment(event.movieId, event.imageUrl)
                val extras = FragmentNavigatorExtras(
                    event.image to "image_${event.movieId}"
                )
                navController.navigate(directions, extras)
            }
        }
    }

    private fun showProgressLoader() {
        binding.swipeToRefresh.isRefreshing = true
    }

    private fun hideProgressLoader() {
        binding.swipeToRefresh.isRefreshing = false
    }

    private fun showAlert(message: Int) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
    }

    sealed class UserIntent : MviIntent {
        object UpdateMovieList : UserIntent()
        class SelectMovie(val movieId: Long, val imageUrl: String, val image: View) : UserIntent()
    }

    sealed class ViewState : MviViewState {
        object Idle : ViewState()
        object Loading : ViewState()
    }

    sealed class SingleEvent : MviSingleEvent {
        object ShowConnectionError : SingleEvent()
        class NavigateToMovieDetails(val movieId: Long, val imageUrl: String, val image: View) : SingleEvent()
    }
}