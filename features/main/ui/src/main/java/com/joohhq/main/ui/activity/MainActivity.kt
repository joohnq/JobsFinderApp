package com.joohhq.main.ui.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.joohhq.ui.navigation.MainNavigationImpl
import com.joohnq.user.domain.entity.User
import com.joohnq.favorite.ui.viewmodel.FavoritesViewModel
import com.joohnq.main.ui.databinding.ActivityMainBinding
import com.joohnq.main.viewmodel.HomeViewModel
import com.joohnq.ui.BaseActivity
import com.joohnq.ui.setOnApplyWindowInsetsListener
import com.joohnq.ui.viewmodel.JobsViewModel
import com.joohnq.user.ui.mapper.fold
import com.joohnq.user.ui.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

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
								lifecycleScope.launch {
												userViewModel.state.collect { state ->
																state.user.fold(
																				onNone = { MainNavigationImpl.navigateToOnboardingActivity(this@MainActivity) },
																				onSuccess = { user: User ->
																								if (user.occupation.isNullOrBlank()) {
																												MainNavigationImpl.navigateToOccupationActivity(this@MainActivity)
																												return@fold
																								}
																								homeViewModel.getHomeJobs(user.occupation)
																				}
																)
												}
								}
								favoritesViewModel.favoritesIds.observe(this@MainActivity) { ids ->
												favoritesViewModel.fetchFavoritesDetails(ids)
								}
				}

}