package com.joohnq.jobsfinderapp.view

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.github.nkuppan.country.utils.openCountrySelectionDialog
import com.joohnq.jobsfinderapp.R
import com.joohnq.jobsfinderapp.databinding.ActivityJobApplyBinding
import com.joohnq.jobsfinderapp.util.Functions
import com.joohnq.jobsfinderapp.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.io.File


@AndroidEntryPoint
class JobApplyActivity : AppCompatActivity() {
    private val tag = "JobApplyActivity"
    private val binding: ActivityJobApplyBinding by lazy {
        ActivityJobApplyBinding.inflate(layoutInflater)
    }
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        bindButtons()
        autoCompleteSomeFields()
    }

    private fun autoCompleteSomeFields() {
        userViewModel.user.observe(this) {state ->
            Functions.handleUiState(
                state,
                onFailure = {error ->
                    Functions.showErrorWithToast(
                        this,
                        tag,
                        error
                    )
                },
                onSuccess = {user ->
                    user?.run{
                        with(binding){
                            textInputEditTextEmail.setText(email)
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
                    binding.btnCountry.setTextColor(resources.getColor(R.color.black, null))
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
                Toast.makeText(
                    this@JobApplyActivity,
                    "Por favor selecione o pais",
                    Toast.LENGTH_SHORT
                ).show()
                return
            }
            if (selectedFile.toString().isEmpty()) {
                Toast.makeText(
                    this@JobApplyActivity,
                    "Por favor selecione o pais",
                    Toast.LENGTH_SHORT
                ).show()
                return
            }

            userViewModel.addUserFile(selectedFile) { state ->
                Functions.handleUiState(
                    state,
                    onFailure = { error ->
                        error?.let {
                            Functions.showErrorWithToast(
                                this@JobApplyActivity,
                                tag,
                                error,
                            )
                        }
                    },
                )
            }

            jobId?.run {
                userViewModel.handleJobIdApplication(this) { state ->
                    Functions.handleUiState(
                        state,
                        onSuccess = {
                            Toast.makeText(this@JobApplyActivity, it, Toast.LENGTH_SHORT).show()
                            finish()
                        },
                        onFailure = { erro ->
                            Functions.showErrorWithToast(
                                this@JobApplyActivity,
                                tag,
                                erro
                            )
                        },
                    )
                }
            }
        }
    }
}