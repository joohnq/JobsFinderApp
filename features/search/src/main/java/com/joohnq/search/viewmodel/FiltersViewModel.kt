package com.joohnq.search.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FiltersViewModel @Inject constructor() : ViewModel() {
    var category: String? = null
    var company: String? = null
    var location: String? = null
    var salaryEntry: String? = null
    var salaryEnd: String? = null
    var type: String? = null
    var typeText: String? = null

    fun clearFilters() {
        category = null
        company = null
        location = null
        salaryEntry = null
        salaryEnd = null
        type = null
        typeText = null
    }

    fun setFilters(filter: String, value: String) {
        when (filter) {
            "category" -> category = value
            "company" -> company = value
            "location" -> location = value
            "salaryEntry" -> salaryEntry = value
            "salaryEnd" -> salaryEnd = value
            "type" -> type = value
            "typeText" -> typeText = value
        }
    }

    fun setAllFilters(
        category: String?,
        company: String?,
        location: String?,
        salaryEntry: String?,
        salaryEnd: String?,
        type: String?,
        typeText: String?
    ) {
        this.category = category
        this.company = company
        this.location = location
        this.salaryEntry = salaryEntry
        this.salaryEnd = salaryEnd
        this.type = type
        this.typeText = typeText

        Log.i(
            "FiltersViewModel",
            "Filters: $category,$company, $location, $salaryEntry, $salaryEnd, $type, $typeText"
        )
    }

    fun getFilter(filter: String): String? {
        Log.i(
            "FiltersViewModel",
            "GET: $company, $location, $salaryEntry, $salaryEnd, $type, $typeText"
        )
        return when (filter) {
            "category" -> category
            "company" -> company
            "location" -> location
            "salaryEntry" -> salaryEntry
            "salaryEnd" -> salaryEnd
            "type" -> type
            "typeText" -> typeText
            else -> null
        }
    }
}