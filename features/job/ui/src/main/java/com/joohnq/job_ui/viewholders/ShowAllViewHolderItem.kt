package com.joohnq.job_ui.viewholders

import androidx.recyclerview.widget.RecyclerView
import com.joohnq.domain.entity.Job
import com.joohnq.ui.databinding.CustomItemJobBinding

class ShowAllViewHolderItem(
				private val binding: CustomItemJobBinding,
):
				RecyclerView.ViewHolder(binding.root) {
				fun bind(
								job: Job,
								isFavorite: Boolean,
								onClick: (Job) -> Unit,
								onPressFavorite: (String) -> Unit,
				) {
//								binding.job = job
//								binding.onItemClick = View.OnClickListener { onClick(job) }
//								binding.isFavorited = isFavorite
//								binding.onPressFavorite = View.OnClickListener {
//												val newState = !(binding.isFavorited)!!
//												binding.isFavorited = newState
//												onPressFavorite(job.id)
//								}
				}
}