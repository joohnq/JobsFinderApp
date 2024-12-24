package com.joohnq.core.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

abstract class BaseActivity<VB : ViewBinding> : AppCompatActivity() {
				private var _binding: VB? = null
				protected val binding get() = _binding!!

				abstract fun inflateBinding(inflater: LayoutInflater, container: ViewGroup?): VB

				override fun onCreate(savedInstanceState: Bundle?) {
								super.onCreate(savedInstanceState)
								_binding = inflateBinding(layoutInflater, null)
								setContentView(binding.root)
				}

				override fun onDestroy() {
								super.onDestroy()
								_binding = null
				}
}