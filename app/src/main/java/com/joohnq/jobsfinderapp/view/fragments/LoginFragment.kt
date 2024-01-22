package com.joohnq.jobsfinderapp.view.fragments

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.joohnq.jobsfinderapp.R
import com.joohnq.jobsfinderapp.databinding.FragmentLoginBinding
import com.joohnq.jobsfinderapp.model.repository.auth.sign_in.GoogleAuthUiClient
import com.joohnq.jobsfinderapp.util.UiState
import com.joohnq.jobsfinderapp.viewmodel.auth.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private val authViewModel: AuthViewModel by viewModels()

    @Inject
    lateinit var googleAuthUiClient: GoogleAuthUiClient
    private lateinit var launcher: ActivityResultLauncher<IntentSenderRequest>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindButtons()
        observer()
        setupActivityResultLauncher()
    }

    private fun setupActivityResultLauncher() {
        launcher = registerForActivityResult(
            ActivityResultContracts.StartIntentSenderForResult()
        ) { result ->
            handleGoogleSignInResult(result)
        }
    }

    private fun handleGoogleSignInResult(result: ActivityResult) {
        if (result.resultCode == Activity.RESULT_OK) {
            lifecycleScope.launch {
                val signInResult =
                    googleAuthUiClient.signInWithIntent(intent = result.data ?: return@launch)
                authViewModel.onLoginResult(signInResult)
            }
        }
    }

    private fun observer() {
        authViewModel.login.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UiState.Loading -> {
                    binding.btnLogin.startAnimation()
                }

                is UiState.Failure -> {
                    state.error?.let {
                        Toast.makeText(this.context, it, Toast.LENGTH_LONG).show()
                    }
                    binding.btnLogin.revertAnimation()
                }

                is UiState.Success -> {
                    val action =
                        LoginFragmentDirections.actionLoginFragmentToHomeFragment()
                    val navOptions = NavOptions.Builder()
                        .setPopUpTo(R.id.homeFragment, false)
                        .build()
                    findNavController().navigate(action, navOptions)
                }
            }
        }
    }

    private fun bindButtons() {
        with(binding) {
            tvGoToRegister.setOnClickListener {
                val action = LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
                findNavController().navigate(action)
            }

            btnLogin.setOnClickListener {
                checkFieldsLogin()
            }

            imageBtnGoogle.setOnClickListener {
                lifecycleScope.launch {
                    val signInIntentSender = googleAuthUiClient.getIntentSender()
                    launcher.launch(
                        IntentSenderRequest.Builder(
                            signInIntentSender ?: return@launch
                        ).build()
                    )
                }
            }
        }
    }

    private fun checkFieldsLogin() {
        with(binding) {
            textInputLayoutEmailLogin.error = null
            textInputLayoutPasswordLogin.error = null

            val email = textInputEditTextEmailLogin.text.toString()
            val password = textInputEditTextPasswordLogin.text.toString()

            if (email.isEmpty()) {
                Toast.makeText(context, "Email: Campo obrigatório", Toast.LENGTH_SHORT).show()
                return
            }
            if (password.isEmpty()) {
                Toast.makeText(context, "Senha: Campo obrigatório", Toast.LENGTH_SHORT).show()
                return
            }

            authViewModel.loginUser(email, password)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }
}