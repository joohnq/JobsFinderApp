package com.joohnq.search.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.RadioButton
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.joohnq.search.databinding.CustomSearchFilterBinding
import com.joohnq.search.viewmodel.FiltersViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CustomSearchFilterFragment(
				private val filtersViewModel: FiltersViewModel,
				private val searchJob: () -> Unit,
): BottomSheetDialogFragment() {
				private lateinit var binding: CustomSearchFilterBinding
				override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
								super.onViewCreated(view, savedInstanceState)
								dialog?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
								bindButtons()
								autoCompleteTextViews()
				}

				private fun bindButtons() {
								with(binding) {
												btnRemoveFilters.setOnClickListener {
																filtersViewModel.clearFilters()
																autoCompleteTextViews()
												}
												btnApplyFilters.setOnClickListener {
																saveTextViews()
																dialog?.dismiss()
																searchJob()
												}
								}
				}

				private fun saveTextViews() {
								try {
												with(binding) {
																val category = selectCategory.selectedItem.toString()
																val companyName = textInputEditTextCompanyName.text.toString()
																val location = textInputEditTextLocation.text.toString()
																val salaryEntry = textInputEditTextSalaryEntry.text.toString()
																val salaryEnd = textInputEditTextSalaryEnd.text.toString()
																val type = jobTypeRadioGroup.checkedRadioButtonId.toString()
																val selectedRadioButton =
																				binding.jobTypeRadioGroup.findViewById<RadioButton>(type.toInt())
																val selectedText = selectedRadioButton.text.toString()
																if (type == "-1") {
																				Toast.makeText(
																								context,
																								"Please select a job type",
																								Toast.LENGTH_SHORT
																				).show()
																				return
																}
																filtersViewModel.setAllFilters(
																				null,
																				companyName,
																				location,
																				salaryEntry,
																				salaryEnd,
																				type,
																				selectedText
																)
																if (category != "Select") {
																				val categoryText = category.replace(" ", "-").lowercase()
																				filtersViewModel.setFilters("category", categoryText)
																} else {
																				filtersViewModel.setFilters("category", "")
																}
												}

								} catch (e: Exception) {
												Log.e("CustomSearchFilterFragment", e.toString())
								}
				}

				private fun autoCompleteTextViews() {
								with(binding) {
												val categoryPos = when (filtersViewModel.getFilter("category")) {
																"popular" -> 1
																"recent-posted" -> 2
																else -> 0
												}
												selectCategory.setSelection(categoryPos)
												textInputEditTextCompanyName.setText(filtersViewModel.getFilter("company") ?: "")
												textInputEditTextLocation.setText(filtersViewModel.getFilter("location") ?: "")
												textInputEditTextSalaryEntry.setText(filtersViewModel.getFilter("salaryEntry") ?: "")
												textInputEditTextSalaryEnd.setText(filtersViewModel.getFilter("salaryEnd") ?: "")
												filtersViewModel.getFilter("type")?.run {
																jobTypeRadioGroup.check(this.toInt())
												}
								}
				}

				override fun onCreateView(
								inflater: LayoutInflater,
								container: ViewGroup?,
								savedInstanceState: Bundle?
				): View {
								binding = CustomSearchFilterBinding.inflate(inflater, null, false)
								return binding.root
				}
}