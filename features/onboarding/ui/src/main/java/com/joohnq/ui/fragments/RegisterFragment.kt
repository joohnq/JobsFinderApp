package com.joohnq.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.joohnq.domain.exceptions.EmailValidatorException
import com.joohnq.domain.exceptions.PasswordValidatorException
import com.joohnq.domain.exceptions.UserNameValidatorException
import com.joohnq.domain.validator.EmailValidator
import com.joohnq.domain.validator.PasswordValidator
import com.joohnq.domain.validator.UserNameValidator
import com.joohnq.onboarding.ui.databinding.FragmentRegisterBinding
import com.joohnq.ui.BaseFragment
import com.joohnq.ui.closeKeyboard
import com.joohnq.ui.helper.CircularProgressButtonHelper
import com.joohnq.ui.helper.SnackBarHelper
import com.joohnq.ui.helper.applyError
import com.joohnq.ui.helper.doOnTextChanged
import com.joohnq.ui.viewmodel.AuthViewModel
import com.joohnq.user.ui.mappers.fold
import com.joohnq.user.ui.viewmodel.UserViewModel
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
																				onSuccess = { message: String ->
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

												authViewModel.signUp(userName, email, password)
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