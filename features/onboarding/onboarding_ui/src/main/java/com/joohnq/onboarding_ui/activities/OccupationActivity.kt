package com.joohnq.onboarding_ui.activities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.core.widget.doOnTextChanged
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.joohnq.core.BaseActivity
import com.joohnq.core.closeKeyboard
import com.joohnq.core.helper.CircularProgressButtonHelper
import com.joohnq.core.helper.SnackBarHelper
import com.joohnq.core.setOnApplyWindowInsetsListener
import com.joohnq.onboarding_ui.databinding.ActivityOccupationBinding
import com.joohnq.onboarding_ui.navigation.OnboardingNavigationImpl
import com.joohnq.user.user_ui.mappers.fold
import com.joohnq.user.user_ui.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OccupationActivity: BaseActivity<ActivityOccupationBinding>() {
				private val userViewModel: UserViewModel by viewModels()
				private val onFailure = { error: String? ->
								error?.let { SnackBarHelper(binding.root, error.toString()) }
								CircularProgressButtonHelper.failureLoadingAnimation(binding.btnOccupation)
				}

				override fun inflateBinding(
								inflater: LayoutInflater,
								container: ViewGroup?
				): ActivityOccupationBinding = ActivityOccupationBinding.inflate(layoutInflater)

				private fun ActivityOccupationBinding.observer() {
								userViewModel.user.observe(this@OccupationActivity) { state ->
												state.fold(
																onFailure = onFailure,
																onLoading = { CircularProgressButtonHelper.startLoadingAnimation(btnOccupation) },
																onSuccess = { user ->
																				if (user.occupation.isNotEmpty()) {
																								CircularProgressButtonHelper.doneLoadingAnimation(btnOccupation)
																								OnboardingNavigationImpl.navigateToMainActivity(this@OccupationActivity)
																				}
																}
												)
								}
				}

				private fun ActivityOccupationBinding.initButtons() {
								btnOccupation.setOnClickListener {
												checkFields()
								}
				}

				private fun ActivityOccupationBinding.whenTextFieldsChanged() {
								textInputEditTextOccupation.doOnTextChanged(textInputLayoutOccupation)
				}

				private fun TextInputEditText.doOnTextChanged(textInputLayout: TextInputLayout) {
								doOnTextChanged { _, _, _, _ ->
												textInputLayout.applyOnTextChange()
												binding.btnOccupation.revertAnimation()
								}
				}

				private fun TextInputLayout.applyOnTextChange() {
								this.apply {
												error = null
												helperText = null
												isErrorEnabled = false
								}
				}

				private fun ActivityOccupationBinding.resetTextLayoutsError() {
								textInputLayoutOccupation.error = null
				}

				private fun ActivityOccupationBinding.checkFields() {
								resetTextLayoutsError()
								val occupation = textInputLayoutOccupation.editText?.text.toString()

								try {
												if (occupation.isEmpty()) throw Exception("Occupation cannot be empty")

												closeKeyboard()

												userViewModel.updateUserOccupation(occupation)
								} catch (e: Exception) {
												textInputLayoutOccupation.applyError(e.message.toString())
								}
				}

				private fun TextInputLayout.applyError(error: String) {
								apply {
												this.error = error
												requestFocus()
												helperText = error
												isErrorEnabled = true
								}
				}

				override fun onCreate(savedInstanceState: Bundle?) {
								super.onCreate(savedInstanceState)
								enableEdgeToEdge()
								binding.setOnApplyWindowInsetsListener()
								binding.initButtons()
								binding.observer()
								binding.whenTextFieldsChanged()
				}
}