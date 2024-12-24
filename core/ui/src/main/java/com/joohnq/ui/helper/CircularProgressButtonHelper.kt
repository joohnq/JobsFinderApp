package com.joohnq.ui.helper

import com.github.leandroborgesferreira.loadingbutton.customViews.CircularProgressButton
import com.joohnq.shared_resources.R

object CircularProgressButtonHelper {
				fun startAnimation(button: CircularProgressButton) {
								button.startAnimation()
				}

				fun doneAnimation(button: CircularProgressButton) {
								button.doneLoadingAnimation(
												R.color.WHITE,
												BitmapHelper.drawableToBitmap(
																button.context,
																R.drawable.ic_check
												)
								)
								button.clearAnimation()
				}

				fun failureAnimation(button: CircularProgressButton) {
								button.doneLoadingAnimation(
												R.color.WHITE,
												BitmapHelper.drawableToBitmap(
																button.context,
																R.drawable.ic_close
												)
								)
				}

				fun revertAnimation(button: CircularProgressButton) {
								button.revertAnimation()
				}
}