package com.joohnq.home.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.joohnq.favorite_ui.viewmodel.FavoritesViewModel
import com.joohnq.job_domain.entities.Job
import com.joohnq.job_ui.databinding.CustomItemJobBinding
import com.joohnq.user.user_ui.mappers.fold

class HomeJobsViewHolderItem(
				private val binding: CustomItemJobBinding,
): ViewHolder(binding.root) {
				fun bind(
								job: Job,
								onClick: (String) -> Unit,
				) {
								val id = job.id
								binding.job = job
								binding.onItemClick = View.OnClickListener { onClick(id) }
				}
}