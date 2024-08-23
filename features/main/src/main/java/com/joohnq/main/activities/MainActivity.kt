package com.joohnq.main.activities

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.joohnq.core.setOnApplyWindowInsetsListener
import com.joohnq.home.viewmodel.HomeViewModel
import com.joohnq.job_ui.viewmodel.JobsViewModel
import com.joohnq.main.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity: AppCompatActivity() {
				private var _binding: ActivityMainBinding? = null
				private val binding get() = _binding!!
				private val homeViewModel: HomeViewModel by viewModels()
				private val jobsViewModel: JobsViewModel by viewModels()

				//				private val favoritesViewModel: FavoritesViewModel by viewModels()
				private val navController by lazy {
								val navHostFragment =
												supportFragmentManager.findFragmentById(binding.navHostFragment.id) as NavHostFragment
								navHostFragment.navController
				}

				override fun onDestroy() {
								super.onDestroy()
								_binding = null
				}

				override fun onCreate(savedInstanceState: Bundle?) {
								super.onCreate(savedInstanceState)
								enableEdgeToEdge()
								_binding = ActivityMainBinding.inflate(layoutInflater)
								setContentView(binding.root)
								binding.setOnApplyWindowInsetsListener(bottoms = 0)
								NavigationUI.setupWithNavController(binding.bottomNav, navController)
								homeViewModel.getHomeJobs()
								jobsViewModel.run {
												getRemoteJobs()
												getPartTimeJobs()
												getFullTimeJobs()
								}
//								favoritesViewModel.fetch()
				}
}