package com.joohnq.jobsfinderapp.view

import SearchListAdapter
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.joohnq.jobsfinderapp.R
import com.joohnq.jobsfinderapp.databinding.ActivitySearchBinding
import com.joohnq.jobsfinderapp.util.Toast
import com.joohnq.jobsfinderapp.util.handleUiState
import com.joohnq.jobsfinderapp.view.fragments.CustomSearchFilterFragment
import com.joohnq.jobsfinderapp.viewmodel.FiltersViewModel
import com.joohnq.jobsfinderapp.viewmodel.JobsViewModel
import com.joohnq.jobsfinderapp.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchActivity : AppCompatActivity() {
    private val context: Context = this
    private var _binding: ActivitySearchBinding? = null
    private val binding get() = _binding!!
    private val userViewModel: UserViewModel by viewModels()
    private val jobViewModel: JobsViewModel by viewModels()
    private val filtersViewModel: FiltersViewModel by viewModels()
    private val searchJobAdapter: SearchListAdapter by lazy {
        SearchListAdapter(
            favoriteObserver = { jobId, binding ->
                addFavoritesObserver(jobId) { drawable ->
                    binding.imgBtnFavorite.setImageResource(drawable)
                }
            },
            onFavourite = { jobId: String ->
//                userViewModel.handleJobIdFavorite(jobId)
            },
        )
    }
    private val customSearchFilterFragment: CustomSearchFilterFragment by lazy {
        CustomSearchFilterFragment(filtersViewModel) {
            val title = binding.textInputEditTextSearchHome.text.toString()
            searchJob(title)
        }
    }

    private fun addFavoritesObserver(
        jobId: String, onBinding: (Int) -> Unit
    ) {
        userViewModel.favorites.observe(this) { state ->
            state.handleUiState(
                onSuccess = {
                    this.binding.loadingLayoutFavorite.visibility = View.GONE
                    val isFavorited: Boolean? = userViewModel.isItemFavorite(jobId)
                    val novoDrawable = if (isFavorited == true) {
                        R.drawable.ic_favorites_filled_red_24
                    } else {
                        R.drawable.ic_favorite_24
                    }
                    onBinding(novoDrawable)
                }, onLoading = {
                    this.binding.loadingLayoutFavorite.visibility = View.VISIBLE
                }
            )
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initRv()
        bindButtons()
        val runOnLoad = intent.getBooleanExtra("runOnLoad", false)
        if (runOnLoad) {
            val title = binding.textInputEditTextSearchHome.text.toString()
            searchJob(title)
        }
    }

    private fun bindButtons() {
        with(binding) {
            imgBtnFilters.setOnClickListener {
                showBottomSheetDialog()
            }
            textInputEditTextSearchHome.requestFocus()
            textInputEditTextSearchHome.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?, start: Int, count: Int, after: Int
                ) = Unit

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) =
                    Unit

                override fun afterTextChanged(s: Editable?) {
                    val newText = s.toString()
                    searchJob(newText)
                }
            })
        }
    }

    private fun initRv() {
        with(binding) {
            rvSearch.layoutManager = LinearLayoutManager(
                this@SearchActivity, LinearLayoutManager.VERTICAL, false
            )
            rvSearch.adapter = searchJobAdapter
        }
    }

    private fun showBottomSheetDialog() {
        val dialog = customSearchFilterFragment
        dialog.show(supportFragmentManager, "CustomSearchFilterFragment")
    }

    private fun searchJob(title: String?) {
        lifecycleScope.launch {
            val state = jobViewModel.searchJob(title)
            state.handleUiState(
                onLoading = {
                    binding.loadingLayoutFavorite.visibility = View.VISIBLE
                    binding.tvDontHave.visibility = View.GONE
                    binding.rvSearch.visibility = View.GONE
                },
                onFailure = { error ->
                    Toast(context).invoke(error.toString())
                },
                onSuccess = { jobs ->
                    binding.loadingLayoutFavorite.visibility = View.GONE
                    jobs.run {
                        if (isEmpty()) {
                            binding.tvDontHave.visibility = View.VISIBLE
                            binding.rvSearch.visibility = View.GONE
                        } else {
                            binding.tvDontHave.visibility = View.GONE
                            binding.rvSearch.visibility = View.VISIBLE
                        }
                        searchJobAdapter.jobs = this
                    }
                }
            )
        }
    }
}

