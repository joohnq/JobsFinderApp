package com.joohnq.job_domain.entities

data class Job(
				val id: String,
				val title: String,
				val description: String,
				val salary: Salary,
				val company: Company,
				val type: String,
				val location: String,
				val status: String,
)