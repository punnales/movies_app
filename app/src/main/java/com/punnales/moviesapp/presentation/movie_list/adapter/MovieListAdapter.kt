package com.punnales.moviesapp.presentation.movie_list.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.punnales.moviesapp.R
import com.punnales.moviesapp.core.domain.Movie
import com.punnales.moviesapp.core.domain.ResourceCode
import com.punnales.moviesapp.core.domain.ResourceRoute
import com.punnales.moviesapp.core.domain.ResourceSize
import com.punnales.moviesapp.core.getMediaUrl
import com.punnales.moviesapp.databinding.ItemMovieBinding

class MovieListAdapter(var resourceRouteList: List<ResourceRoute> = emptyList(), private val movieSelectedCallback: (Long, String, View) -> Unit) :
    PagingDataAdapter<Movie, MovieListAdapter.ViewHolder>(DiffCallback) {

    object DiffCallback : DiffUtil.ItemCallback<Movie>() {

        override fun areItemsTheSame(oldItem: Movie, newItem: Movie) =
            oldItem.id == newItem.id


        override fun areContentsTheSame(oldItem: Movie, newItem: Movie) =
            oldItem == newItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            ItemMovieBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder.binding) {
            getItem(position)?.let { movie ->
                val mediaUrl = movie.getMediaUrl(resourceRouteList, ResourceCode.POSTER, ResourceSize.MEDIUM)
                Glide.with(root)
                    .load(mediaUrl)
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    .placeholder(R.color.black_variant)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(ivItemMoviePoster)
                tvItemMovieName.text = movie.name
                ivItemMoviePoster.transitionName = "image_${movie.id}"
                root.setOnClickListener { movieSelectedCallback(movie.id, mediaUrl, ivItemMoviePoster) }
            }
        }
    }

    inner class ViewHolder(val binding: ItemMovieBinding) :
        RecyclerView.ViewHolder(binding.root)
}