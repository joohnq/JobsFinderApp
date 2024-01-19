package com.joohnq.jobsfinderapp.model.entity

data class User(
    var id: String? = "",
    val name: String = "",
    val email: String = "",
    val imageUrl: String? = "",
    val application: List<Job> = mutableListOf(),
    val favourites: List<Job> = mutableListOf()
)