package com.joohnq.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.joohnq.domain.exceptions.EmailValidatorException
import com.joohnq.domain.exceptions.PasswordValidatorException
import com.joohnq.domain.validator.EmailValidator
import com.joohnq.domain.validator.PasswordValidator
import com.joohnq.onboarding.ui.databinding.FragmentLoginBinding
import com.joohnq.ui.BaseFragment
import com.joohnq.ui.closeKeyboard
import com.joohnq.ui.helper.CircularProgressButtonHelper
import com.joohnq.ui.helper.SnackBarHelper
import com.joohnq.ui.helper.applyError
import com.joohnq.ui.helper.doOnTextChanged
import com.joohnq.ui.navigation.OnboardingNavigationImpl
import com.joohnq.ui.viewmodel.AuthViewModel
import com.joohnq.user.ui.mappers.fold
import com.joohnq.user.ui.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment: BaseFragment<FragmentLoginBinding>() {
				private val authViewModel: AuthViewModel by viewModels()
				private val userViewModel: UserViewModel by viewModels()

				fun onFailure(error: String?) {
								error?.let { SnackBarHelper(requireView(), error.toString()) }
								CircularProgressButtonHelper.failureAnimation(binding.btnLogin)
				}

				private fun FragmentLoginBinding.bindButtons() {
								tvGoToRegister.setOnClickListener {
												findNavController().navigate(
																LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
												)
								}
								btnLogin.setOnClickListener { onContinue() }
//								btnEnterWithGoogle.setOnClickListener {
//												CircularProgressButtonHelper.startAnimation(btnLogin)
//												authViewModel.signInWithGoogleCredentials(requireContext())
//								}
//								btnEnterWithGuest.setOnClickListener {
//												authViewModel.signIn(
//																OnBoardingConstants.EMAIL_GUEST,
//																OnBoardingConstants.PASSWORD_GUEST
//												)
//								}
								btnForgetPassword.setOnClickListener {
												findNavController().navigate(
																LoginFragmentDirections.actionLoginFragmentToForgotPasswordFragment()
												)
								}
				}

				private fun FragmentLoginBinding.observers() {
								lifecycleScope.launch {
												authViewModel.state.collect { state ->
																state.login.fold(
																				onFailure = ::onFailure,
																				onLoading = { CircularProgressButtonHelper.startAnimation(btnLogin) },
																				onSuccess = {
																								userViewModel.get()
																								CircularProgressButtonHelper.doneAnimation(binding.btnLogin)
																								OnboardingNavigationImpl.navigateToMainActivity(requireContext())
																				}
																)
												}
								}
//								authViewModel.googleSignIn.observe(viewLifecycleOwner) { state ->
//												state.fold(
//																onFailure = ::onFailure,
//																onSuccess = {
//																				btnLogin.revertAnimation()
//																},
//												)
//								}
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

				private fun FragmentLoginBinding.onContinue() {
								resetTextLayoutsError()
								val (email, password) = getFields()

								try {
												EmailValidator(email)
												PasswordValidator(password)

												requireActivity().closeKeyboard()

												authViewModel.signIn(
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