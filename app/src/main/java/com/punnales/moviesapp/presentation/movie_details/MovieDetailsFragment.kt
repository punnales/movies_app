package com.punnales.moviesapp.presentation.movie_details

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.core.view.ViewCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.transition.TransitionInflater
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.material.chip.Chip
import com.google.common.base.Splitter
import com.punnales.moviesapp.R
import com.punnales.moviesapp.core.domain.Movie
import com.punnales.moviesapp.core.domain.ResourceCode
import com.punnales.moviesapp.core.domain.ResourceRoute
import com.punnales.moviesapp.core.domain.ResourceSize
import com.punnales.moviesapp.core.getMediaUrl
import com.punnales.moviesapp.core.mvi.AMviFragment
import com.punnales.moviesapp.core.mvi.MviIntent
import com.punnales.moviesapp.core.mvi.MviSingleEvent
import com.punnales.moviesapp.core.mvi.MviViewState
import com.punnales.moviesapp.databinding.FragmentMovieDetailsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MovieDetailsFragment : AMviFragment<MovieDetailsFragment.UserIntent, MovieDetailsFragment.ViewState, MovieDetailsFragment.SingleEvent, MovieDetailsViewModel>(R.layout.fragment_movie_details) {

    override val viewModel: MovieDetailsViewModel by viewModels()

    private lateinit var binding: FragmentMovieDetailsBinding

    private val movieDetailsFragmentArgs: MovieDetailsFragmentArgs by navArgs()

    private lateinit var exoPlayer: ExoPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = TransitionInflater.from(requireContext()).inflateTransition(android.R.transition.move)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentMovieDetailsBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)
    }

    override fun setupViews() {
        viewModel.loadMovieDetails(movieDetailsFragmentArgs.movieId)
        setupHeroImage()
        setupExoPlayer()
    }

    private fun setupExoPlayer() {
        binding.ivMovieTrailerPlay.setOnClickListener {
            lifecycleScope.launch {
                viewModel.sendUserIntent(UserIntent.PlayTrailer)
            }
        }
        exoPlayer = ExoPlayer.Builder(requireContext()).build()
    }

    private fun setupHeroImage() {
        ViewCompat.setTransitionName(binding.ivMoviePoster, "image_${movieDetailsFragmentArgs.movieId}")
        postponeEnterTransition()
        loadImage(movieDetailsFragmentArgs.imageUrl, binding.ivMoviePoster)
    }

    private fun loadImage(
        imageAddress: String,
        imageView: ImageView,
    ) {
        Glide.with(this)
            .load(imageAddress)
            .placeholder(R.color.black_variant)
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: com.bumptech.glide.request.target.Target<Drawable>?,
                    isFirstResource: Boolean,
                ): Boolean {
                    startPostponedEnterTransition()
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable,
                    model: Any,
                    target: com.bumptech.glide.request.target.Target<Drawable>,
                    dataSource: DataSource,
                    isFirstResource: Boolean,
                ): Boolean {
                    startPostponedEnterTransition()
                    return false
                }
            })
            .into(imageView)
    }

    override fun render(viewState: ViewState) {
        when (viewState) {
            is ViewState.Loaded -> {
                handleLoadedState(viewState)
            }
            ViewState.Loading -> {}
        }
    }

    private fun handleLoadedState(viewState: ViewState.Loaded) {

        with(binding) {
            tvMovieName.text = viewState.movie.name
            tvItemMovieDuration.text = viewState.movie.length
            tvItemMovieRating.text = viewState.movie.rating
            tvItemMovieSynopsis.text = viewState.movie.synopsis
            setupGenres(viewState.movie.genre)
            Glide.with(root)
                .load(viewState.movie.getMediaUrl(viewState.resourceRouteList, ResourceCode.BACKGROUND_SYNOPSIS, ResourceSize.MEDIUM))
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .placeholder(R.color.black_variant)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(ivMovieTrailerPreview)
            setupTrailer(viewState.movie.getMediaUrl(viewState.resourceRouteList, ResourceCode.TRAILER_MP4, ResourceSize.MEDIUM))
        }
    }

    private fun setupGenres(genre: String) {
        Splitter.on(",").splitToList(genre).forEach {
            binding.chipGroupGenre.addView(
                Chip(binding.root.context).apply { text = it }
            )
        }
    }

    private fun setupTrailer(mediaUrl: String) {
        val mediaItem: MediaItem = MediaItem.fromUri(mediaUrl)
        binding.movieTrailerPlayer.player = exoPlayer
        exoPlayer.apply {
            setMediaItem(mediaItem)
            prepare()
        }
    }

    override fun handleSingleEvent(event: SingleEvent) {
        when (event) {
            SingleEvent.PlayTrailer -> handlePlayTrailerEvent()
            SingleEvent.ShowConnectionError -> TODO()
        }
    }

    private fun handlePlayTrailerEvent() {
        with(binding) {
            ivMovieTrailerPreview.visibility = View.GONE
            ivMovieTrailerPlay.visibility = View.GONE
        }
        exoPlayer.play()
    }

    override fun onDestroy() {
        super.onDestroy()
        exoPlayer.release()
    }

    sealed class UserIntent : MviIntent {
        object PlayTrailer : UserIntent()
    }

    sealed class ViewState : MviViewState {
        object Loading : ViewState()
        class Loaded(val movie: Movie, val resourceRouteList: List<ResourceRoute>) : ViewState()
    }

    sealed class SingleEvent : MviSingleEvent {
        object ShowConnectionError : SingleEvent()
        object PlayTrailer : SingleEvent()
    }
}