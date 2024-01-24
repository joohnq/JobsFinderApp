package com.joohnq.jobsfinderapp.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.joohnq.jobsfinderapp.R
import com.joohnq.jobsfinderapp.databinding.ActivityNavigationBinding
import com.joohnq.jobsfinderapp.viewmodel.AuthViewModel
import com.joohnq.jobsfinderapp.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NavigationActivity : AppCompatActivity() {
    private val binding: ActivityNavigationBinding by lazy {
        ActivityNavigationBinding.inflate(
            layoutInflater
        )
    }
    private val authViewModel: AuthViewModel by viewModels()
    private val userViewModel: UserViewModel by viewModels()
    private val navController by lazy {
        val navHostFragment =
            supportFragmentManager
                .findFragmentById(R.id.navHostFragment) as NavHostFragment

        navHostFragment.navController
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        checkAuthentication()
    }

    private fun checkAuthentication() {
        userViewModel.getUserUid{ id ->
            if (id == null) {
                val intent = Intent(this, PresentationActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NO_HISTORY
                startActivity(intent)
                finish()
            }else{
                initNavigation()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        lifecycleScope.launch {
            authViewModel.logout()
        }
    }

    private fun initNavigation() {
        val graphInflater = navController.navInflater
        val navigationGraph = graphInflater.inflate(R.navigation.navigation_graph)
        navController.graph = navigationGraph
        NavigationUI.setupWithNavController(binding.bottomNav, navController)
    }

}