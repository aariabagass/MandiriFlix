package com.ariabagas.mandiriflix.ui.screen.seeall

import android.os.Bundle
import android.os.Parcelable
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs

import androidx.recyclerview.widget.GridLayoutManager
import com.ariabagas.mandiriflix.R
import com.ariabagas.mandiriflix.databinding.FragmentSeeAllBinding
import com.ariabagas.mandiriflix.ui.adapter.MovieAdapter
import com.ariabagas.mandiriflix.ui.base.BaseFragment
import com.ariabagas.mandiriflix.util.Content
import com.ariabagas.mandiriflix.util.Type
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SeeAllFragment : BaseFragment<FragmentSeeAllBinding>(R.layout.fragment_see_all) {

    override val viewModel: SeeAllViewModel by viewModels()

    private val movieAdapter by lazy { MovieAdapter(isGrid = true) }

    private var detailType: Parcelable? = null
    private var genreId: Int? = null
    private var listId: String? = null
    private var region: String? = null

    var contentType: Parcelable? = null
    var title: String? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getArgs()
        getList()

        binding.rvSeeAll.layoutManager = GridLayoutManager(
            requireContext(), if (contentType == Content.VIDEOS || contentType == Content.IMAGES) 2
            else 3
        )

        manageRecyclerViewAdapterLifecycle(binding.rvSeeAll)
    }

    private fun getArgs() {
        val args: SeeAllFragmentArgs by navArgs()

        contentType = args.contentType
        detailType = args.detailType
        genreId = args.genreId
        listId = args.listId
        region = args.region
        title = args.title + if (contentType == Content.GENRE) {
            " " + if (detailType == Type.MOVIE) getString(R.string.title_movies) else getString(
                R.string.title_tv_shows
            )
        } else ""
    }

    private fun getList() {
        binding.rvSeeAll.adapter = movieAdapter

        collectFlows(listOf(::collectListResult, ::collectUiState))

        viewModel.initRequest(contentType, detailType, genreId, listId, region)
    }

    private suspend fun collectListResult() {
        viewModel.results.collect { results ->
            movieAdapter.submitList(results.toList())
        }
    }

    private suspend fun collectUiState() {
        viewModel.uiState.collect { state ->
            if (state.isError) showSnackbar(
                message = state.errorText!!, actionText = getString(R.string.button_retry)
            ) {
                viewModel.retryConnection {
                    viewModel.initRequest(contentType, detailType, id, listId, region)
                }
            }
        }
    }
}