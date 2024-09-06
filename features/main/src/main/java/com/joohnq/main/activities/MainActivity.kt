package com.joohnq.main.activities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.joohnq.core.BaseActivity
import com.joohnq.core.navigation.MainNavigation
import com.joohnq.core.setOnApplyWindowInsetsListener
import com.joohnq.core.state.UiState
import com.joohnq.favorite_ui.viewmodel.FavoritesViewModel
import com.joohnq.home.viewmodel.HomeViewModel
import com.joohnq.job_ui.viewmodel.JobsViewModel
import com.joohnq.main.activities.navigation.MainNavigationImpl
import com.joohnq.main.databinding.ActivityMainBinding
import com.joohnq.user.user_ui.viewmodel.UserViewModel
import com.joohnq.user_domain.entities.User
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity: BaseActivity<ActivityMainBinding>() {
				private val userViewModel: UserViewModel by viewModels()
				private val homeViewModel: HomeViewModel by viewModels()
				private val jobsViewModel: JobsViewModel by viewModels()
				private val favoritesViewModel: FavoritesViewModel by viewModels()
				private val navController by lazy {
								val navHostFragment =
												supportFragmentManager.findFragmentById(binding.navHostFragment.id) as NavHostFragment
								navHostFragment.navController
				}

				override fun inflateBinding(
								inflater: LayoutInflater,
								container: ViewGroup?
				): ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)

				override fun onCreate(savedInstanceState: Bundle?) {
								super.onCreate(savedInstanceState)
								enableEdgeToEdge()
								binding.setOnApplyWindowInsetsListener(bottoms = 0)
								NavigationUI.setupWithNavController(binding.bottomNav, navController)
								jobsViewModel.run {
												getRemoteJobs()
												getPartTimeJobs()
												getFullTimeJobs()
								}
								observers()
								favoritesViewModel.fetch()
				}

				private fun observers() {
								userViewModel.user.observe(this@MainActivity) { state ->
												when (state) {
																is UiState.None -> MainNavigationImpl.navigateToOnboardingActivity(this@MainActivity)
																is UiState.Success -> homeViewModel.getHomeJobs()
																else -> Unit
												}
								}
								favoritesViewModel.favoritesIds.observe(this@MainActivity) { ids ->
												favoritesViewModel.fetchFavoritesDetails(ids)
								}
				}

}