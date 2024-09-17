package com.joohnq.onboarding_ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.joohnq.core.BaseFragment
import com.joohnq.core.closeKeyboard
import com.joohnq.core.exceptions.EmailValidatorException
import com.joohnq.core.exceptions.PasswordValidatorException
import com.joohnq.core.helper.CircularProgressButtonHelper
import com.joohnq.core.helper.SnackBarHelper
import com.joohnq.core.helper.applyError
import com.joohnq.core.helper.doOnTextChanged
import com.joohnq.core.validator.EmailValidator
import com.joohnq.core.validator.PasswordValidator
import com.joohnq.onboarding_domain.constants.OnBoardingConstants
import com.joohnq.onboarding_ui.R
import com.joohnq.onboarding_ui.databinding.FragmentLoginBinding
import com.joohnq.onboarding_ui.navigation.OnboardingNavigationImpl
import com.joohnq.onboarding_ui.viewmodel.AuthViewModel
import com.joohnq.user.user_ui.mappers.fold
import com.joohnq.user.user_ui.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment: BaseFragment<FragmentLoginBinding>() {
				private val authViewModel: AuthViewModel by viewModels()
				private val userViewModel: UserViewModel by viewModels()
				private val onFailure = { error: String? ->
								error?.let { SnackBarHelper(requireView(), error.toString()) }
								CircularProgressButtonHelper.failureLoadingAnimation(binding.btnLogin)
				}

				private fun FragmentLoginBinding.bindButtons() {
								tvGoToRegister.setOnClickListener {
												findNavController().navigate(
																LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
												)
								}
								btnLogin.setOnClickListener { checkFields() }
								btnEnterWithGoogle.setOnClickListener {
												CircularProgressButtonHelper.startLoadingAnimation(btnLogin)
												authViewModel.signInWithGoogleCredentials(requireContext())
								}
								btnEnterWithGuest.setOnClickListener {
												authViewModel.signInWithEmailAndPassword(
																OnBoardingConstants.EMAIL_GUEST,
																OnBoardingConstants.PASSWORD_GUEST
												)
								}
								btnForgetPassword.setOnClickListener {
												findNavController().navigate(
															LoginFragmentDirections.actionLoginFragmentToForgotPasswordFragment()
												)
								}
				}

				private fun FragmentLoginBinding.observers() {
								authViewModel.auth.observe(viewLifecycleOwner) { state ->
												state.fold(
																onFailure = onFailure,
																onLoading = { CircularProgressButtonHelper.startLoadingAnimation(btnLogin)},
																onSuccess = {
																				userViewModel.fetchUser()
																				CircularProgressButtonHelper.doneLoadingAnimation(binding.btnLogin)
																				OnboardingNavigationImpl.navigateToMainActivity(requireContext())
																})
								}
								authViewModel.googleSignIn.observe(viewLifecycleOwner) { state ->
												state.fold(
																onFailure = onFailure,
																onSuccess = {
																				btnLogin.revertAnimation()
																},
												)
								}
				}

				private fun FragmentLoginBinding.whenTextFieldsChanged() {
								textInputEditTextEmailLogin.doOnTextChanged(textInputLayoutEmailLogin) {
												binding.btnLogin.revertAnimation()
								}
								textInputEditTextPasswordLogin.doOnTextChanged(textInputLayoutPasswordLogin) {
												binding.btnLogin.revertAnimation()
								}
				}

				private fun FragmentLoginBinding.getFields(): Pair<String, String> =
								Pair(
												textInputEditTextEmailLogin.text.toString(),
												textInputEditTextPasswordLogin.text.toString()
								)

				private fun FragmentLoginBinding.resetTextLayoutsError() {
								textInputLayoutEmailLogin.error = null
								textInputLayoutPasswordLogin.error = null
				}

				private fun FragmentLoginBinding.checkFields() {
								resetTextLayoutsError()
								val (email, password) = getFields()

								try {
												EmailValidator(email)
												PasswordValidator(password)

												requireActivity().closeKeyboard()

												authViewModel.signInWithEmailAndPassword(
																email,
																password
												)
								} catch (e: EmailValidatorException) {
												textInputLayoutEmailLogin.applyError(e.message.toString())
								} catch (e: PasswordValidatorException) {
												textInputLayoutPasswordLogin.applyError(e.message.toString())
								}
				}

				override fun inflateBinding(
								inflater: LayoutInflater,
								container: ViewGroup?
				): FragmentLoginBinding =
								FragmentLoginBinding.inflate(inflater, container, false)

				override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
								super.onViewCreated(
												view,
												savedInstanceState
								)
								binding.bindButtons()
								binding.observers()
								binding.whenTextFieldsChanged()
				}
}