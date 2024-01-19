package com.joohnq.jobsfinderapp.model.entity

data class Company(
    val logoUrl: String,
    val name: String,
    val location: String,
    val about: String,
    val reviews: List<Review>
)
