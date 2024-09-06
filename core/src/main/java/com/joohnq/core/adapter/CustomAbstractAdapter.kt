package com.joohnq.core.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.joohnq.core.state.RecyclerViewState

@Suppress("UNCHECKED_CAST")
@SuppressLint("NotifyDataSetChanged")
abstract class CustomAbstractAdapter<T, VHNothing: ViewHolder, VHLoading: ViewHolder, VHEmpty: ViewHolder, VHSuccess: ViewHolder, VHError: ViewHolder>:
				Adapter<ViewHolder>() {
				protected var uiState: RecyclerViewState<T> = RecyclerViewState.None

				fun setState(newState: RecyclerViewState<T>) {
								val oldState = uiState
								uiState = newState
								if (oldState is RecyclerViewState.Success && newState is RecyclerViewState.Success) {
												val diffResult =
																DiffUtil.calculateDiff(getJobDiffCallback(oldState.data, newState.data))
												diffResult.dispatchUpdatesTo(this)
								} else {
												notifyDataSetChanged()
								}
				}

				fun getState(): RecyclerViewState<T> = uiState

				override fun getItemViewType(position: Int): Int {
								return when (uiState) {
												is RecyclerViewState.Loading -> VIEW_TYPE_LOADING
												is RecyclerViewState.Empty -> VIEW_TYPE_EMPTY
												is RecyclerViewState.Success -> VIEW_TYPE_SUCCESS
												is RecyclerViewState.Error -> VIEW_TYPE_ERROR
												is RecyclerViewState.None -> VIEW_TYPE_NONE
								}
				}

				override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
								val inflater = LayoutInflater.from(parent.context)
								return when (viewType) {
												VIEW_TYPE_LOADING -> createLoadingViewHolder(inflater, parent)
												VIEW_TYPE_EMPTY -> createEmptyViewHolder(inflater, parent)
												VIEW_TYPE_SUCCESS -> createSuccessViewHolder(inflater, parent)
												VIEW_TYPE_ERROR -> createErrorViewHolder(inflater, parent)
												VIEW_TYPE_NONE -> createNothingViewHolder(inflater, parent)
												else -> throw IllegalArgumentException("Invalid view type")
								}
				}

				override fun onBindViewHolder(holder: ViewHolder, position: Int) {
								when (holder.itemViewType) {
												VIEW_TYPE_LOADING -> bindLoadingViewHolder(holder as VHLoading)
												VIEW_TYPE_EMPTY -> bindEmptyViewHolder(holder as VHEmpty)
												VIEW_TYPE_SUCCESS -> bindSuccessViewHolder(holder as VHSuccess, position)
												VIEW_TYPE_ERROR -> bindErrorViewHolder(holder as VHError)
												VIEW_TYPE_NONE -> bindNothingViewHolder(holder as VHNothing)
								}
				}

				override fun getItemCount(): Int {
								return when (uiState) {
												is RecyclerViewState.Success -> (uiState as RecyclerViewState.Success<T>).data.size
												else -> 1
								}
				}

				abstract fun getJobDiffCallback(oldList: List<T>, newList: List<T>): DiffUtil.Callback

				abstract fun createSuccessViewHolder(inflater: LayoutInflater, parent: ViewGroup): VHSuccess
				abstract fun createLoadingViewHolder(inflater: LayoutInflater, parent: ViewGroup): VHLoading
				abstract fun createEmptyViewHolder(inflater: LayoutInflater, parent: ViewGroup): VHEmpty
				abstract fun createErrorViewHolder(inflater: LayoutInflater, parent: ViewGroup): VHError
				abstract fun createNothingViewHolder(inflater: LayoutInflater, parent: ViewGroup): VHNothing

				abstract fun bindLoadingViewHolder(holder: VHLoading)
				abstract fun bindEmptyViewHolder(holder: VHEmpty)
				abstract fun bindSuccessViewHolder(holder: VHSuccess, position: Int)
				abstract fun bindErrorViewHolder(holder: VHError)
				abstract fun bindNothingViewHolder(holder: VHNothing)

				companion object {
								const val VIEW_TYPE_LOADING = 0
								const val VIEW_TYPE_EMPTY = 1
								const val VIEW_TYPE_SUCCESS = 2
								const val VIEW_TYPE_ERROR = 3
								const val VIEW_TYPE_NONE = 4
				}
}
