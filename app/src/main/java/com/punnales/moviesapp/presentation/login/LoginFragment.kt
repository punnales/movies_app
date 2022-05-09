package com.punnales.moviesapp.presentation.login

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.punnales.moviesapp.R
import com.punnales.moviesapp.core.mvi.*
import com.punnales.moviesapp.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : AMviFragment<LoginFragment.UserIntent, LoginFragment.ViewState, LoginFragment.SingleEvent, LoginViewModel>(R.layout.fragment_login) {

    override val viewModel: LoginViewModel by viewModels()

    lateinit var binding: FragmentLoginBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentLoginBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)
    }

    override fun setupViews() {

    }

    override fun render(viewState: ViewState) {
        when (viewState) {
            ViewState.Idle -> {}
            ViewState.Loading -> {}
        }
    }

    override fun handleSingleEvent(event: SingleEvent) {
        TODO("Not yet implemented")
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
    }

}