package com.joohnq.show_all_domain.mappers

import com.joohnq.show_all_domain.entities.ShowAllType

object ShowAllTypeMapper {
				fun toShowAllType(str: String): ShowAllType = when (str) {
								"popular" -> ShowAllType.POPULAR
								"recent" -> ShowAllType.RECENT_POST
								else -> throw IllegalArgumentException("Invalid ShowAllType")
				}
}

