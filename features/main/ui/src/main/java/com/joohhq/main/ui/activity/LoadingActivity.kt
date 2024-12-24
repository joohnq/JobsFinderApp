package com.joohhq.main.ui.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.joohhq.ui.navigation.LoadingNavigationImpl
import com.joohnq.user.domain.entity.User
import com.joohnq.main.ui.databinding.ActivityLoadingBinding
import com.joohnq.ui.BaseActivity
import com.joohnq.ui.setOnApplyWindowInsetsListener
import com.joohnq.user.ui.mapper.fold
import com.joohnq.user.ui.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

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
								userViewModel.get()
				}

				private fun observers() {
								lifecycleScope.launch {
												userViewModel.state.collect { state ->
																state.user.fold(
																				onSuccess = { user: User? ->
																								if (user == null) {
																												LoadingNavigationImpl.navigateToOnboardingActivity(this@LoadingActivity)
																												return@fold
																								}
																								if (user.occupation.isEmpty()) {
																												println()
																												LoadingNavigationImpl.navigateToOccupationActivity(this@LoadingActivity)
																												return@fold
																								}
																								LoadingNavigationImpl.navigateToMainActivity(this@LoadingActivity)
																				},
																				onFailure = {
																								LoadingNavigationImpl.navigateToOnboardingActivity(this@LoadingActivity)
																				},
																)
												}
								}
				}
}