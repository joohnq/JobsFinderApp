package com.joohnq.search.activities

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.joohnq.core.helper.RecyclerViewHelper
import com.joohnq.core.helper.SnackBarHelper
import com.joohnq.favorite_ui.viewmodel.FavoritesViewModel
import com.joohnq.job_ui.viewmodel.JobsViewModel
import com.joohnq.search.adapter.SearchListAdapter
import com.joohnq.search.databinding.ActivitySearchBinding
import com.joohnq.search.fragments.CustomSearchFilterFragment
import com.joohnq.search.navigation.SearchNavigation
import com.joohnq.search.viewmodel.FiltersViewModel
import com.joohnq.user.user_ui.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchActivity: AppCompatActivity() {
				private var _binding: ActivitySearchBinding? = null
				private val binding get() = _binding!!
				private val userViewModel: UserViewModel by viewModels()
				private val favoritesViewModel: FavoritesViewModel by viewModels()
				private val jobViewModel: JobsViewModel by viewModels()
				private val filtersViewModel: FiltersViewModel by viewModels()
				private val onFailure = { error: String ->
								SnackBarHelper(binding.root, error)
				}
				private val searchJobAdapter: SearchListAdapter by lazy {
								SearchListAdapter(
												favoriteObserver = { jobId ->
//                addFavoritesObserver(jobId) { drawable ->
//                    binding.imgBtnFavorite.setImageResource(drawable)
//                }
																true
												},
												onFavourite = { jobId: String, state: Boolean ->
//                userViewModel.handleJobIdFavorite(jobId)
												},
												onClick = { id: String ->
																SearchNavigation.navigateToJobDetailActivity(this, id)
												}
								)
				}
				private val customSearchFilterFragment: CustomSearchFilterFragment by lazy {
								CustomSearchFilterFragment(filtersViewModel) {
												val title = binding.textInputEditTextSearchHome.text.toString()
												searchJob(title)
								}
				}

//    private fun addFavoritesObserver(
//        jobId: String, onBinding: (Int) -> Unit
//    ) {
//        userViewModel.favorites.observe(this) { state ->
//            state.handleUiState(
//                onSuccess = {
//                    this.binding.loadingLayoutFavorite.visibility = View.GONE
//                    val isFavorite: Boolean? = userViewModel.isItemFavorite(jobId)
//                    val novoDrawable = if (isFavorite == true) {
//                        R.drawable.ic_favorites_filled_red_24
//                    } else {
//                        R.drawable.ic_favorite_24
//                    }
//                    onBinding(novoDrawable)
//                }, onLoading = {
//                    this.binding.loadingLayoutFavorite.visibility = View.VISIBLE
//                }
//            )
//        }
//    }

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
												textInputEditTextSearchHome.addTextChangedListener(object: TextWatcher {
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
								RecyclerViewHelper.initVertical(
												binding.rvSearch,
												searchJobAdapter
								)
				}

				private fun showBottomSheetDialog() {
								val dialog = customSearchFilterFragment
								dialog.show(supportFragmentManager, "CustomSearchFilterFragment")
				}

				private fun searchJob(title: String?) {
								lifecycleScope.launch {
//												val state = jobViewModel.searchJob(title)
//												searchJobAdapter.setState(state.toRecyclerViewState(onFailure = onFailure))
//            state.fold(
//                onLoading = {
//                    binding.loadingLayoutFavorite.visibility = View.VISIBLE
//                    binding.tvDontHave.visibility = View.GONE
//                    binding.rvSearch.visibility = View.GONE
//                },
//                onFailure = { error ->
//                    SnackBarHelper(binding.root, error.toString())
//                },
//                onSuccess = { jobs ->
//                    binding.loadingLayoutFavorite.visibility = View.GONE
//                    jobs.run {
//                        if (isEmpty()) {
//                            binding.tvDontHave.visibility = View.VISIBLE
//                            binding.rvSearch.visibility = View.GONE
//                        } else {
//                            binding.tvDontHave.visibility = View.GONE
//                            binding.rvSearch.visibility = View.VISIBLE
//                        }
//                        searchJobAdapter.jobs = this
//                    }
//                }
//            )
								}
				}
}

