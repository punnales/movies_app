package com.punnales.moviesapp.presentation.utils

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.punnales.moviesapp.R
import com.punnales.moviesapp.databinding.MovieAppProgressLoaderBinding

class MoviesAppProgressLoader : DialogFragment() {

    companion object {
        const val TAG = "MOVIES_APP_PROGRESS_LOADER"
    }

    private lateinit var binding: MovieAppProgressLoaderBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isCancelable = false
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = MovieAppProgressLoaderBinding.inflate(inflater, container, false)
        requireDialog().window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return binding.root
    }

    override fun getTheme() = R.style.MoviesAppProgressLoaderTheme
}