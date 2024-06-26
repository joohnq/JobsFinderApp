package com.joohnq.jobsfinderapp.view.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.joohnq.jobsfinderapp.databinding.FragmentLoginBinding
import com.joohnq.jobsfinderapp.databinding.FragmentRegisterBinding
import com.joohnq.jobsfinderapp.model.entity.User
import com.joohnq.jobsfinderapp.util.EmailValidator
import com.joohnq.jobsfinderapp.util.PasswordValidator
import com.joohnq.jobsfinderapp.util.Toast
import com.joohnq.jobsfinderapp.util.UserNameValidator
import com.joohnq.jobsfinderapp.util.handleUiState
import com.joohnq.jobsfinderapp.viewmodel.AuthViewModel
import com.joohnq.jobsfinderapp.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RegisterFragment: Fragment() {
				private val context: Context = requireContext()
				@Inject lateinit var userNameValidator: UserNameValidator
				@Inject lateinit var passwordValidator: PasswordValidator
				@Inject lateinit var emailValidator: EmailValidator
				private lateinit var binding: FragmentRegisterBinding
				private val authViewModel: AuthViewModel by viewModels()

				private fun FragmentRegisterBinding.observer() {
								authViewModel.register.observe(viewLifecycleOwner) { state ->
												state.handleUiState(onFailure = { error ->
																Toast(context).invoke(error.toString())
																startButton()
												}, onSuccess = { _ ->
																stopButton()
												}, onLoading = {
																stopButton()
												})
								}
				}

				private fun FragmentRegisterBinding.startButton() {
								btnRegister.startAnimation()
				}

				private fun FragmentRegisterBinding.stopButton() {
								btnRegister.revertAnimation()
				}

				private fun FragmentRegisterBinding.checkFieldsRegister() {
								textInputLayoutUserNameRegister.error = null
								textInputLayoutEmailRegister.error = null
								textInputLayoutPasswordRegister.error = null
								val userName = textInputEditTextUserNameRegister.text.toString()
								val email = textInputEditTextEmailRegister.text.toString()
								val password = textInputEditTextPasswordRegister.text.toString()

								try {
												val isUserNameValid = userNameValidator(userName)
												val isEmailValid = emailValidator(email)
												val isPasswordValid = passwordValidator(password)
												val user = User(
																name = userName,
																email = email,
												)
												authViewModel.createUserWithEmailAndPassword(user, password)

												if (!isEmailValid && !isPasswordValid && !isUserNameValid) return
								} catch (e: Throwable) {
												Toast(context).invoke(e.message.toString())
								}
				}

				private fun FragmentRegisterBinding.bindButtons() {
								tvBackToLogin.setOnClickListener { findNavController().popBackStack() }
								imgBtnPopUp.setOnClickListener { findNavController().popBackStack() }
								btnRegister.setOnClickListener { checkFieldsRegister() }
								imageBtnGoogle.setOnClickListener { startButton() }
				}

				override fun onCreateView(
								inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
				): View {
								binding = FragmentRegisterBinding.inflate(inflater, container, false)
								return binding.root
				}

				override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
								super.onViewCreated(view, savedInstanceState)
								binding.bindButtons()
								binding.observer()
				}
}