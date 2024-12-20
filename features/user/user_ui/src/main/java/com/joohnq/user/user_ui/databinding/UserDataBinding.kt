package com.joohnq.core.databinding

import android.graphics.drawable.ColorDrawable
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.google.android.material.textfield.TextInputEditText
import com.joohnq.core.mappers.StringMapper
import com.joohnq.core.state.UiState
import com.joohnq.shared_resources.R
import com.joohnq.user.user_ui.mappers.fold
import com.joohnq.user_domain.entities.User

@BindingAdapter("check_your_email")
fun setCheckYourEmail(textView: TextView, email: String) {
				textView.text = textView.context.getString(R.string.check_your_email_subtitle, email)
}

@BindingAdapter("image")
fun setUserImage(imageView: ImageView, user: UiState<User>?) {
				user?.fold(
								onSuccess = { u ->
												val image = u.imageUrl

												Glide
																.with(imageView)
																.load(
																				image.ifEmpty { ColorDrawable(ContextCompat.getColor(imageView.context, R.color.GRAY8)) }
																)
																.into(imageView)
								},
				)
}

@BindingAdapter("name")
fun setUserName(textView: TextView, user: UiState<User>?) {
				val context = textView.context
				user?.fold(
								onLoading = {
												textView.text = context.getString(R.string.salutation, "...")
								},
								onSuccess = { u ->
												val name = StringMapper.getFirstWord(u.name)
												textView.text = context.getString(R.string.salutation, name)
								},
				)
}

@BindingAdapter("name")
fun setUserName(textInputEditText: TextInputEditText, user: UiState<User>?) {
				user?.fold(
								onSuccess = { u ->
												textInputEditText.setText(u.name)
								},
				)
}


@BindingAdapter("email")
fun setUserEmail(textInputEditText: TextInputEditText, user: UiState<User>?) {
				user?.fold(
								onSuccess = { u ->
												textInputEditText.setText(u.email)
								},
				)
}

@BindingAdapter("email")
fun setUserEmail(textView: TextView, user: UiState<User>?) {
				user?.fold(
								onSuccess = { u ->
												textView.text = u.email
								},
				)
}

@BindingAdapter("pageVisibilityByUserState")
fun setPageVisibilityByUserState(view: View, user: UiState<User>?) {
				view.visibility = if(user is UiState.Loading) View.VISIBLE else View.GONE
}

@BindingAdapter("occupation")
fun setUserOccupation(textInputEditText: TextInputEditText, user: UiState<User>?) {
				user?.fold(
								onSuccess = { u ->
												textInputEditText.setText(u.occupation)
								},
				)
}


