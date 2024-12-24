package com.joohnq.job.domain.entity

import android.os.Parcel
import android.os.Parcelable
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
				@SerialName("descriptionhtml") val descriptionHTML: String,
				@SerialName("externalapplylink") val externalApplyLink: String?,
				@SerialName("searchinput") val searchInput: String?,
				@SerialName("isexpired") val isExpired: Boolean,
				@SerialName("companyinfo") val companyInfo: String?,
				@SerialName("jobtype") val jobType: String?
): Parcelable {
				constructor(parcel: Parcel): this(
								parcel.readString() ?: "",
								parcel.readString() ?: "",
								parcel.readString() ?: "",
								parcel.readString() ?: "",
								parcel.readValue(Double::class.java.classLoader) as? Double,
								parcel.readString(),
								parcel.readString() ?: "",
								parcel.readString() ?: "",
								parcel.readValue(Int::class.java.classLoader) as? Int,
								parcel.readString(),
								parcel.readString(),
								parcel.readString() ?: "",
								parcel.readString(),
								parcel.readString(),
								parcel.readByte() != 0.toByte(),
								parcel.readString(),
								parcel.readString()
				)

				override fun describeContents(): Int = 0

				override fun writeToParcel(parcel: Parcel, flags: Int) {
								parcel.writeString(id)
								parcel.writeString(company)
								parcel.writeString(description)
								parcel.writeString(location)
								parcel.writeValue(rating)
								parcel.writeString(salary)
								parcel.writeString(url)
								parcel.writeString(positionName)
								parcel.writeValue(reviewsCount)
								parcel.writeString(postedAt)
								parcel.writeString(postingDateParsed)
								parcel.writeString(descriptionHTML)
								parcel.writeString(externalApplyLink)
								parcel.writeString(searchInput)
								parcel.writeByte(if (isExpired) 1 else 0)
								parcel.writeString(companyInfo)
								parcel.writeString(jobType)
				}

				companion object CREATOR: Parcelable.Creator<Job> {
								override fun createFromParcel(parcel: Parcel): Job {
												return Job(parcel)
								}

								override fun newArray(size: Int): Array<Job?> {
												return arrayOfNulls(size)
								}
				}
}
