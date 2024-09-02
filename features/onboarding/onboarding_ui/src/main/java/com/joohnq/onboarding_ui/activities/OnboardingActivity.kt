package com.joohnq.onboarding_ui.activities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.joohnq.core.BaseActivity
import com.joohnq.core.setOnApplyWindowInsetsListener
import com.joohnq.onboarding_ui.databinding.ActivityOnboardingBinding
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