package com.joohnq.onboarding_ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.joohnq.core.closeKeyboard
import com.joohnq.core.helper.CircularProgressButtonHelper
import com.joohnq.core.helper.SnackBarHelper
import com.joohnq.core.validator.EmailValidator
import com.joohnq.core.validator.PasswordValidator
import com.joohnq.core.validator.UserNameValidator
import com.joohnq.onboarding_ui.databinding.FragmentRegisterBinding
import com.joohnq.onboarding_ui.viewmodel.AuthViewModel
import com.joohnq.user.user_ui.mappers.fold
import com.joohnq.user.user_ui.viewmodel.UserViewModel
import com.joohnq.user_domain.entities.User
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment: Fragment() {
				private var _binding: FragmentRegisterBinding? = null
				private val binding get() = _binding!!
				private val authViewModel: AuthViewModel by activityViewModels()
				private val userViewModel: UserViewModel by activityViewModels()
				private val onFailure = { error: String? ->
								error?.let { SnackBarHelper(requireView(), error.toString()) }
								CircularProgressButtonHelper.doneLoadingAnimation(binding.btnRegister, false)
				}

				override fun onDestroy() {
								super.onDestroy()
								_binding = null
								authViewModel.setAuthNone()
				}

				private fun FragmentRegisterBinding.observer() {
								authViewModel.auth.observe(viewLifecycleOwner) { state ->
												state.fold(
																onFailure = onFailure,
																onLoading = { btnRegister.startAnimation() },
																onSuccess = { _ ->
																				userViewModel.fetchUser()
																				CircularProgressButtonHelper.doneLoadingAnimation(binding.btnRegister, true)
																}
												)
								}
				}

				private fun FragmentRegisterBinding.whenTextFieldsChanged() {
								textInputEditTextUserNameRegister.doOnTextChanged(textInputLayoutUserNameRegister)
								textInputEditTextEmailRegister.doOnTextChanged(textInputLayoutEmailRegister)
								textInputEditTextPasswordRegister.doOnTextChanged(textInputLayoutPasswordRegister)
				}

				private fun TextInputEditText.doOnTextChanged(textInputLayout: TextInputLayout) {
								doOnTextChanged { _, _, _, _ ->
												textInputLayout.applyOnTextChange()
								}
				}

				private fun TextInputLayout.applyOnTextChange() {
								this.apply {
												error = null
												helperText = null
												isErrorEnabled = false
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

				private fun FragmentRegisterBinding.checkFieldsRegister() {
								resetTextLayoutsError()
								val (userName, email, password) = getFields()

								try {
												val isUserNameValid = UserNameValidator(userName)
												val isEmailValid = EmailValidator(email)
												val isPasswordValid = PasswordValidator(password)
												val user = User(
																name = userName,
																email = email,
												)

												if (!isEmailValid && !isPasswordValid && !isUserNameValid) throw Exception("Error on fields validation")

												requireActivity().closeKeyboard()

												authViewModel.createUserWithEmailAndPassword(
																user,
																password
												)
								} catch (e: Throwable) {
												SnackBarHelper(requireView(), e.message.toString())
								}
				}

				private fun FragmentRegisterBinding.bindButtons() {
								tvBackToLogin.setOnClickListener { findNavController().popBackStack() }
								imgBtnPopUp.setOnClickListener { findNavController().popBackStack() }
								btnRegister.setOnClickListener { checkFieldsRegister() }
								imageBtnGoogle.setOnClickListener {
												btnRegister.startAnimation()
												authViewModel.signInWithGoogleCredentials(requireContext())
								}
				}

				override fun onCreateView(
								inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
				): View {
								_binding = FragmentRegisterBinding.inflate(
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
								binding.observer()
								binding.whenTextFieldsChanged()
				}
}