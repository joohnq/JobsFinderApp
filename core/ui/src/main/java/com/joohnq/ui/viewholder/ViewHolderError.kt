package com.joohnq.ui.viewholder

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.joohnq.core.databinding.RecyclerViewErrorBinding

class ViewHolderError(private val binding: RecyclerViewErrorBinding): ViewHolder(binding.root){
				fun bind(message: String?) {
								binding.tvError.setText(message.toString())
				}
}