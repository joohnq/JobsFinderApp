package com.joohnq.show_all_ui.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.joohnq.job_domain.entities.Job
import com.joohnq.job_ui.databinding.CustomItemJobBinding

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
								binding.job = job
								binding.onItemClick = View.OnClickListener { onClick(job) }
								binding.isFavorited = isFavorite
								binding.onPressFavorite = View.OnClickListener {
												val newState = !(binding.isFavorited)!!
												binding.isFavorited = newState
												onPressFavorite(job.id)
								}
				}
}