package com.joohnq.onboarding_ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.joohnq.core.BaseFragment
import com.joohnq.onboarding_ui.databinding.FragmentCheckEmailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CheckEmailFragment: BaseFragment<FragmentCheckEmailBinding>() {
				private lateinit var email: String
				override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
								super.onViewCreated(view, savedInstanceState)
								email = arguments?.getString("email").toString()
								binding.email = email
				}

				override fun inflateBinding(
								inflater: LayoutInflater,
								container: ViewGroup?
				): FragmentCheckEmailBinding = FragmentCheckEmailBinding.inflate(inflater, container, false)
}