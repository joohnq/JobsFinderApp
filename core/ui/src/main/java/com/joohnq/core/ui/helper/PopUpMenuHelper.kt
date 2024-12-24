package com.joohnq.core.ui.helper

import android.content.Context
import android.view.View
import android.widget.PopupMenu

object PopUpMenuHelper {
				fun invoke(context: Context, view: View, menuRes: Int, onClick: (Int) -> Boolean) {
								PopupMenu(context, view).apply {
												menuInflater.inflate(menuRes, menu)
												setOnMenuItemClickListener { menu ->
																onClick(menu.itemId)
												}
												show()
								}
				}
}