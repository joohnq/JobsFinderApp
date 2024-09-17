package com.joohnq.core.databinding

import android.graphics.Color
import android.view.View
import android.widget.TextView
import androidx.core.graphics.drawable.DrawableCompat
import androidx.databinding.BindingAdapter
import com.joohnq.core.mappers.StringMapper
import com.joohnq.shared_resources.R

@BindingAdapter("remote_jobs")
fun setRemoteJobsCount(textView: TextView, remoteJobs: Long) {
				textView.text = remoteJobs.toString()
}

@BindingAdapter("part_time_jobs")
fun setPartTimeJobs(textView: TextView, partTimeJobs: Long) {
				textView.text = partTimeJobs.toString()
}

@BindingAdapter("full_time_jobs")
fun setFullTimeJobs(textView: TextView, fullTimeJobs: Long) {
				textView.text = fullTimeJobs.toString()
}

@BindingAdapter("title")
fun setTitle(textView: TextView, title: String) {
				textView.text = StringMapper.fixEncoding(title)
}

@BindingAdapter("company")
fun setCompany(textView: TextView, company: String?) {
				if (company == null) {
								textView.visibility = View.GONE
				} else {
								textView.text = company
				}
}

@BindingAdapter("location")
fun setLocation(textView: TextView, location: String?) {
				if (location == null) {
								textView.visibility = View.GONE
				} else {
								textView.text = StringMapper.fixEncoding(location)
				}
}

@BindingAdapter("salary")
fun setSalary(textView: TextView, salary: String?) {
				if (salary == null) {
								textView.visibility = View.GONE
				} else {
								textView.text = salary
				}
}

@BindingAdapter("description")
fun setDescription(textView: TextView, description: String) {
				textView.text = StringMapper.fixEncoding(description)
}

@BindingAdapter("rating")
fun setRating(textView: TextView, rating: Double?) {
				if (rating == null) {
								textView.visibility = View.GONE
				} else {
								textView.text = rating.toString()
				}
}

@BindingAdapter("isExpired")
fun setIsExpired(textView: TextView, isExpired: Boolean) {
				val context = textView.context
				val text = if (isExpired) R.string.expired else R.string.disponivel
				textView.text = context.getString(R.string.status, context.getString(text))
				val drawable = textView.compoundDrawablesRelative[2]

				drawable?.let {
								DrawableCompat.setTint(it, if (isExpired) Color.RED else Color.GREEN)
								textView.setCompoundDrawablesRelativeWithIntrinsicBounds(
												textView.compoundDrawablesRelative[0],
												textView.compoundDrawablesRelative[1],
												it,
												textView.compoundDrawablesRelative[3]
								)
				}
}


