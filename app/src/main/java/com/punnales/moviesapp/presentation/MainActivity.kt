package com.punnales.moviesapp.presentation

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.punnales.moviesapp.R
import com.punnales.moviesapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), NavController.OnDestinationChangedListener {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupBottomNavigation()
    }

    private fun setupBottomNavigation() {
        val navController = getNavController()
        NavigationUI.setupWithNavController(binding.bottomNavigationView, navController)
        navController.addOnDestinationChangedListener(this)
    }

    private fun getNavController(): NavController {
        val fragment = supportFragmentManager.findFragmentById(R.id.rootFragmentContainer)
        check(fragment is NavHostFragment) { "Activity $this does not have a NavHostFragment" }
        return fragment.navController
    }

    override fun onDestinationChanged(controller: NavController, destination: NavDestination, arguments: Bundle?) {
        binding.bottomNavigationView.visibility = if (destination.label in arrayOf("ProfileFragment", "MovieListFragment")) View.VISIBLE else View.GONE
    }
}