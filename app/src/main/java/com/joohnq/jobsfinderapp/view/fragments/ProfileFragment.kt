package com.joohnq.jobsfinderapp.view.fragments

import android.content.Intent
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.joohnq.jobsfinderapp.R
import com.joohnq.jobsfinderapp.databinding.FragmentProfileBinding
import com.joohnq.jobsfinderapp.model.entity.AuthType
import com.joohnq.jobsfinderapp.model.entity.User
import com.joohnq.jobsfinderapp.util.UiState
import com.joohnq.jobsfinderapp.view.PresentationActivity
import com.joohnq.jobsfinderapp.viewmodel.AuthViewModel
import com.joohnq.jobsfinderapp.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private val userViewModel: UserViewModel by activityViewModels()
    private val authViewModel: AuthViewModel by viewModels()
    private lateinit var user: User

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observer()
        bindButtons()
    }

    private fun bindButtons() {
        with(binding) {
            btnSaveNow.setOnClickListener {
                checkFieldsProfile()
            }
            btnLogOut.setOnClickListener {
                lifecycleScope.launch {
                    authViewModel.logout()
                    val intent = Intent(context, PresentationActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NO_HISTORY
                    startActivity(intent)
                }
            }
        }
    }

    private fun checkFieldsProfile() {
        with(binding) {

            textInputLayoutUserNameProfile.error = null
            textInputLayoutUserEmailProfile.error = null

            val name = textInputEditTextUserNameProfile.text.toString()
            val email = textInputEditTextUserEmailProfile.text.toString()

            if (name.isEmpty()) {
                Toast.makeText(context, "Email: Campo obrigatório", Toast.LENGTH_SHORT).show()
                return
            }
            if (email.isEmpty()) {
                Toast.makeText(context, "Senha: Campo obrigatório", Toast.LENGTH_SHORT).show()
                return
            }

//            userViewModel.updateUserToDatabase(user)
        }

    }

    private fun observer() {
        userViewModel.user.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UiState.Failure -> {
                    state.error?.let {
                        Toast.makeText(this.context, it, Toast.LENGTH_LONG).show()
                    }
                    userViewModel.getUserData()
                }

                is UiState.Success -> {
                    binding.loadingLayout.visibility = View.GONE
                    user = state.data!!
                    initUserData()
                }

                is UiState.Loading -> {
                    binding.loadingLayout.visibility = View.VISIBLE
                }
            }

        }
    }

    private fun initUserData() {
        with(binding) {
            user.run {
                tvUserNameProfile.text = name
                textInputEditTextUserNameProfile.setText(name)
                textInputEditTextUserEmailProfile.setText(email)
                Glide.with(imgViewUserProfile).load(imageUrl).into(imgViewUserProfile)
                if (user.authType == AuthType.GOOGLE) {
                    textInputEditTextUserEmailProfile.inputType = InputType.TYPE_NULL
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }
}