package com.joohnq.core.ui.viewholder

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.joohnq.core.ui.databinding.RecyclerViewErrorBinding

class ViewHolderError(private val binding: RecyclerViewErrorBinding): ViewHolder(binding.root){
				fun bind(message: String?) {
								binding.tvError.setText(message.toString())
				}
}