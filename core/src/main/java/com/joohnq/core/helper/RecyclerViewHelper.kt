package com.joohnq.core.helper

import android.annotation.SuppressLint
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter

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

				fun initHorizontal(recyclerView: RecyclerView, adapter: Adapter<*>) {
								recyclerView.apply {
												this.adapter = adapter
												layoutManager = LinearLayoutManager(
																context,
																RecyclerView.HORIZONTAL,
																false
												)
								}
				}

				fun initVerticalWithoutScroll(recyclerView: RecyclerView, adapter: Adapter<*>) {
								recyclerView.apply {
												initVertical(this, adapter)
												isNestedScrollingEnabled = false
								}
				}

				fun initVerticalWithScrollEvent(recyclerView: RecyclerView, adapter: Adapter<*>) {
								recyclerView.apply {
												initVertical(this, adapter)
												addOnScrollListener(object: RecyclerView.OnScrollListener() {
																override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
																				super.onScrolled(recyclerView, dx, dy)
																				adapter.notifyDataSetChanged()
																}
												})
								}
				}

				fun initVerticalWithScrollEvent(recyclerView: RecyclerView, adapter: Adapter<*>, onEndScroll: () -> Unit) {
								recyclerView.apply {
												initVertical(this, adapter)
												addOnScrollListener(object: RecyclerView.OnScrollListener() {
																override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
																				super.onScrolled(recyclerView, dx, dy)
																				onEndScroll()
																				adapter.notifyDataSetChanged()
																}
												})
								}
				}
}