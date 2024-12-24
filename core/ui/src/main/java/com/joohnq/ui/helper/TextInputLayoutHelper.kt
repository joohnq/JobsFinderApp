package com.joohnq.ui.helper

import com.google.android.material.textfield.TextInputLayout

fun TextInputLayout.applyError(error: String) {
				apply {
								isErrorEnabled = true
								this.error = error
								helperText = error
								requestFocus()
				}
}

fun TextInputLayout.applyOnTextChange() {
				this.apply {
								error = null
								helperText = null
								isErrorEnabled = false
				}
}