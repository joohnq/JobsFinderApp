package com.joohnq.jobsfinderapp.view.fragments

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.identity.Identity
import com.google.firebase.auth.FirebaseAuth
import com.joohnq.jobsfinderapp.R
import com.joohnq.jobsfinderapp.databinding.FragmentRegisterBinding
import com.joohnq.jobsfinderapp.model.entity.User
import com.joohnq.jobsfinderapp.model.repository.auth.sign_in.GoogleAuthUiClient
import com.joohnq.jobsfinderapp.utils.UiState
import com.joohnq.jobsfinderapp.viewmodel.auth.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class RegisterFragment: Fragment() {
    private lateinit var binding: FragmentRegisterBinding
    private val authViewModel: AuthViewModel by viewModels()
    @Inject
    lateinit var googleAuthUiClient: GoogleAuthUiClient
//    private val auth by lazy { FirebaseAuth.getInstance() }
//    private val googleAuthUiClient by lazy {
//        GoogleAuthUiClient(
//            requireContext(),
//            Identity.getSignInClient(requireContext()),
//            auth
//        )
//    }
    private lateinit var launcher: ActivityResultLauncher<IntentSenderRequest>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindButtons()
        observer()
        launcher = registerForActivityResult(
            ActivityResultContracts.StartIntentSenderForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                lifecycleScope.launch {
                    val signInResult =
                        googleAuthUiClient.signInWithIntent(intent = result.data ?: return@launch)
                    authViewModel.onSignInResult(signInResult)
                }
            }
        }
    }

    private fun observer() {
        authViewModel.register.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UiState.Loading -> {
                    binding.btnRegister.startAnimation()
                }

                is UiState.Failure -> {
                    Toast.makeText(this.context, "Erro", Toast.LENGTH_SHORT).show()
                    binding.btnRegister.revertAnimation()
                }

                is UiState.Success -> {
                    val action = RegisterFragmentDirections.actionRegisterFragmentToHomeFragment()
                    val navOptions = NavOptions.Builder()
                        .setPopUpTo(R.id.homeFragment, false)
                        .build()
                    findNavController().navigate(action, navOptions)
                }
            }
        }
    }

    private fun checkFieldsRegister() {
        with(binding) {
            textInputLayoutUserNameRegister.error = null
            textInputLayoutEmailRegister.error = null
            textInputLayoutPasswordRegister.error = null

            val userName = textInputEditTextUserNameRegister.text.toString()
            val email = textInputEditTextEmailRegister.text.toString()
            val password = textInputEditTextPasswordRegister.text.toString()

            if (userName.isEmpty()) {
                textInputLayoutUserNameRegister.error = "Campo obrigatório"
                return
            }
            if (email.isEmpty()) {
                textInputLayoutEmailRegister.error = "Campo obrigatório"
                return
            }
            if (password.isEmpty()) {
                textInputLayoutPasswordRegister.error = "Campo obrigatório"
                return
            }

            val user = User(
                name = userName,
                email = email,
            )
            authViewModel.registerUser(user, password)
        }

    }

    private fun bindButtons() {
        with(binding) {
            tvBackToLogin.setOnClickListener {
                findNavController().popBackStack()
            }

            imgBtnPopUp.setOnClickListener {
                findNavController().popBackStack()
            }

            btnRegister.setOnClickListener {
                checkFieldsRegister()
            }

            imageBtnGoogle.setOnClickListener {
                lifecycleScope.launch {
                    val signInIntentSender = googleAuthUiClient.signIn()
                    launcher.launch(
                        IntentSenderRequest.Builder(
                            signInIntentSender ?: return@launch
                        ).build()
                    )
                }


            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }
}