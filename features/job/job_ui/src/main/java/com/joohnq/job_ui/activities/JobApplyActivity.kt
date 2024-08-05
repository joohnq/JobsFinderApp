package com.joohnq.job_ui.activities

import android.net.Uri
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.github.nkuppan.country.utils.openCountrySelectionDialog
import com.joohnq.core.helper.SnackBarHelper
import com.joohnq.job_ui.databinding.ActivityJobApplyBinding
import com.joohnq.shared_resources.R
import com.joohnq.user.user_ui.mappers.fold
import com.joohnq.user.user_ui.viewmodel.UserViewModel
import com.joohnq.user_domain.entities.User
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class JobApplyActivity: AppCompatActivity() {
				private var _binding: ActivityJobApplyBinding? = null
				private val binding get() = _binding!!
				private lateinit var selectedCountry: String
				private lateinit var selectedFile: Uri
				private val jobId: String? by lazy {
								intent.extras?.getString("jobId")
				}
				private val userViewModel: UserViewModel by viewModels()
				private val getFile = registerForActivityResult(
								ActivityResultContracts.GetContent()
				) { uri ->
								uri?.run {
												selectedFile = this
												binding.btnUploadCv.text = this.toString()
								}
				}

				override fun onDestroy() {
								super.onDestroy()
								_binding = null
				}

				override fun onCreate(savedInstanceState: Bundle?) {
								super.onCreate(savedInstanceState)
								_binding = ActivityJobApplyBinding.inflate(layoutInflater)
								setContentView(binding.root)
								bindButtons()
								autoCompleteSomeFields()
				}

				private fun autoCompleteSomeFields() {
								userViewModel.user.observe(this) { state ->
												state.fold(
																onFailure = { error ->
																				SnackBarHelper(binding.root, error.toString())
																},
																onSuccess = { user: User? ->
																				user?.run {
																								with(binding) {
																												textInputEditTextEmail.setText(email)
																												textInputEditTextFirstName.setText(name)
																								}
																				}
																}
												)
								}
				}

				private fun bindButtons() {
								with(binding) {
												imgBtnBack.setOnClickListener {
																finish()
												}

												btnCountry.setOnClickListener {
																supportFragmentManager.openCountrySelectionDialog {
																				val countryName = it.name.toString()
																				selectedCountry = countryName
																				binding.btnCountry.text = countryName
																				binding.btnCountry.setTextColor(resources.getColor(R.color.BLACK, null))
																}
												}

												btnUploadCv.setOnClickListener {
																getFile.launch("application/pdf")
												}

												btnApply.setOnClickListener {
																lifecycleScope.launch {
																				verifyFields()
																}
												}
								}
				}

				private suspend fun verifyFields() {
								with(binding) {
												textInputLayoutFirstName.error = null
												textInputLayoutLastName.error = null
												textInputEditTextEmail.error = null

												val firstName = textInputEditTextFirstName.text.toString()
												val lastName = textInputEditTextLastName.text.toString()
												val email = textInputEditTextEmail.text.toString()

												if (firstName.isEmpty()) {
																textInputLayoutFirstName.error = "Campo obrigatório"
																return
												}
												if (lastName.isEmpty()) {
																textInputLayoutLastName.error = "Campo obrigatório"
																return
												}
												if (email.isEmpty()) {
																textInputEditTextEmail.error = "Campo obrigatório"
																return
												}
												if (selectedCountry.isEmpty()) {
																SnackBarHelper(binding.root, "Por favor selecione o pais")
																return
												}
												if (selectedFile.toString().isEmpty()) {
																SnackBarHelper(binding.root, "Por favor selecione o pais")
																return
												}


//												btnApply.startAnimation()
//            userViewModel.addUserFile(selectedFile) { state ->
//                state.handleUiState(
//                    onFailure = { error ->
//                        error?.let {
//                            Functions.showErrorWithToast(
//                                this@JobApplyActivity,
//                                tag,
//                                error,
//                            )
//                        }
//                        btnApply.revertAnimation()
//                    },
//                )
//            }

												jobId?.run {
//                userViewModel.handleJobIdApplication(this) { state ->
//                    Functions.handleUiState(
//                        state,
//                        onSuccess = {
//                            Toast.makeText(this@JobApplyActivity, it, Toast.LENGTH_SHORT).show()
//                            finish()
//                            btnApply.revertAnimation()
//                        },
//                        onFailure = { erro ->
//                            Functions.showErrorWithToast(
//                                this@JobApplyActivity,
//                                tag,
//                                erro
//                            )
//                            btnApply.revertAnimation()
//                        },
//                    )
//                }
												}
								}
				}
}