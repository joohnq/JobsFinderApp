package com.joohnq.jobsfinderapp.view

import SearchListAdapter
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.joohnq.jobsfinderapp.R
import com.joohnq.jobsfinderapp.databinding.ActivitySearchBinding
import com.joohnq.jobsfinderapp.util.Functions
import com.joohnq.jobsfinderapp.view.fragments.CustomSearchFilterFragment
import com.joohnq.jobsfinderapp.viewmodel.FiltersViewModel
import com.joohnq.jobsfinderapp.viewmodel.JobsViewModel
import com.joohnq.jobsfinderapp.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchActivity : AppCompatActivity() {
    private val tag = "SearchActivity"
    private val binding: ActivitySearchBinding by lazy {
        ActivitySearchBinding.inflate(layoutInflater)
    }
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
                userViewModel.handleJobIdFavorite(jobId)
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
            Functions.handleUiState(state, onSuccess = {
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
            })
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
        with(binding) {
            val category: String? = filtersViewModel.category
            val company: String? = filtersViewModel.company
            val location: String? = filtersViewModel.location
            val salaryEntry: String? = filtersViewModel.salaryEntry
            val salaryEnd: String? = filtersViewModel.salaryEnd
            val typeText: String? = filtersViewModel.typeText
            jobViewModel.searchJob(
                title, category, company, location, salaryEntry, salaryEnd, typeText
            ) { state ->
                Functions.handleUiState(state, onLoading = {
                    loadingLayoutFavorite.visibility = View.VISIBLE
                    tvDontHave.visibility = View.GONE
                    rvSearch.visibility = View.GONE
                }, onFailure = { error ->
                    Functions.showErrorWithToast(
                        this@SearchActivity, tag, error
                    )
                }, onSuccess = { jobs ->
                    loadingLayoutFavorite.visibility = View.GONE
                    jobs.run {
                        if (isEmpty()) {
                            tvDontHave.visibility = View.VISIBLE
                            rvSearch.visibility = View.GONE
                        } else {
                            tvDontHave.visibility = View.GONE
                            rvSearch.visibility = View.VISIBLE
                        }
                        searchJobAdapter.jobs = this
                    }
                })
            }
        }
    }
}

