package com.joohnq.user_domain.entities

data class WorkExperience(
				val companyName: String,
				val position: String,
				val initialDate: String,
				val endDate: String,
				val currentJob: Boolean
)