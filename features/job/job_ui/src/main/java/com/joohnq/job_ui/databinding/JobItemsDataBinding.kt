package com.joohnq.job_ui.databinding

import android.util.Log
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.joohnq.core.constants.Constants
import com.joohnq.core.state.UiState
import com.joohnq.job_domain.entities.Job
import com.joohnq.shared_resources.R
import com.joohnq.user.user_ui.mappers.fold

@BindingAdapter("imageUrl")
fun setCompanyLogo(imageView: ImageView, imageUrl: String) {
				Glide
								.with(imageView)
								.load(imageUrl)
								.into(imageView)
}

@BindingAdapter("remote_jobs")
fun setRemoteJobsCount(textView: TextView, remoteJobs: UiState<List<Job>>?) {
				remoteJobs?.fold(
								onSuccess = { jobs ->
												textView.text = jobs.size.toString()
												Log.i(Constants.TAG, "setRemoteJobsCount")
								},
								onFailure = { error ->
												textView.text = error
								}
				)
}

@BindingAdapter("part_time_jobs")
fun setPartTimeJobs(textView: TextView, partTimeJobs: UiState<List<Job>>?) {
				partTimeJobs?.fold(
								onSuccess = { jobs ->
												textView.text = jobs.size.toString()
												Log.i(Constants.TAG, "setPartTimeJobsCount")
								},
								onFailure = { error ->
												textView.text = error
								}
				)
}

@BindingAdapter("full_time_jobs")
fun setFullTimeJobs(textView: TextView, fullTimeJobs: UiState<List<Job>>?) {
				fullTimeJobs?.fold(
								onSuccess = { jobs ->
												textView.text = jobs.size.toString()
												Log.i(Constants.TAG, "setFullTimeJobsCount")
								},
								onFailure = { error ->
												textView.text = error
								}
				)
}