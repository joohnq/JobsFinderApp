package com.joohnq.user.ui.activities

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.joohnq.ui.BaseActivity
import com.joohnq.ui.closeKeyboard
import com.joohnq.ui.contracts.Contracts
import com.joohnq.domain.exceptions.EmailValidatorException
import com.joohnq.domain.exceptions.OccupationValidatorException
import com.joohnq.domain.exceptions.UserNameValidatorException
import com.joohnq.ui.helper.CircularProgressButtonHelper
import com.joohnq.ui.helper.SnackBarHelper
import com.joohnq.ui.helper.applyError
import com.joohnq.ui.helper.doOnTextChangedWithTextReturn
import com.joohnq.ui.permission.PermissionManager
import com.joohnq.ui.permission.PermissionManager.Companion.GALLERY_PERMISSION_CODE
import com.joohnq.ui.setOnApplyWindowInsetsListener
import com.joohnq.domain.validator.OccupationValidator
import com.joohnq.domain.validator.UserNameValidator
import com.joohnq.profile.databinding.ActivityProfileBinding
import com.joohnq.shared_resources.R
import com.joohnq.user.ui.mappers.fold
import com.joohnq.user.ui.viewmodel.UserViewModel
import com.joohnq.domain.entities.User
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProfileActivity: com.joohnq.ui.BaseActivity<ActivityProfileBinding>() {
				private val userViewModel: UserViewModel by viewModels()
				private var initialState: Boolean = false
				private lateinit var currentUser: User
				private val permissionManager by lazy { com.joohnq.ui.permission.PermissionManager(this@ProfileActivity) }
				private var userGalleryImage: Uri? = null
				private val activityResultLauncherGalleryImagePicker: ActivityResultLauncher<PickVisualMediaRequest> =
								com.joohnq.ui.contracts.Contracts.getGalleryImagePicker(this@ProfileActivity)
								{ uri ->
												Glide
																.with(binding.imageProfile)
																.load(uri)
																.into(binding.imageProfile)
												userGalleryImage = uri
												binding.btnSaveNow.isEnabled = true
								}

				private fun onFailure(error: String?) {
								com.joohnq.ui.helper.CircularProgressButtonHelper.failureAnimation(binding.btnSaveNow)
								com.joohnq.ui.helper.SnackBarHelper(binding.root, error.toString())
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
																com.joohnq.ui.helper.SnackBarHelper(
																				binding.root,
																				getString(R.string.change_profile_photo)
																)
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
								lifecycleScope.launch {
												userViewModel.state.collect { state ->
																state.user.fold(
																				onSuccess = { user: User ->
																								this@ProfileActivity.currentUser = user
																								if (initialState) {
																												CircularProgressButtonHelper.doneAnimation(btnSaveNow)
																								}
																								initialState = true
																				},
																				onFailure = ::onFailure,
																				onLoading = {
																								CircularProgressButtonHelper.startAnimation(btnSaveNow)
																				}
																)
												}
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

												userViewModel.update(
																currentUser.copy(
																				name = name,
																				occupation = occupation
																)
												)

												userGalleryImage?.run {
//																userViewModel.updateUserImageUrl(this)
												}
								} catch (e: com.joohnq.domain.exceptions.UserNameValidatorException) {
												textInputLayoutUserName.applyError(e.message.toString())
								} catch (e: com.joohnq.domain.exceptions.EmailValidatorException) {
												textInputLayoutUserEmail.applyError(e.message.toString())
								} catch (e: com.joohnq.domain.exceptions.OccupationValidatorException) {
												textInputLayoutUserOccupation.applyError(e.message.toString())
								}
				}
}