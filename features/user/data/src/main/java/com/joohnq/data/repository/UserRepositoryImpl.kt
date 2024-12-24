package com.joohnq.data.repository

import com.joohnq.data.network.UserService
import com.joohnq.domain.entities.User

class UserRepositoryImpl(
				private val service: UserService
): UserRepository {
				override suspend fun update(user: User): String = com.joohnq.data.repository.executeOperation {
								val id = user.id ?: throw Exception("User id is null")
								service.update(id, user)
				}.message

				override suspend fun update(id: String, updates: Map<String, Any>): String = com.joohnq.data.repository.executeOperation {
								service.update(id, updates)
				}.message

				override suspend fun get(): User = com.joohnq.data.repository.executeOperation {
								service.get()
				}
}