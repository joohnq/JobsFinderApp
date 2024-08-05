package com.joohnq.onboarding_ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.joohnq.core.setOnApplyWindowInsetsListener
import com.joohnq.onboarding_ui.databinding.ActivityOnboardingBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnboardingActivity: AppCompatActivity() {
				private var _binding: ActivityOnboardingBinding? = null
				private val binding get() = _binding!!
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
								_binding = ActivityOnboardingBinding.inflate(layoutInflater)
								setContentView(binding.root)
								binding.setOnApplyWindowInsetsListener()
				}
}