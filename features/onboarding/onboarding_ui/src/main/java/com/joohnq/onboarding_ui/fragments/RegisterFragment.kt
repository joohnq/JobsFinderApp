package com.joohnq.onboarding_ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
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
import com.joohnq.onboarding_ui.viewmodel.AuthViewModel
import com.joohnq.user.user_ui.mappers.fold
import com.joohnq.user.user_ui.viewmodel.UserViewModel
import com.joohnq.user_domain.entities.User
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RegisterFragment: BaseFragment<FragmentRegisterBinding>() {
				private val authViewModel: AuthViewModel by activityViewModels()
				private val userViewModel: UserViewModel by activityViewModels()

				fun onFailure(error: String?) {
								error?.let { SnackBarHelper(requireView(), error.toString()) }
								CircularProgressButtonHelper.failureAnimation(binding.btnRegister)
				}

				private fun FragmentRegisterBinding.observer() {
								lifecycleScope.launch {
												authViewModel.state.collect { state ->
																state.register.fold(
																				onFailure = ::onFailure,
																				onLoading = { CircularProgressButtonHelper.startAnimation(btnRegister) },
																				onSuccess = { message ->
																								SnackBarHelper(requireView(), message.toString())
																								CircularProgressButtonHelper.doneAnimation(binding.btnRegister)
																				}
																)
												}
								}
				}

				private fun FragmentRegisterBinding.whenTextFieldsChanged() {
								textInputEditTextUserNameRegister.doOnTextChanged(textInputLayoutUserNameRegister) {
												CircularProgressButtonHelper.revertAnimation(binding.btnRegister)
								}
								textInputEditTextEmailRegister.doOnTextChanged(textInputLayoutEmailRegister) {
												CircularProgressButtonHelper.revertAnimation(binding.btnRegister)
								}
								textInputEditTextPasswordRegister.doOnTextChanged(textInputLayoutPasswordRegister) {
												CircularProgressButtonHelper.revertAnimation(binding.btnRegister)
								}
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

				private fun FragmentRegisterBinding.onContinue() {
								resetTextLayoutsError()
								val (userName, email, password) = getFields()

								try {
												UserNameValidator(userName)
												EmailValidator(email)
												PasswordValidator(password)

												requireActivity().closeKeyboard()

												authViewModel.signUp(userName, email,password)
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
								btnRegister.setOnClickListener { onContinue() }
								btnEnterWithGoogle.setOnClickListener {
												CircularProgressButtonHelper.startAnimation(btnRegister)
//												authViewModel.signInWithGoogleCredentials(requireContext())
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