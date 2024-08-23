package com.joohnq.core

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.joohnq.core.databinding.RecyclerViewEmptyBinding
import com.joohnq.core.databinding.RecyclerViewErrorBinding
import com.joohnq.core.databinding.RecyclerViewLoadingBinding
import com.joohnq.core.state.RecyclerViewState
import com.joohnq.core.viewholder.ViewHolderEmpty
import com.joohnq.core.viewholder.ViewHolderError
import com.joohnq.core.viewholder.ViewHolderLoading
import com.joohnq.job_domain.entities.Job

@Suppress("UNCHECKED_CAST")
abstract class LESEAdapter<VHLoading: ViewHolder, VHEmpty: ViewHolder, VHSuccess: ViewHolder, VHError: ViewHolder>:
				CustomAbstractAdapter<Job, VHLoading, VHEmpty, VHSuccess, VHError>() {

				override fun getJobDiffCallback(oldList: List<Job>, newList: List<Job>): DiffUtil.Callback {
								return object: DiffUtil.Callback() {
												override fun getOldListSize() = oldList.size
												override fun getNewListSize() = newList.size

												override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
																return oldList[oldItemPosition].id == newList[newItemPosition].id
												}

												override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
																return oldList[oldItemPosition] == newList[newItemPosition]
												}
								}
				}

				abstract override fun createSuccessViewHolder(
								inflater: LayoutInflater,
								parent: ViewGroup
				): VHSuccess

				override fun createLoadingViewHolder(inflater: LayoutInflater, parent: ViewGroup): VHLoading =
								ViewHolderLoading(
												RecyclerViewLoadingBinding.inflate(inflater, parent, false)
								) as VHLoading

				override fun createEmptyViewHolder(inflater: LayoutInflater, parent: ViewGroup): VHEmpty =
								ViewHolderEmpty(
												RecyclerViewEmptyBinding.inflate(inflater, parent, false)
								) as VHEmpty

				override fun createErrorViewHolder(inflater: LayoutInflater, parent: ViewGroup): VHError =
								ViewHolderError(
												RecyclerViewErrorBinding.inflate(inflater, parent, false)
								) as VHError

				override fun bindLoadingViewHolder(holder: VHLoading) {}
				override fun bindEmptyViewHolder(holder: VHEmpty) {}
				abstract override fun bindSuccessViewHolder(holder: VHSuccess, position: Int)
				abstract override fun bindErrorViewHolder(holder: VHError)
}
