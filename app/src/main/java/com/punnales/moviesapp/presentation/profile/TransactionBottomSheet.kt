package com.punnales.moviesapp.presentation.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.punnales.moviesapp.R
import com.punnales.moviesapp.core.domain.Transaction
import com.punnales.moviesapp.databinding.FragmentTransactionBottomSheetBinding
import com.punnales.moviesapp.presentation.profile.adapter.TransactionListAdapter
import com.punnales.moviesapp.presentation.utils.LinearViewDecorator

class TransactionBottomSheet(private val transactionList: List<Transaction>) : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentTransactionBottomSheetBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        binding = FragmentTransactionBottomSheetBinding.inflate(inflater, container, false)

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupTransactionList()
    }

    private fun setupTransactionList() {
        val transactionListAdapter = TransactionListAdapter()
        binding.rvTransactionList.apply {
            addItemDecoration(
                LinearViewDecorator(ContextCompat.getDrawable(requireContext(),
                    R.drawable.ic_line_separator_vertical)))
            adapter = transactionListAdapter
        }
        transactionListAdapter.submitList(transactionList)
    }

    companion object {
        const val TAG = "TRANSACTION_BOTTOM_SHEET"
    }
}