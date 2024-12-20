package com.joohnq.core.repository

import com.google.gson.Gson
import com.joohnq.core.ErrorResponse
import retrofit2.Response

suspend fun <T> executeOperation(block: suspend () -> Response<T>): T {
				val res = block()
				if (!res.isSuccessful) {
								val error =
												Gson().fromJson<ErrorResponse>(res.errorBody()?.string(), ErrorResponse::class.java)
								throw Exception(error.message)
				}
				val body =
								res.body() ?: throw Exception("Something went wrong with the content, try again later")
				return body
}