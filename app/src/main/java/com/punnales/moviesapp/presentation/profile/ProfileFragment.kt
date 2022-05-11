package com.punnales.moviesapp.presentation.profile

import android.os.Bundle
import android.text.Editable
import android.view.View
import androidx.fragment.app.commitNow
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar
import com.punnales.moviesapp.R
import com.punnales.moviesapp.core.domain.Transaction
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
    private var transactionBottomSheet: TransactionBottomSheet? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentProfileBinding.bind(view)
        moviesAppProgressLoader = MoviesAppProgressLoader()
        super.onViewCreated(view, savedInstanceState)
    }

    override fun setupViews() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.sendUserIntent(UserIntent.UpdateUserProfile)
        }
        binding.btnShowTransactions.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launchWhenStarted {
                viewModel.sendUserIntent(UserIntent.ShowTransactions(binding.etCardNumber.text?.toString()))
            }
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
            binding.apply {
                tvProfileName.text = getString(R.string.full_name, firstName, lastName)
                tvProfileEmail.text = email
                tvProfilePhoneNumber.text = phoneNumber
                tvProfileCardNumber.text = getString(R.string.profile_card_number, cardNumber)
                Glide.with(root)
                    .load(profilePicture)
                    .placeholder(R.drawable.ic_user)
                    .into(ivLoginImage)
            }
        }
    }

    override fun handleSingleEvent(event: SingleEvent) {
        when (event) {
            SingleEvent.ShowConnectionError -> showAlert(R.string.alert_connection_error)
            SingleEvent.EmptyCardNumberError -> showAlert(R.string.alert_empty_card_number)
            is SingleEvent.ShowTransactionBottomSheet -> showTransactionBottomSheet(event.transactionList)
            SingleEvent.ShowUserNotFoundError -> showAlert(R.string.alert_user_not_found)
        }
    }

    private fun showTransactionBottomSheet(transactionList: List<Transaction>) {
        if (transactionBottomSheet == null)
            transactionBottomSheet = TransactionBottomSheet(transactionList)
        transactionBottomSheet?.show(childFragmentManager, TransactionBottomSheet.TAG)
    }

    private fun showProgressLoader() {
        hideProgressLoader()
        moviesAppProgressLoader.show(childFragmentManager, TAG)
    }

    private fun hideProgressLoader() {
        if (moviesAppProgressLoader.isVisible || moviesAppProgressLoader.isAdded)
            moviesAppProgressLoader.dismissAllowingStateLoss()
    }

    private fun showAlert(message: Int) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
    }

    override fun onStop() {
        super.onStop()
        transactionBottomSheet?.dismissAllowingStateLoss()
        hideProgressLoader()
    }

    sealed class UserIntent : MviIntent {
        object UpdateUserProfile : UserIntent()
        class ShowTransactions(val cardNumber: String?) : UserIntent()
    }

    sealed class ViewState : MviViewState {
        object Idle : ViewState()
        object Loading : ViewState()
        class Loaded(val user: User) : ViewState()
    }

    sealed class SingleEvent : MviSingleEvent {
        object ShowConnectionError : SingleEvent()
        object ShowUserNotFoundError : SingleEvent()
        object EmptyCardNumberError : SingleEvent()
        class ShowTransactionBottomSheet(val transactionList: List<Transaction>) : SingleEvent()
    }

}