package com.joohnq.onboarding_ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.joohnq.core.closeKeyboard
import com.joohnq.core.exceptions.EmailValidatorException
import com.joohnq.core.exceptions.PasswordValidatorException
import com.joohnq.core.helper.CircularProgressButtonHelper
import com.joohnq.core.helper.SnackBarHelper
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
class LoginFragment: Fragment() {
				private var _binding: FragmentLoginBinding? = null
				private val binding get() = _binding!!
				private val authViewModel: AuthViewModel by viewModels()
				private val userViewModel: UserViewModel by viewModels()
				private val onFailure = { error: String? ->
								error?.let { SnackBarHelper(requireView(), error.toString()) }
								CircularProgressButtonHelper.failureLoadingAnimation(binding.btnLogin)
				}

				override fun onDestroy() {
								super.onDestroy()
								_binding = null
								authViewModel.setAuthNone()
				}

				private fun FragmentLoginBinding.bindButtons() {
								tvGoToRegister.setOnClickListener {
												findNavController().navigate(
//																LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
																R.id.action_loginFragment_to_registerFragment
												)
								}
								btnLogin.setOnClickListener { checkFields() }
								btnEnterWithGoogle.setOnClickListener {
												btnLogin.startAnimation()
												authViewModel.signInWithGoogleCredentials(requireContext())
								}
								btnEnterWithGuest.setOnClickListener {
												authViewModel.signInWithEmailAndPassword(
																OnBoardingConstants.EMAIL_GUEST,
																OnBoardingConstants.PASSWORD_GUEST
												)
								}
				}

				private fun FragmentLoginBinding.observers() {
								authViewModel.auth.observe(viewLifecycleOwner) { state ->
												state.fold(
																onFailure = onFailure,
																onLoading = { btnLogin.startAnimation() },
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
								textInputEditTextEmailLogin.doOnTextChanged(textInputLayoutEmailLogin)
								textInputEditTextPasswordLogin.doOnTextChanged(textInputLayoutPasswordLogin)
				}

				private fun TextInputEditText.doOnTextChanged(textInputLayout: TextInputLayout) {
								doOnTextChanged { _, _, _, _ ->
												textInputLayout.applyOnTextChange()
												binding.btnLogin.revertAnimation()
								}
				}

				private fun TextInputLayout.applyOnTextChange() {
								this.apply {
												error = null
												helperText = null
												isErrorEnabled = false
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
												val isEmailValid = EmailValidator(email)
												val isPasswordValid = PasswordValidator(password)

												if (!isEmailValid && !isPasswordValid) return

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

				private fun TextInputLayout.applyError(error: String) {
								apply {
												this.error = error
												requestFocus()
												helperText = error
												isErrorEnabled = true
								}
				}

				override fun onCreateView(
								inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
				): View {
								_binding = FragmentLoginBinding.inflate(
												inflater,
												container,
												false
								)
								return binding.root
				}

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