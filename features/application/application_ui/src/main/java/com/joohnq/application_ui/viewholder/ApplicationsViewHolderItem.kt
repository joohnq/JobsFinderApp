package com.joohnq.application_ui.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.joohnq.application_ui.databinding.CustomItemApplicationBinding
import com.joohnq.job_domain.entities.Job

class ApplicationsViewHolderItem(private val binding: CustomItemApplicationBinding):
				RecyclerView.ViewHolder(binding.root) {
				fun bind(job: Job) {
								binding.job = job
								binding.onItemClick = View.OnClickListener {}
				}
}