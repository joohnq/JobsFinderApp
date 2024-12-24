package com.joohnq.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.joohnq.core.ui.databinding.RecyclerViewEmptyBinding
import com.joohnq.core.ui.databinding.RecyclerViewErrorBinding
import com.joohnq.core.ui.databinding.RecyclerViewLoadingBinding
import com.joohnq.core.ui.databinding.RecyclerViewNothingBinding
import com.joohnq.domain.entity.Job

@Suppress("UNCHECKED_CAST")
abstract class JobLESEAdapter<VHNothing: ViewHolder, VHLoading: ViewHolder, VHEmpty: ViewHolder, VHSuccess: ViewHolder, VHError: ViewHolder>: LESEAdapter<Job, VHNothing, VHLoading, VHEmpty, VHSuccess, VHError>() {

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
								com.joohnq.ui.viewholder.ViewHolderLoading(
												RecyclerViewLoadingBinding.inflate(inflater, parent, false)
								) as VHLoading

				override fun createEmptyViewHolder(inflater: LayoutInflater, parent: ViewGroup): VHEmpty =
								com.joohnq.ui.viewholder.ViewHolderEmpty(
												RecyclerViewEmptyBinding.inflate(inflater, parent, false)
								) as VHEmpty

				override fun createErrorViewHolder(inflater: LayoutInflater, parent: ViewGroup): VHError =
								com.joohnq.ui.viewholder.ViewHolderError(
												RecyclerViewErrorBinding.inflate(inflater, parent, false)
								) as VHError

				override fun createNothingViewHolder(inflater: LayoutInflater, parent: ViewGroup): VHNothing =
								com.joohnq.ui.viewholder.ViewHolderNothing(
												RecyclerViewNothingBinding.inflate(inflater, parent, false)
								) as VHNothing

				override fun bindLoadingViewHolder(holder: VHLoading) {}
				override fun bindEmptyViewHolder(holder: VHEmpty) {}
				override fun bindNothingViewHolder(holder: VHNothing){}
				abstract override fun bindSuccessViewHolder(holder: VHSuccess, position: Int)
				abstract override fun bindErrorViewHolder(holder: VHError)
}
