package com.joohnq.profile.activities

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.joohnq.core.BaseActivity
import com.joohnq.core.permission.PermissionManager
import com.joohnq.core.permission.PermissionManager.Companion.GALLERY_PERMISSION_CODE
import com.joohnq.core.closeKeyboard
import com.joohnq.core.contracts.Contracts
import com.joohnq.core.exceptions.EmailValidatorException
import com.joohnq.core.exceptions.OccupationValidatorException
import com.joohnq.core.exceptions.UserNameValidatorException
import com.joohnq.core.helper.CircularProgressButtonHelper
import com.joohnq.core.helper.SnackBarHelper
import com.joohnq.core.helper.applyError
import com.joohnq.core.helper.doOnTextChangedWithTextReturn
import com.joohnq.core.setOnApplyWindowInsetsListener
import com.joohnq.core.validator.OccupationValidator
import com.joohnq.core.validator.UserNameValidator
import com.joohnq.shared_resources.R
import com.joohnq.profile.databinding.ActivityProfileBinding
import com.joohnq.user.user_ui.mappers.fold
import com.joohnq.user.user_ui.viewmodel.UserViewModel
import com.joohnq.user_domain.entities.User
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileActivity: BaseActivity<ActivityProfileBinding>() {
				private val userViewModel: UserViewModel by viewModels()
				private var initialState: Boolean = false
				private lateinit var currentUser: User
				private val onFailure = { error: String? ->
								CircularProgressButtonHelper.failureLoadingAnimation(binding.btnSaveNow)
								SnackBarHelper(binding.root, error.toString())
				}
				private val permissionManager by lazy { PermissionManager(this@ProfileActivity) }
				private var userGalleryImage: Uri? = null
				private val activityResultLauncherGalleryImagePicker: ActivityResultLauncher<PickVisualMediaRequest> =
								Contracts.getGalleryImagePicker(this@ProfileActivity)
								{ uri ->
												Glide
																.with(binding.imageProfile)
																.load(uri)
																.into(binding.imageProfile)
												userGalleryImage = uri
												binding.btnSaveNow.isEnabled = true
								}

				override fun inflateBinding(
								inflater: LayoutInflater,
								container: ViewGroup?
				): ActivityProfileBinding =
								ActivityProfileBinding.inflate(layoutInflater)

				override fun onCreate(savedInstanceState: Bundle?) {
								super.onCreate(savedInstanceState)
								enableEdgeToEdge()
								binding.setOnApplyWindowInsetsListener()
								binding.viewmodel = userViewModel
								binding.observers()
								binding.whenTextFieldsChanged()
								binding.bindButtons()
								binding.initTopAppBar()
				}

				private fun ActivityProfileBinding.initTopAppBar() {
								topAppBar.setNavigationOnClickListener { finish() }
				}

				override fun onRequestPermissionsResult(
								requestCode: Int,
								permissions: Array<out String>,
								grantResults: IntArray
				) {
								super.onRequestPermissionsResult(requestCode, permissions, grantResults)
								permissionManager.onRequestPermissionsResult(
												requestCode,
												GALLERY_PERMISSION_CODE,
												grantResults,
												onAllowPermission = {
																activityResultLauncherGalleryImagePicker.launch(
																				PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
																)
												},
												onDenyPermission = {
																SnackBarHelper(binding.root, getString(R.string.change_profile_photo))
												}
								)
				}

				private fun ActivityProfileBinding.bindButtons() {
								btnSaveNow.setOnClickListener { checkFields() }
								imageProfileBox.setOnClickListener {
												val isAllowed = permissionManager.verifyGalleryPermission()

												if (!isAllowed) {
																permissionManager.requestGalleryPermission(this@ProfileActivity)
																return@setOnClickListener
												}
								}
				}

				private fun ActivityProfileBinding.observers() {
								userViewModel.user.observe(this@ProfileActivity) { state ->
												state.fold(
																onSuccess = { user: User ->
																				this@ProfileActivity.currentUser = user
																				if (initialState) {
																								CircularProgressButtonHelper.doneLoadingAnimation(btnSaveNow)
																				}
																				initialState = true
																},
																onFailure = onFailure,
																onLoading = {
																				CircularProgressButtonHelper.startLoadingAnimation(btnSaveNow)
																}
												)
								}
				}

				private fun ActivityProfileBinding.whenTextFieldsChanged() {
								textInputEditTextUserName.doOnTextChangedWithTextReturn(textInputLayoutUserName) { name ->
												btnSaveNow.isEnabled = currentUser.name != name
								}
								textInputEditTextUserEmail.doOnTextChangedWithTextReturn(textInputLayoutUserEmail)
								{ email ->
												btnSaveNow.isEnabled = currentUser.email != email
								}
								textInputEditTextUserOccupation.doOnTextChangedWithTextReturn(textInputLayoutUserOccupation)
								{ occupation ->
												btnSaveNow.isEnabled = currentUser.occupation != occupation
								}
				}

				private fun ActivityProfileBinding.getFields(): Pair<String, String> =
								Pair(
												textInputEditTextUserName.text.toString(),
												textInputEditTextUserOccupation.text.toString()
								)

				private fun ActivityProfileBinding.resetTextLayoutsError() {
								textInputEditTextUserName.error = null
								textInputEditTextUserEmail.error = null
								textInputEditTextUserOccupation.error = null
				}

				private fun ActivityProfileBinding.checkFields() {
								resetTextLayoutsError()
								val (name, occupation) = getFields()

								try {
												UserNameValidator(name)
												OccupationValidator(occupation)

												closeKeyboard()

												userViewModel.updateUser(
																currentUser.copy(
																				name = name,
																				occupation = occupation
																)
												)

												userGalleryImage?.run {
																userViewModel.updateUserImageUrl(this)
												}
								} catch (e: UserNameValidatorException) {
												textInputLayoutUserName.applyError(e.message.toString())
								} catch (e: EmailValidatorException) {
												textInputLayoutUserEmail.applyError(e.message.toString())
								} catch (e: OccupationValidatorException) {
												textInputLayoutUserOccupation.applyError(e.message.toString())
								}
				}
}