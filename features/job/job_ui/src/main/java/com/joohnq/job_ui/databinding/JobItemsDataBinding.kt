package com.joohnq.job_ui.databinding

import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.joohnq.job_domain.entities.Salary
import com.joohnq.job_domain.mappers.SalaryMapper
import com.joohnq.shared_resources.R

@BindingAdapter("isFavorite")
fun setFavoriteIcon(button: ImageButton, isFavorite: Boolean) {
				if (isFavorite) {
								button.setImageResource(R.drawable.ic_favorites_filled_red_24)
				} else {
								button.setImageResource(R.drawable.ic_favorite_24)
				}
}

@BindingAdapter("jobSalary")
fun setJobSalary(textView: TextView, jobSalary: Salary) {
				textView.text = SalaryMapper.getFormattedSalary(jobSalary)
}

@BindingAdapter("imageUrl")
fun setCompanyLogo(imageView: ImageView, imageUrl: String) {
				Glide
								.with(imageView)
								.load(imageUrl)
								.into(imageView)
}