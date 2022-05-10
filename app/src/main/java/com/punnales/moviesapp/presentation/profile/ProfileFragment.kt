package com.punnales.moviesapp.presentation.profile

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import com.punnales.moviesapp.R
import com.punnales.moviesapp.core.domain.User
import com.punnales.moviesapp.core.mvi.AMviFragment
import com.punnales.moviesapp.core.mvi.MviIntent
import com.punnales.moviesapp.core.mvi.MviSingleEvent
import com.punnales.moviesapp.core.mvi.MviViewState
import com.punnales.moviesapp.databinding.FragmentProfileBinding
import com.punnales.moviesapp.presentation.utils.MoviesAppProgressLoader
import com.punnales.moviesapp.presentation.utils.MoviesAppProgressLoader.Companion.TAG
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : AMviFragment<ProfileFragment.UserIntent, ProfileFragment.ViewState, ProfileFragment.SingleEvent, ProfileViewModel>(R.layout.fragment_profile) {

    override val viewModel: ProfileViewModel by viewModels()

    private lateinit var binding: FragmentProfileBinding
    private lateinit var moviesAppProgressLoader: MoviesAppProgressLoader

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentProfileBinding.bind(view)
        moviesAppProgressLoader = MoviesAppProgressLoader()
        super.onViewCreated(view, savedInstanceState)
    }

    override fun setupViews() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.sendUserIntent(UserIntent.UpdateUserProfile)
        }
    }

    override fun render(viewState: ViewState) {

        if (viewState != ViewState.Loading)
            hideProgressLoader()

        when (viewState) {
            ViewState.Idle -> {}
            ViewState.Loading -> {
                showProgressLoader()
            }
            is ViewState.Loaded -> {
                handleLoadedState(viewState)
            }
        }
    }

    private fun handleLoadedState(viewState: ViewState.Loaded) {
        with(viewState.user) {
            binding.tvProfileName.text = getString(R.string.full_name, firstName, lastName)
            binding.tvProfileEmail.text = email
            binding.tvProfilePhoneNumber.text = phoneNumber
            binding.tvProfileCardNumber.text = getString(R.string.profile_card_number, cardNumber)
        }
    }

    override fun handleSingleEvent(event: SingleEvent) {
        when (event) {
            SingleEvent.ShowConnectionError -> showAlert(R.string.alert_connection_error)
        }
    }

    private fun showProgressLoader() {
        hideProgressLoader()
        moviesAppProgressLoader.show(childFragmentManager, TAG)
    }

    private fun hideProgressLoader() {
        if (moviesAppProgressLoader.isVisible || moviesAppProgressLoader.isAdded)
            moviesAppProgressLoader.dismiss()
    }

    private fun showAlert(message: Int) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
    }

    sealed class UserIntent : MviIntent {
        object UpdateUserProfile : UserIntent()
    }

    sealed class ViewState : MviViewState {
        object Idle : ViewState()
        object Loading : ViewState()
        class Loaded(val user: User) : ViewState()
    }

    sealed class SingleEvent : MviSingleEvent {
        object ShowConnectionError : SingleEvent()
    }

}