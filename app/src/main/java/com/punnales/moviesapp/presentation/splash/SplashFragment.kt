package com.punnales.moviesapp.presentation.splash

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.punnales.moviesapp.R
import com.punnales.moviesapp.core.mvi.AMviFragment
import com.punnales.moviesapp.core.mvi.MviIntent
import com.punnales.moviesapp.core.mvi.MviSingleEvent
import com.punnales.moviesapp.core.mvi.MviViewState
import com.punnales.moviesapp.databinding.FragmentSplashBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SplashFragment :
    AMviFragment<SplashFragment.UserIntent, SplashFragment.ViewState, SplashFragment.SingleEvent, SplashViewModel>(
        R.layout.fragment_splash) {

    override val viewModel: SplashViewModel by viewModels()
    private lateinit var binding: FragmentSplashBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentSplashBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)
    }

    override fun setupViews() {
    }

    override fun render(viewState: ViewState) {
    }

    override fun handleSingleEvent(event: SingleEvent) = when (event) {
        SingleEvent.NavigateToLogin -> navController.navigate(SplashFragmentDirections.actionSplashFragmentToLoginFragment())
        SingleEvent.NavigateToProfileFragment -> navController.navigate(SplashFragmentDirections.actionSplashFragmentToProfileFragment())
    }

    sealed class UserIntent : MviIntent {
    }

    sealed class ViewState : MviViewState {
        object Idle : ViewState()
    }

    sealed class SingleEvent : MviSingleEvent {
        object NavigateToProfileFragment : SingleEvent()
        object NavigateToLogin : SingleEvent()
    }
}