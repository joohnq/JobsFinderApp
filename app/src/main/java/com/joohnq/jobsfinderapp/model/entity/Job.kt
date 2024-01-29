package com.joohnq.jobsfinderapp.model.entity

data class
Job(
    val id: String,
    val title: String,
    val description: String,
    val salary: String,
    val company: Company,
    val type: String,
    val location: String
)