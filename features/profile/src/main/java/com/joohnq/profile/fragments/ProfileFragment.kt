@file:Suppress("DEPRECATION")

package com.joohnq.profile.fragments

import android.app.Activity.RESULT_OK
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.joohnq.core.helper.BitmapHelper
import com.joohnq.profile.databinding.FragmentProfileBinding
import com.joohnq.profile.navigation.ProfileDetailNavigation
import com.joohnq.user.user_ui.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment: Fragment() {
				private var _binding: FragmentProfileBinding? = null
				private val binding get() = _binding!!
				private val userViewModel: UserViewModel by activityViewModels()
				private var profileImageSelected: Uri? = null
				private val openGallery = registerForActivityResult(
								ActivityResultContracts.GetContent()
				) { uri ->
								uri?.run {
												profileImageSelected = uri
												binding.loadProfileImage(uri)
								}
				}
				private val openCamera = registerForActivityResult(
								ActivityResultContracts.StartActivityForResult()
				) { resultActivity ->
								if (resultActivity.resultCode == RESULT_OK) {
												val selectedImage = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
																resultActivity.data?.extras?.getParcelable(
																				"data",
																				Bitmap::class.java
																)
												} else {
																resultActivity.data?.extras?.getParcelable("data")
												}

												selectedImage?.let {
																val bitmap = BitmapHelper.bitmapToUriConverter(requireContext(), it)
																profileImageSelected = bitmap
																binding.loadProfileImage(bitmap)
												}
								}
				}

				private fun FragmentProfileBinding.loadProfileImage(uri: Uri) =
								Glide.with(imgViewUserProfile).load(uri).into(imgViewUserProfile)

				override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
								super.onViewCreated(
												view,
												savedInstanceState
								)
								binding.viewmodel = userViewModel
								binding.bindButtons()
				}

				private fun FragmentProfileBinding.bindButtons() {
								btnGoToProfileDetail.setOnClickListener {
												ProfileDetailNavigation.navigateToProfileDetailActivity(requireContext())
								}
				}

				override fun onDestroy() {
								super.onDestroy()
								_binding = null
				}

				override fun onCreateView(
								inflater: LayoutInflater, container: ViewGroup?,
								savedInstanceState: Bundle?
				): View {
								_binding = FragmentProfileBinding.inflate(
												inflater,
												container,
												false
								)
								return binding.root
				}
}