package com.joohnq.onboarding_ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.joohnq.core.setOnApplyWindowInsetsListener
import com.joohnq.onboarding_ui.databinding.ActivityOnboardingBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnboardingActivity: AppCompatActivity() {
				private var _binding: ActivityOnboardingBinding? = null
				private val binding get() = _binding!!

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