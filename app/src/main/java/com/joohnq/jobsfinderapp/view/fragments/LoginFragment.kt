package com.joohnq.jobsfinderapp.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.joohnq.jobsfinderapp.R
import com.joohnq.jobsfinderapp.databinding.FragmentLoginBinding
import com.joohnq.jobsfinderapp.utils.UiState
import com.joohnq.jobsfinderapp.viewmodel.auth.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private val authViewModel: AuthViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindButtons()
        observer()
    }

    private fun observer() {
        authViewModel.login.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UiState.Loading -> {
                    binding.btnLogin.startAnimation()
                }

                is UiState.Failure -> {
                    Toast.makeText(this.context, "Erro", Toast.LENGTH_SHORT).show()
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
        }
    }

    private fun checkFieldsLogin() {
        with(binding) {
            textInputLayoutEmailLogin.error = null
            textInputLayoutPasswordLogin.error = null

            val email = textInputEditTextEmailLogin.text.toString()
            val password = textInputEditTextPasswordLogin.text.toString()

            if (email.isEmpty()) {
                textInputLayoutEmailLogin.error = "Campo obrigatório"
                return
            }
            if (password.isEmpty()) {
                textInputLayoutPasswordLogin.error = "Campo obrigatório"
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