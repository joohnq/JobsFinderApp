package com.joohnq.job.ui.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.joohnq.core.ui.databinding.CustomItemJobBinding
import com.joohnq.job.domain.entity.Job

class SearchViewHolderItem(
				private val binding: CustomItemJobBinding
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