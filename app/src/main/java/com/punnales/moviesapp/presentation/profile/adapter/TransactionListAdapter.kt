package com.punnales.moviesapp.presentation.profile.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.punnales.moviesapp.core.domain.Transaction
import com.punnales.moviesapp.databinding.ItemTransactionBinding
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

class TransactionListAdapter : ListAdapter<Transaction, TransactionListAdapter.ViewHolder>(AsyncDifferConfig.Builder(DiffCallback()).build()) {

    private class DiffCallback : DiffUtil.ItemCallback<Transaction>() {

        override fun areItemsTheSame(oldItem: Transaction, newItem: Transaction) =
            oldItem == newItem


        override fun areContentsTheSame(oldItem: Transaction, newItem: Transaction) =
            oldItem == newItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            ItemTransactionBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(getItem(position)) {
            holder.binding.run {
                tvItemTransactionCinema.text = cinema
                tvItemTransactionMessage.text = message
                tvItemTransactionDate.text = LocalDateTime.parse(date).format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG))
                tvItemTransactionPoints.text = points
            }
        }
    }

    inner class ViewHolder(val binding: ItemTransactionBinding) :
        RecyclerView.ViewHolder(binding.root)
}