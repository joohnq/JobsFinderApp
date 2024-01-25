package com.joohnq.jobsfinderapp.view.fragments

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.IntentSender
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
import androidx.navigation.fragment.findNavController
import com.joohnq.jobsfinderapp.databinding.FragmentRegisterBinding
import com.joohnq.jobsfinderapp.model.entity.User
import com.joohnq.jobsfinderapp.sign_in.GoogleAuthUiClient
import com.joohnq.jobsfinderapp.util.Functions
import com.joohnq.jobsfinderapp.view.NavigationActivity
import com.joohnq.jobsfinderapp.viewmodel.AuthViewModel
import com.joohnq.jobsfinderapp.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class RegisterFragment : Fragment() {
    private val tag = "RegisterFragment"
    private lateinit var binding: FragmentRegisterBinding
    private val authViewModel: AuthViewModel by viewModels()
    private val userViewModel: UserViewModel by viewModels()

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
            handleGoogleSignInResult(result) {
                binding.btnRegister.revertAnimation()
            }
        }
    }

    private fun handleGoogleSignInResult(result: ActivityResult, onCancel: () -> Unit) {
        if (result.resultCode == RESULT_OK) {
            lifecycleScope.launch {
                val signInResult =
                    googleAuthUiClient.signInWithIntent(intent = result.data ?: return@launch)
                authViewModel.onSignInResult(signInResult) { user ->
                    user?.run {
                        userViewModel.registerUserToDatabaseWithGoogle(this)
                    }
                }
            }
        } else {
            onCancel()
        }
    }

    private fun observer() {
        authViewModel.register.observe(viewLifecycleOwner) { state ->
            Functions.handleUiState(
                state,
                onFailure = { error ->
                    Functions.showErrorWithToast(
                        requireContext(),
                        tag,
                        error
                    )
                    binding.btnRegister.revertAnimation()
                },
                onSuccess = { _ ->
                    val intent = Intent(requireContext(), NavigationActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NO_HISTORY
                    startActivity(intent)
                },
                onLoading = {
                    binding.btnRegister.startAnimation()
                }
            )
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
                Toast.makeText(context, "Nome: Campo obrigatório", Toast.LENGTH_SHORT).show()
                return
            }
            if (email.isEmpty()) {
                Toast.makeText(context, "Email: Campo obrigatório", Toast.LENGTH_SHORT).show()
                return
            }
            if (password.isEmpty()) {
                Toast.makeText(context, "Senha: Campo obrigatório", Toast.LENGTH_SHORT).show()
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
                btnRegister.startAnimation()
                lifecycleScope.launch {
                    val signInIntentSender: IntentSender? = googleAuthUiClient.getIntentSender()
                    val intentSenderRequest: IntentSenderRequest = IntentSenderRequest.Builder(
                        signInIntentSender ?: return@launch
                    ).build()
                    launcher.launch(intentSenderRequest)
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