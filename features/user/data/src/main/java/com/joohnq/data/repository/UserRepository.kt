package com.joohnq.data.repository

import com.joohnq.domain.entity.User

interface UserRepository {
				suspend fun update(user: User): String
				suspend fun update(id: String, updates: Map<String, Any>): String
				suspend fun get(): User
}