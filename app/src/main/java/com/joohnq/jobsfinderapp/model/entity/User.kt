package com.joohnq.jobsfinderapp.model.entity

data class User(
    var id: String? = "",
    var authType: AuthType? = AuthType.NOT_ESPECIFIED,
    val name: String = "",
    val email: String = "",
    val imageUrl: String? = "",
    val application: List<String> = mutableListOf(),
    val favourites: List<String> = mutableListOf()
)