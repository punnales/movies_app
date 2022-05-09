package com.punnales.moviesapp.core.mvi

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.flow.collect

abstract class AMviFragment<
        I : MviIntent,
        S : MviViewState,
        E : MviSingleEvent,
        VM : MviViewModel<I, S, E>,
        > : Fragment, MviFragment<I, S, E> {

    constructor() : super()

    @Suppress("unused")
    constructor(layoutRes: Int) : super(layoutRes)

    abstract val viewModel: VM

    val navController: NavController
        get() = findNavController()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        bindViewModel()
    }

    /**
     * Setup immutable views which are not rendered by MviViewState.
     */
    abstract fun setupViews()

    /**
     * Observe MVI in viewModel
     */
    protected open fun bindViewModel() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.viewState.collect(this@AMviFragment::render)
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.singleEvent.collect(this@AMviFragment::handleSingleEvent)
        }
    }
}