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
import com.joohnq.jobsfinderapp.util.Constants.EMAIL_GUEST
import com.joohnq.jobsfinderapp.util.Constants.PASSWORD_GUEST
import com.joohnq.jobsfinderapp.util.EmailValidator
import com.joohnq.jobsfinderapp.util.PasswordValidator
import com.joohnq.jobsfinderapp.util.Toast
import com.joohnq.jobsfinderapp.util.handleUiState
import com.joohnq.jobsfinderapp.viewmodel.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment: Fragment() {
				private val context: Context = requireContext()
				@Inject lateinit var passwordValidator: PasswordValidator
				@Inject lateinit var emailValidator: EmailValidator
				private var _binding: FragmentLoginBinding? = null
				private val binding get() = _binding!!
				private val authViewModel: AuthViewModel by viewModels()

				private fun FragmentLoginBinding.startButton() {
								btnLogin.startAnimation()
				}

				private fun FragmentLoginBinding.stopButton() {
								btnLogin.revertAnimation()
				}

				private fun FragmentLoginBinding.bindButtons() {
								tvGoToRegister.setOnClickListener {
												findNavController().navigate(
																LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
												)
								}
								btnLogin.setOnClickListener { checkFields() }
								imageBtnGoogle.setOnClickListener { startButton() }
								tvEnterWithGuest.setOnClickListener {
												authViewModel.signInWithEmailAndPassword(
																EMAIL_GUEST, PASSWORD_GUEST
												)
								}
				}

				private fun FragmentLoginBinding.observer() {
								authViewModel.login.observe(viewLifecycleOwner) { state ->
												state.run {
																handleUiState(onFailure = { error ->
																				error?.let {
																								Toast(context).invoke(error.toString())
																				}
																				stopButton()
																}, onLoading = {
																				startButton()
																}, onSuccess = {
																				stopButton()
																})
												}
								}
				}

				private fun FragmentLoginBinding.checkFields() {
								textInputLayoutEmailLogin.error = null
								textInputLayoutPasswordLogin.error = null
								val email = textInputEditTextEmailLogin.text.toString()
								val password = textInputEditTextPasswordLogin.text.toString()

								try {
												val isEmailValid = emailValidator(email)
												val isPasswordValid = passwordValidator(password)

												if (!isEmailValid && !isPasswordValid) return

												authViewModel.signInWithEmailAndPassword(email, password)
								} catch (e: Throwable) {
												Toast(context).invoke(e.message.toString())
								}
				}

				override fun onCreateView(
								inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
				): View {
								_binding = FragmentLoginBinding.inflate(inflater, container, false)
								return binding.root
				}

				override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
								super.onViewCreated(view, savedInstanceState)
								binding.bindButtons()
								binding.observer()
				}
}