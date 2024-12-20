package com.joohnq.onboarding_ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.joohnq.core.BaseFragment
import com.joohnq.core.closeKeyboard
import com.joohnq.core.helper.CircularProgressButtonHelper
import com.joohnq.core.helper.SnackBarHelper
import com.joohnq.core.helper.applyError
import com.joohnq.core.helper.doOnTextChanged
import com.joohnq.core.validator.EmailValidator
import com.joohnq.onboarding_ui.databinding.FragmentForgotPasswordBinding
import com.joohnq.onboarding_ui.viewmodel.AuthViewModel
import com.joohnq.user.user_ui.mappers.fold
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject
import com.joohnq.shared_resources.R as ResourcesR

@AndroidEntryPoint
class ForgotPasswordFragment: BaseFragment<FragmentForgotPasswordBinding>() {
				@Inject lateinit var ioDispatcher: CoroutineDispatcher
				private val authViewModel by viewModels<AuthViewModel>()
				private lateinit var email: String
				private val onFailure = { error: String? ->
								error?.let { SnackBarHelper(requireView(), error.toString()) }
								CircularProgressButtonHelper.failureAnimation(binding.btnResetPassword)
				}

				override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
								super.onViewCreated(view, savedInstanceState)
								binding.bindButtons()
								observers()
								binding.whenTextFieldsChanged()
				}

				private fun observers() {
//								authViewModel.resetEmail.observe(viewLifecycleOwner) { state ->
//												state.fold(
//																onLoading = {
//																				CircularProgressButtonHelper.startAnimation(binding.btnResetPassword)
//																},
//																onFailure = onFailure,
//																onSuccess = {
//																				SnackBarHelper(
//																								requireView(),
//																								requireContext().getString(ResourcesR.string.email_sent)
//																				)
//																				CircularProgressButtonHelper.doneAnimation(binding.btnResetPassword)
//																				findNavController().navigate(
//																								ForgotPasswordFragmentDirections.actionForgotPasswordFragmentToCheckEmailFragment(email)
//																				)
//																},
//												)
//								}
				}

				private fun FragmentForgotPasswordBinding.resetTextLayoutsError() {
								textInputEditTextEmailForgotPassword.error = null
				}

				private fun FragmentForgotPasswordBinding.bindButtons() {
								binding.btnResetPassword.setOnClickListener { checkFields() }
								btnBackToLogin.setOnClickListener { findNavController().popBackStack() }
				}

				private fun FragmentForgotPasswordBinding.whenTextFieldsChanged() {
								textInputEditTextEmailForgotPassword.doOnTextChanged(textInputLayoutEmailForgotPassword) {
												binding.btnResetPassword.revertAnimation()
								}
				}

				private fun FragmentForgotPasswordBinding.checkFields() {
								resetTextLayoutsError()
								try {
												email = textInputEditTextEmailForgotPassword.text.toString()
												EmailValidator(email)
												requireActivity().closeKeyboard()
//												authViewModel.sendPasswordResetEmail(email)
								} catch (e: Exception) {
												textInputLayoutEmailForgotPassword.applyError(e.message.toString())
								}
				}

				override fun inflateBinding(
								inflater: LayoutInflater,
								container: ViewGroup?
				): FragmentForgotPasswordBinding =
								FragmentForgotPasswordBinding.inflate(inflater, container, false)
}