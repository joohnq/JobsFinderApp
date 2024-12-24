package com.joohnq.ui

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Parcelable
import android.view.inputmethod.InputMethodManager
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewbinding.ViewBinding

fun ViewBinding.setOnApplyWindowInsetsListener(){
				ViewCompat.setOnApplyWindowInsetsListener(root) { v, insets ->
								val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
								v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
								insets
				}
}

fun ViewBinding.setOnApplyWindowInsetsListener(bottoms: Int){
				ViewCompat.setOnApplyWindowInsetsListener(root) { v, insets ->
								val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
								v.setPadding(systemBars.left, systemBars.top, systemBars.right, bottoms)
								insets
				}
}

fun Activity.closeKeyboard(){
				val view = this.currentFocus
				val imm = view?.context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
				imm?.hideSoftInputFromWindow(view?.windowToken, 0)
}

@Suppress("DEPRECATION")
inline fun <reified T: Parcelable> Intent.getParcelableExtraProvider(identifierParameter: String): T? {
				return if (Build.VERSION.SDK_INT >= 33) {
								getParcelableExtra(identifierParameter, T::class.java)
				} else {
								getParcelableExtra(identifierParameter)
				}
}