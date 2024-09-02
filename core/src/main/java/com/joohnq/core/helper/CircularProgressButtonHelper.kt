package com.joohnq.core.helper

import com.github.leandroborgesferreira.loadingbutton.customViews.CircularProgressButton
import com.joohnq.shared_resources.R

object CircularProgressButtonHelper {
				fun startLoadingAnimation(button: CircularProgressButton) {
								button.startAnimation()
				}

				fun doneLoadingAnimation(button: CircularProgressButton) {
								button.doneLoadingAnimation(
												R.color.WHITE,
												BitmapHelper.drawableToBitmap(
																button.context,
																R.drawable.ic_check
												)
								)
								button.clearAnimation()
				}

				fun failureLoadingAnimation(button: CircularProgressButton) {
								button.doneLoadingAnimation(
												R.color.WHITE,
												BitmapHelper.drawableToBitmap(
																button.context,
																R.drawable.ic_close
												)
								)
				}
}