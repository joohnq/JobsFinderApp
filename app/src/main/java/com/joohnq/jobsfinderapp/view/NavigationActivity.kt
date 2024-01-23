package com.joohnq.jobsfinderapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.joohnq.jobsfinderapp.R
import com.joohnq.jobsfinderapp.databinding.ActivityNavigationBinding
import com.joohnq.jobsfinderapp.viewmodel.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NavigationActivity : AppCompatActivity() {
    private val binding: ActivityNavigationBinding by lazy {
        ActivityNavigationBinding.inflate(
            layoutInflater
        )
    }
    private val authViewModel: AuthViewModel by viewModels()
    private val navController by lazy {
        val navHostFragment =
            supportFragmentManager
                .findFragmentById(R.id.navHostFragment) as NavHostFragment

        navHostFragment.navController
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initNavigation()

    }

    override fun onStart() {
        super.onStart()
//        lifecycleScope.launch {
//            authViewModel.logout()
//        }
    }

    private fun initNavigation() {
        val graphInflater = navController.navInflater
        val navigationGraph = graphInflater.inflate(R.navigation.navigation_graph)
        navController.graph = navigationGraph
        NavigationUI.setupWithNavController(binding.bottomNav, navController)
    }

}