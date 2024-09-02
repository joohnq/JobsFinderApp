package com.joohnq.job_ui.databinding

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.joohnq.core.mappers.StringMapper
import com.joohnq.core.state.UiState
import com.joohnq.job_domain.entities.Job
import com.joohnq.shared_resources.R
import com.joohnq.user.user_ui.mappers.fold

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

@BindingAdapter("position_name")
fun setPositionName(textView: TextView, positionName: String) {
				textView.text = StringMapper.fixEncoding(positionName)
}