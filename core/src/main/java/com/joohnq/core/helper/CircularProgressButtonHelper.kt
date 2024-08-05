package com.joohnq.core.helper

import com.github.leandroborgesferreira.loadingbutton.customViews.CircularProgressButton
import com.joohnq.shared_resources.R

object CircularProgressButtonHelper {
				fun doneLoadingAnimation(button: CircularProgressButton, status: Boolean) {
								button.doneLoadingAnimation(
												R.color.WHITE,
												BitmapHelper.drawableToBitmap(
																button.context,
																if (status) R.drawable.ic_check else R.drawable.ic_close
												)
								)
				}
}