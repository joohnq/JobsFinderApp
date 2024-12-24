package com.joohnq.ui.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.joohnq.onboarding.ui.databinding.ActivityOnboardingBinding
import com.joohnq.ui.BaseActivity
import com.joohnq.ui.setOnApplyWindowInsetsListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnboardingActivity: BaseActivity<ActivityOnboardingBinding>() {
				override fun inflateBinding(
								inflater: LayoutInflater,
								container: ViewGroup?
				): ActivityOnboardingBinding = ActivityOnboardingBinding.inflate(layoutInflater)

				override fun onCreate(savedInstanceState: Bundle?) {
								super.onCreate(savedInstanceState)
								binding.setOnApplyWindowInsetsListener()
				}
}