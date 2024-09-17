package com.joohhq.loading.activities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.joohhq.loading.databinding.ActivityLoadingBinding
import com.joohhq.loading.navigation.LoadingNavigationImpl
import com.joohnq.core.BaseActivity
import com.joohnq.core.setOnApplyWindowInsetsListener
import com.joohnq.user.user_ui.mappers.fold
import com.joohnq.user.user_ui.viewmodel.UserViewModel
import com.joohnq.user_domain.entities.User
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoadingActivity: BaseActivity<ActivityLoadingBinding>() {
				private val userViewModel: UserViewModel by viewModels()

				override fun inflateBinding(
								inflater: LayoutInflater,
								container: ViewGroup?
				): ActivityLoadingBinding =
								ActivityLoadingBinding.inflate(layoutInflater)

				override fun onCreate(savedInstanceState: Bundle?) {
								super.onCreate(savedInstanceState)
								enableEdgeToEdge()
								binding.setOnApplyWindowInsetsListener()
								observers()
								userViewModel.fetchUser()
				}

				private fun observers() {
								userViewModel.user.observe(this) { state ->
												state.fold(
																onSuccess = { user: User? ->
																				if (user == null) {
																								LoadingNavigationImpl.navigateToOnboardingActivity(this)
																								return@fold
																				}
																				if (user.occupation.isEmpty()) {
																								println()
																								LoadingNavigationImpl.navigateToOccupationActivity(this)
																								return@fold
																				}
																				LoadingNavigationImpl.navigateToMainActivity(this)
																},
																onFailure = {
																				LoadingNavigationImpl.navigateToOnboardingActivity(this)
																},
												)
								}
				}
}