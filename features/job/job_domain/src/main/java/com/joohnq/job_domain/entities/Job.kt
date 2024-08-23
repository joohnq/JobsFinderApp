package com.joohnq.job_domain.entities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Job(
				val id: String,
				val company: String,
				val description: String,
				val location: String,
				val rating: Double?,
				val salary: String?,
				val url: String,
				@SerialName("positionname") val positionName: String,
				@SerialName("reviewscount") val reviewsCount: Int?,
				@SerialName("postedat") val postedAt: String?,
				@SerialName("postingdateparsed") val postingDateParsed: String?,
				@SerialName("descriptionhtml") val descriptionHTML:String,
				@SerialName("externalapplylink") val externalApplyLink: String?,
				@SerialName("searchinput") val searchInput: String?,
				@SerialName("isexpired") val isExpired: Boolean,
				@SerialName("companyinfo") val companyInfo: String?,
				@SerialName("jobtype") val jobType: String?
)