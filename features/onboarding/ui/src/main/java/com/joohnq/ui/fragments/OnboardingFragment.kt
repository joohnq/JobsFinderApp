package com.joohnq.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.joohnq.onboarding.ui.databinding.FragmentOnboardingBinding
import com.joohnq.ui.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnboardingFragment: BaseFragment<FragmentOnboardingBinding>() {
				override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
								super.onViewCreated(view, savedInstanceState)
								binding.bindButtons()
				}

				private fun FragmentOnboardingBinding.bindButtons() {
								btnLetsGetStarted.setOnClickListener {
												findNavController().navigate(
																OnboardingFragmentDirections.actionOnboardingFragmentToLoginFragment()
												)
								}
				}

				override fun inflateBinding(
								inflater: LayoutInflater,
								container: ViewGroup?
				): FragmentOnboardingBinding = FragmentOnboardingBinding.inflate(inflater, container, false)
}