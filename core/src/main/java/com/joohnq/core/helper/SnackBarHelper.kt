package com.joohnq.core.helper

import android.view.View
import com.google.android.material.snackbar.Snackbar

object SnackBarHelper {
				operator fun invoke(view: View, error: String) {
								Snackbar.make(view, error, Snackbar.LENGTH_LONG).show()
				}
}