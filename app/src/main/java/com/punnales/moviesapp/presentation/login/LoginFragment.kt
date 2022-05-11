package com.punnales.moviesapp.presentation.login

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import com.punnales.moviesapp.R
import com.punnales.moviesapp.core.mvi.*
import com.punnales.moviesapp.databinding.FragmentLoginBinding
import com.punnales.moviesapp.presentation.utils.MoviesAppProgressLoader
import com.punnales.moviesapp.presentation.utils.MoviesAppProgressLoader.Companion.TAG
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : AMviFragment<LoginFragment.UserIntent, LoginFragment.ViewState, LoginFragment.SingleEvent, LoginViewModel>(R.layout.fragment_login) {

    override val viewModel: LoginViewModel by viewModels()

    private lateinit var binding: FragmentLoginBinding
    private lateinit var moviesAppProgressLoader: MoviesAppProgressLoader

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentLoginBinding.bind(view)
        moviesAppProgressLoader = MoviesAppProgressLoader()
        super.onViewCreated(view, savedInstanceState)
    }

    override fun setupViews() {
        setupLoginButton()
    }

    private fun setupLoginButton() {
        with(binding) {
            btnLoginUser.setOnClickListener {
                viewLifecycleOwner.lifecycleScope.launch {
                    viewModel.sendUserIntent(UserIntent.Login(
                        //TODO Validate email
                        etLoginEmail.text?.toString() ?: "",
                        etLoginPassword.text?.toString() ?: ""
                    ))
                }
            }
        }
    }

    override fun render(viewState: ViewState) {
        when (viewState) {
            ViewState.Idle -> {
                hideProgressLoader()
            }
            ViewState.Loading -> {
                showProgressLoader()
            }
        }
    }

    override fun handleSingleEvent(event: SingleEvent) {
        when (event) {
            SingleEvent.NavigateToHomeFragment -> navController.navigate(LoginFragmentDirections.actionLoginFragmentToProfileFragment())
            SingleEvent.ShowConnectionError -> showAlert(R.string.alert_connection_error)
            SingleEvent.ShowWrongCredentialsError -> showAlert(R.string.alert_credentials_error)
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
        class Login(val user: String, val password: String) : UserIntent()
    }

    sealed class ViewState : MviViewState {
        object Idle : ViewState()
        object Loading : ViewState()
    }

    sealed class SingleEvent : MviSingleEvent {
        object NavigateToHomeFragment : SingleEvent()
        object ShowWrongCredentialsError : SingleEvent()
        object ShowConnectionError : SingleEvent()
    }

}