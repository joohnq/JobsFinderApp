package com.joohnq.data.repository

import com.google.gson.Gson
import com.joohnq.domain.entity.MessageResponse
import retrofit2.Response

suspend fun <T> executeOperation(block: suspend () -> Response<T>): T {
				val res = block()
				if (!res.isSuccessful) {
								val error =
												Gson().fromJson<MessageResponse>(res.errorBody()?.string(), MessageResponse::class.java)
								throw Exception(error.message)
				}
				val body =
								res.body() ?: throw Exception("Something went wrong with the content, try again later")
				return body
}