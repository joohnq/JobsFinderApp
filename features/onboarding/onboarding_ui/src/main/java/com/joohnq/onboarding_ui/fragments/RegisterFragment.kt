package com.joohnq.onboarding_ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.joohnq.core.BaseFragment
import com.joohnq.core.closeKeyboard
import com.joohnq.core.exceptions.EmailValidatorException
import com.joohnq.core.exceptions.PasswordValidatorException
import com.joohnq.core.exceptions.UserNameValidatorException
import com.joohnq.core.helper.CircularProgressButtonHelper
import com.joohnq.core.helper.SnackBarHelper
import com.joohnq.core.helper.applyError
import com.joohnq.core.helper.doOnTextChanged
import com.joohnq.core.validator.EmailValidator
import com.joohnq.core.validator.PasswordValidator
import com.joohnq.core.validator.UserNameValidator
import com.joohnq.onboarding_ui.databinding.FragmentRegisterBinding
import com.joohnq.onboarding_ui.navigation.OnboardingNavigationImpl
import com.joohnq.onboarding_ui.viewmodel.AuthViewModel
import com.joohnq.user.user_ui.mappers.fold
import com.joohnq.user.user_ui.viewmodel.UserViewModel
import com.joohnq.user_domain.entities.User
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment: BaseFragment<FragmentRegisterBinding>() {
				private val authViewModel: AuthViewModel by activityViewModels()
				private val userViewModel: UserViewModel by activityViewModels()
				private val onFailure = { error: String? ->
								error?.let { SnackBarHelper(requireView(), error.toString()) }
								CircularProgressButtonHelper.failureLoadingAnimation(binding.btnRegister)
				}

				private fun FragmentRegisterBinding.observer() {
								authViewModel.auth.observe(viewLifecycleOwner) { state ->
												state.fold(
																onFailure = onFailure,
																onLoading = { CircularProgressButtonHelper.startLoadingAnimation(btnRegister) },
																onSuccess = { _ ->
																				userViewModel.fetchUser()
																				CircularProgressButtonHelper.doneLoadingAnimation(binding.btnRegister)
																				OnboardingNavigationImpl.navigateToOccupationActivity(requireContext())
																}
												)
								}
				}

				private fun FragmentRegisterBinding.whenTextFieldsChanged() {
								textInputEditTextUserNameRegister.doOnTextChanged(textInputLayoutUserNameRegister)
								textInputEditTextEmailRegister.doOnTextChanged(textInputLayoutEmailRegister)
								textInputEditTextPasswordRegister.doOnTextChanged(textInputLayoutPasswordRegister)
				}

				private fun FragmentRegisterBinding.resetTextLayoutsError() {
								textInputLayoutUserNameRegister.error = null
								textInputLayoutEmailRegister.error = null
								textInputLayoutPasswordRegister.error = null
				}

				private fun FragmentRegisterBinding.getFields(): Triple<String, String, String> =
								Triple(
												textInputEditTextUserNameRegister.text.toString(),
												textInputEditTextEmailRegister.text.toString(),
												textInputEditTextPasswordRegister.text.toString()
								)

				private fun FragmentRegisterBinding.checkFieldsRegister() {
								resetTextLayoutsError()
								val (userName, email, password) = getFields()

								try {
												UserNameValidator(userName)
												EmailValidator(email)
												PasswordValidator(password)
												val user = User(
																name = userName,
																email = email,
												)

												requireActivity().closeKeyboard()

												authViewModel.createUserWithEmailAndPassword(
																user,
																password
												)
								} catch (e: UserNameValidatorException) {
												textInputLayoutUserNameRegister.applyError(e.message.toString())
								} catch (e: EmailValidatorException) {
												textInputLayoutEmailRegister.applyError(e.message.toString())
								} catch (e: PasswordValidatorException) {
												textInputLayoutPasswordRegister.applyError(e.message.toString())
								}
				}

				private fun FragmentRegisterBinding.bindButtons() {
								imgBtnPopUp.setOnClickListener { findNavController().popBackStack() }
								btnRegister.setOnClickListener { checkFieldsRegister() }
								btnEnterWithGoogle.setOnClickListener {
												CircularProgressButtonHelper.startLoadingAnimation(btnRegister)
												authViewModel.signInWithGoogleCredentials(requireContext())
								}
				}

				override fun inflateBinding(
								inflater: LayoutInflater,
								container: ViewGroup?
				): FragmentRegisterBinding = FragmentRegisterBinding.inflate(
								inflater,
								container,
								false
				)

				override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
								super.onViewCreated(
												view,
												savedInstanceState
								)
								binding.bindButtons()
								binding.observer()
								binding.whenTextFieldsChanged()
				}
}