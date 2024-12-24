package com.joohnq.core.ui.helper

import android.annotation.SuppressLint
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.joohnq.core.domain.entity.RecyclerViewState

@SuppressLint("NotifyDataSetChanged")
object RecyclerViewHelper {
				fun initVertical(recyclerView: RecyclerView, adapter: Adapter<*>) {
								recyclerView.apply {
												this.adapter = adapter
												layoutManager = LinearLayoutManager(
																context,
																RecyclerView.VERTICAL,
																false
												)
								}
				}

				fun initVerticalWithoutScroll(recyclerView: RecyclerView, adapter: Adapter<*>) {
								recyclerView.apply {
												com.joohnq.core.ui.helper.RecyclerViewHelper.initVertical(this, adapter)
												isNestedScrollingEnabled = false
								}
				}

				fun initVerticalWithScrollEvent(
								recyclerView: RecyclerView,
								adapter: Adapter<*>,
								onEndScroll: () -> Unit
				) {
								recyclerView.apply {
												com.joohnq.core.ui.helper.RecyclerViewHelper.initVertical(this, adapter)
												addOnScrollListener(object: RecyclerView.OnScrollListener() {
																override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
																				super.onScrolled(recyclerView, dx, dy)
//																				val state = adapter.getState()
//																				if (state is RecyclerViewState.Success) {
//																								val layoutManager = recyclerView.layoutManager as LinearLayoutManager
//																								val totalChildCount = layoutManager.childCount
//																								val totalItemCount = layoutManager.itemCount
//																								val lastVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
//
//																								if (lastVisibleItemPosition + totalChildCount >= totalItemCount) {
//																												println("Scrollow")
//																												onEndScroll()
//																								}
//																				}
																}
												})
								}
				}
}