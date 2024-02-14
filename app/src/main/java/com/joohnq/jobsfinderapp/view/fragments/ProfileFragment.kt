package com.joohnq.jobsfinderapp.view.fragments

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.text.InputType
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.joohnq.jobsfinderapp.R
import com.joohnq.jobsfinderapp.databinding.FragmentProfileBinding
import com.joohnq.jobsfinderapp.model.entity.AuthType
import com.joohnq.jobsfinderapp.model.entity.User
import com.joohnq.jobsfinderapp.util.Functions
import com.joohnq.jobsfinderapp.view.PresentationActivity
import com.joohnq.jobsfinderapp.viewmodel.AuthViewModel
import com.joohnq.jobsfinderapp.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProfileFragment : Fragment() {
    private val tag = "ProfileFragment"
    private lateinit var binding: FragmentProfileBinding
    private val userViewModel: UserViewModel by activityViewModels()
    private val authViewModel: AuthViewModel by viewModels()
    private lateinit var user: User
    private var profileImageSelected: Uri? = null
    private val openGallery = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.run {
            profileImageSelected = uri
            loadProfileImage(uri)
        }
    }
    private val openCamera = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { resultActivity ->
        if (resultActivity.resultCode == RESULT_OK) {
            val selectedImage = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                resultActivity.data?.extras?.getParcelable("data", Bitmap::class.java)
            } else {
                resultActivity.data?.extras?.getParcelable<Bitmap>("data")
            }

            selectedImage?.let {
                profileImageSelected = Functions.bitmapToUriConverter(requireContext(), it)
            }
            profileImageSelected?.run { loadProfileImage(this) }
        }
    }

    private fun loadProfileImage(uri: Uri) {
        val imgViewBinding = binding.imgViewUserProfile
        Glide.with(imgViewBinding).load(uri).into(imgViewBinding)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observer()
    }

    private fun bindButtons() {
        with(binding) {
            btnSaveNow.setOnClickListener {
                lifecycleScope.launch {
                    checkFieldsProfile()
                }
            }
            btnLogOut.setOnClickListener {
                lifecycleScope.launch {
                    authViewModel.logout()
                    Functions.customAlertDialog(
                        requireContext(),
                        resources.getString(R.string.logout),
                        resources.getString(R.string.are_you_sure_want_to_logout)
                    ) {
                        val intent = Intent(requireContext(), PresentationActivity::class.java)
                        intent.flags =
                            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)
                    }
                }
            }
            imgBtnOpenGallery.setOnClickListener {
                openGallery.launch("image/*")
            }

            imgBtnOpenCamera.setOnClickListener {
                val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                openCamera.launch(intent)
            }
        }
    }

    private suspend fun checkFieldsProfile() {
        with(binding) {
            textInputLayoutUserNameProfile.error = null

            val name = textInputEditTextUserNameProfile.text.toString()

            if (name.isEmpty()) {
                Toast.makeText(context, "Email: Campo obrigatório", Toast.LENGTH_SHORT).show()
                return
            }

            if (profileImageSelected != null) {
                userViewModel.updateUserImage(profileImageSelected!!) { state ->
                    Functions.handleUiState(
                        state,
                        onFailure = { error ->
                            error?.let {
                                Functions.showErrorWithToast(
                                    requireContext(),
                                    tag,
                                    error,
                                )
                            }
                        },
                        onSuccess = { data ->
                            user = user.copy(
                                imageUrl = data
                            )
                        },
                        onLoading = {
                            binding.loadingLayout.visibility = View.VISIBLE
                        }
                    )
                }
            }

            if (user.name != name || profileImageSelected != null) {
                if (user.name != name) {
                    user = user.copy(
                        name = name
                    )
                }
                userViewModel.updateUserToDatabase(user)
            } else {
                Toast.makeText(
                    requireContext(),
                    "Por favor, modifique os dados",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun observer() {
        userViewModel.user.observe(viewLifecycleOwner) { state ->
            Functions.handleUiState(
                state,
                onFailure = { error ->
                    Functions.showErrorWithToast(
                        requireContext(),
                        tag,
                        error,
                    )
                    userViewModel.getUserFromDatabase()
                },
                onSuccess = { data ->
                    binding.loadingLayout.visibility = View.GONE
                    if (data != null) {
                        user = data
                    }
                    initUserData()
                },
                onLoading = {
                    binding.loadingLayout.visibility = View.VISIBLE
                }
            )
        }
    }

    private fun initUserData() {
        with(binding) {
            user.run {
                tvUserNameProfile.text = name
                textInputEditTextUserNameProfile.setText(name)
                Glide.with(imgViewUserProfile).load(imageUrl).into(imgViewUserProfile)
                if (user.authType == AuthType.GOOGLE) {
                    textView12.visibility = View.GONE
                    textInputEditTextUserEmailProfile.visibility = View.GONE
                    textInputLayoutUserEmailProfile.visibility = View.GONE
                } else {
                    textInputEditTextUserEmailProfile.setText(email)
                }
                bindButtons()
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