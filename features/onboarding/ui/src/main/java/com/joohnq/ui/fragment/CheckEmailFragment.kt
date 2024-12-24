package com.joohnq.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.joohnq.onboarding.ui.databinding.FragmentCheckEmailBinding
import com.joohnq.ui.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CheckEmailFragment: BaseFragment<FragmentCheckEmailBinding>() {
				private lateinit var email: String
				override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
								super.onViewCreated(view, savedInstanceState)
								email = arguments?.getString("email").toString()
//								binding.email = email
				}

				override fun inflateBinding(
								inflater: LayoutInflater,
								container: ViewGroup?
				): FragmentCheckEmailBinding = FragmentCheckEmailBinding.inflate(inflater, container, false)
}