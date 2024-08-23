package com.joohnq.onboarding_ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.joohnq.onboarding_ui.R
import com.joohnq.onboarding_ui.databinding.FragmentOnboardingBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnboardingFragment : Fragment() {
    private var _binding: FragmentOnboardingBinding? = null
    private val binding get() = _binding!!

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.bindButtons()
    }

    private fun FragmentOnboardingBinding.bindButtons() {
        btnLetsGetStarted.setOnClickListener {
            findNavController().navigate(
//                OnboardingFragmentDirections.actionOnboardingFragmentToLoginFragment()
                R.id.action_onboardingFragment_to_loginFragment
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOnboardingBinding.inflate(inflater, container, false)
        return binding.root
    }
}