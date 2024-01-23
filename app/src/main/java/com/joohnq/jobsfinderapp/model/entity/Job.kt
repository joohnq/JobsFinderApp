package com.joohnq.jobsfinderapp.model.entity

data class
Job(
    val title: String,
    val description: String,
    val salary: String,
    val company: Company,
    val type: JobType,
    val location: String
)