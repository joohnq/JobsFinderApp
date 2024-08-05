package com.joohnq.job_domain.entities

data class Company(
    val logoUrl: String,
    val name: String,
    val about: String,
    val reviews: String = ""
)
