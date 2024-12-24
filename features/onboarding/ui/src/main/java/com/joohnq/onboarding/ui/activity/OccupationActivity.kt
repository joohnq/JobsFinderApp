package com.joohnq.onboarding.ui.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.joohnq.onboarding.ui.databinding.ActivityOccupationBinding
import com.joohnq.ui.BaseActivity
import com.joohnq.ui.closeKeyboard
import com.joohnq.ui.helper.CircularProgressButtonHelper
import com.joohnq.ui.helper.SnackBarHelper
import com.joohnq.ui.helper.applyError
import com.joohnq.ui.helper.doOnTextChanged
import com.joohnq.onboarding.ui.navigation.OnboardingNavigationImpl
import com.joohnq.ui.setOnApplyWindowInsetsListener
import com.joohnq.user.ui.mapper.fold
import com.joohnq.user.ui.viewmodel.UserViewModel
import com.joohnq.user.domain.entity.User
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class OccupationActivity: BaseActivity<ActivityOccupationBinding>() {
				private val userViewModel: UserViewModel by viewModels()

				fun onFailure(error: String?) {
								error?.let { SnackBarHelper(binding.root, error.toString()) }
								CircularProgressButtonHelper.failureAnimation(binding.btnOccupation)
				}

				override fun inflateBinding(
								inflater: LayoutInflater,
								container: ViewGroup?
				): ActivityOccupationBinding = ActivityOccupationBinding.inflate(layoutInflater)

				private fun ActivityOccupationBinding.observer() {
								lifecycleScope.launch {
												userViewModel.state.collect { state ->
																state.user.fold(
																				onFailure = ::onFailure,
																				onLoading = { CircularProgressButtonHelper.startAnimation(btnOccupation) },
																				onSuccess = { user: User ->
																								if (user.occupation.isEmpty()) return@fold

																								CircularProgressButtonHelper.doneAnimation(btnOccupation)
																								OnboardingNavigationImpl.navigateToMainActivity(this@OccupationActivity)
																				}
																)
												}
								}
				}

				private fun ActivityOccupationBinding.initButtons() {
								btnOccupation.setOnClickListener { onContinue() }
				}

				private fun ActivityOccupationBinding.whenTextFieldsChanged() {
								textInputEditTextOccupation.doOnTextChanged(textInputLayoutOccupation) {
												binding.btnOccupation.revertAnimation()
								}
				}

				private fun ActivityOccupationBinding.resetTextLayoutsError() {
								textInputLayoutOccupation.error = null
				}

				private fun ActivityOccupationBinding.onContinue() {
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

				override fun onCreate(savedInstanceState: Bundle?) {
								super.onCreate(savedInstanceState)
								enableEdgeToEdge()
								userViewModel.get()
								binding.setOnApplyWindowInsetsListener()
								binding.initButtons()
								binding.observer()
								binding.whenTextFieldsChanged()
				}
}