package com.joohnq.core_test.mockk

import com.google.android.gms.tasks.Task
import io.mockk.every
import io.mockk.mockk

inline fun <reified T> mockTask(result: T?, exception: Exception? = null): Task<T> {
				val task: Task<T> = mockk(relaxed = true)
				every { task.isComplete } returns true
				every { task.exception } returns exception
				every { task.isCanceled } returns false
				every { task.isSuccessful } returns (exception == null)
//				val relaxedT: T = mockk(relaxed = true)
				every { task.result } returns result
				return task
}
