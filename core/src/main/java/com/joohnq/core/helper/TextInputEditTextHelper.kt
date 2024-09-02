package com.joohnq.core.helper

import androidx.core.widget.doOnTextChanged
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

fun TextInputEditText.doOnTextChanged(
				textInputLayout: TextInputLayout,
				run: () -> Unit = {}
) {
				doOnTextChanged { _, _, _, _ ->
								textInputLayout.applyOnTextChange()
								run()
				}
}

fun TextInputEditText.doOnTextChangedWithTextReturn(
				textInputLayout: TextInputLayout,
				run: (String) -> Unit = {}
) {
				doOnTextChanged { _, _, _, _ ->
								textInputLayout.applyOnTextChange()
								run(text.toString())
				}
}