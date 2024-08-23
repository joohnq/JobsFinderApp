package com.joohnq.show_all_domain.mappers

import com.joohnq.show_all_domain.entities.ShowAllType

object ShowAllTypeMapper {
				fun toShowAllType(str: String): ShowAllType = when (str) {
								"REMOTE_JOBS" -> ShowAllType.REMOTE_JOBS
								"PART_TIME" -> ShowAllType.PART_TIME
								"FULL_TIME" -> ShowAllType.FULL_TIME
								else -> throw IllegalArgumentException("Invalid ShowAllType")
				}
}

