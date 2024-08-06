package com.joohnq.loading.activities

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.joohnq.core.setOnApplyWindowInsetsListener
import com.joohnq.loading.databinding.ActivityLoadingBinding
import com.joohnq.loading.navigation.LoadingNavigation
import com.joohnq.user.user_ui.mappers.fold
import com.joohnq.user.user_ui.viewmodel.UserViewModel
import com.joohnq.user_domain.entities.User
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoadingActivity: AppCompatActivity() {
				private var _binding: ActivityLoadingBinding? = null
				private val binding get() = _binding!!
				private val userViewModel: UserViewModel by viewModels()

				override fun onStart() {
								super.onStart()
								userViewModel.fetchUser()
				}

				override fun onDestroy() {
								super.onDestroy()
								_binding = null
				}

				override fun onCreate(savedInstanceState: Bundle?) {
								super.onCreate(savedInstanceState)
								enableEdgeToEdge()
								_binding = ActivityLoadingBinding.inflate(layoutInflater)
								setContentView(binding.root)
								binding.setOnApplyWindowInsetsListener()
								observers()
				}

				private fun observers() {
								userViewModel.user.observe(this) { state ->
												state.fold(
																onSuccess = { user: User? ->
																				if (user == null)
																								LoadingNavigation.navigateToOnboardingActivity(this)
																				else
																								LoadingNavigation.navigateToMainActivity(this)
																},
																onFailure = {
																				LoadingNavigation.navigateToOnboardingActivity(this)
																},
												)
								}
				}
}