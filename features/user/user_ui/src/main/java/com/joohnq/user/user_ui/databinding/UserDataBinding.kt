package com.joohnq.user.user_ui.databinding

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
import com.joohnq.user_domain.entities.Education
import com.joohnq.user_domain.entities.User
import com.joohnq.user_domain.entities.WorkExperience

@BindingAdapter("image")
fun setUserImage(imageView: ImageView, user: UiState<User?>?) {
				user?.fold(
								onSuccess = { u ->
												if(u == null) return@fold

												val image = u.imageUrl
												Glide
																.with(imageView)
																.load(
																				image.ifEmpty { ColorDrawable(ContextCompat.getColor(imageView.context, R.color.GRAY3)) }
																)
																.into(imageView)
								},
				)
}

@BindingAdapter("name")
fun setUserName(textView: TextView, user: UiState<User?>?) {
				user?.fold(
								onSuccess = { u ->
												if(u == null) return@fold
												val name = u.name
												textView.text = StringMapper.getFirstWord(name)
								},
				)
}

@BindingAdapter("name")
fun setUserName(textInputEditText: TextInputEditText, user: UiState<User?>?) {
				user?.fold(
								onSuccess = { u ->
												if(u == null) return@fold
												textInputEditText.setText(u.name)
								},
				)
}


@BindingAdapter("email")
fun setUserEmail(textInputEditText: TextInputEditText, user: UiState<User?>?) {
				user?.fold(
								onSuccess = { u ->
												if(u == null) return@fold
												textInputEditText.setText(u.email)
								},
				)
}

@BindingAdapter("email")
fun setUserEmail(textView: TextView, user: UiState<User?>?) {
				user?.fold(
								onSuccess = { u ->
												if(u == null) return@fold
												textView.text = u.email
								},
				)
}

@BindingAdapter("aboutMe")
fun setAboutMe(textInputEditText: TextInputEditText, user: UiState<User?>?) {
				user?.fold(
								onSuccess = { u ->
												if(u == null) return@fold
												textInputEditText.setText(u.aboutMe)
								},
				)
}

@BindingAdapter("memberSince")
fun setMemberSince(textView: TextView, user: UiState<User?>?) {
				user?.fold(
								onSuccess = { u ->
												if(u == null) return@fold
												textView.text = u.memberSince
								},
				)
}

@BindingAdapter("jobsApplied")
fun setJobsApplied(textView: TextView, user: UiState<User?>?) {
				user?.fold(
								onSuccess = { u ->
												if(u == null) return@fold
												textView.text = u.application.size.toString()
								},
				)
}


@BindingAdapter("pageVisibilityByUserState")
fun setPageVisibilityByUserState(view: View, user: UiState<User?>?) {
				view.visibility = if(user is UiState.Loading) View.VISIBLE else View.GONE
}

//@BindingAdapter("aboutMe")
//fun setPageVisibilityByUserState(textView: TextView, user: UiState<User?>?) {
//				user?.fold(
//								onSuccess = { u ->
//												if(u == null) return@fold
//												textView.text = u.aboutMe
//								},
//				)
//}

@BindingAdapter("timeEducation")
fun setUserTimeEducation(textView: TextView, timeEducation: Education) {
				textView.text = timeEducation.initialDate
}

@BindingAdapter("timeWorkExperience")
fun setUserTimeWorkExperience(textView: TextView, timeWorkExperience: WorkExperience) {
				textView.text = timeWorkExperience.initialDate
}

