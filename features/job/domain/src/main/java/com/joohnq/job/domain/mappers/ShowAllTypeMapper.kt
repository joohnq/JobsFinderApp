package com.joohnq.job.domain.mappers

import com.joohnq.job.domain.entity.ShowAllType

object ShowAllTypeMapper {
				fun toShowAllType(str: String): ShowAllType = when (str) {
								"REMOTE_JOBS" -> ShowAllType.REMOTE
								"PART_TIME" -> ShowAllType.PART_TIME
								"FULL_TIME" -> ShowAllType.FULL_TIME
								else -> throw IllegalArgumentException("Invalid ShowAllType")
				}
}

