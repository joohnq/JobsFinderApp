package com.joohnq.user.data.repository

import com.joohnq.core.data.repository.executeOperation
import com.joohnq.user.data.network.UserService
import com.joohnq.user.domain.entity.User

class UserRepositoryImpl(
				private val service: UserService
): UserRepository {
				override suspend fun update(user: User): String = executeOperation {
								val id = user.id ?: throw Exception("User id is null")
								service.update(id, user)
				}.message

				override suspend fun update(id: String, updates: Map<String, Any>): String = executeOperation {
								service.update(id, updates)
				}.message

				override suspend fun get(): User = executeOperation {
								service.get()
				}
}