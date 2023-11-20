package ru.apteka.reviews.presentation

import android.util.Log
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import ru.apteka.components.ui.BaseFragment
import ru.apteka.components.ui.delegate_adapter.CompositeDelegateAdapter
import ru.apteka.reviews.R
import ru.apteka.reviews.databinding.ReviewsFragmentBinding


/**
 * Представляет фрагмент "Отзывы".
 */
@AndroidEntryPoint
class ReviewsFragment : BaseFragment<ReviewsViewModel, ReviewsFragmentBinding>() {
    override val viewModel: ReviewsViewModel by viewModels()
    override val layoutId: Int = R.layout.reviews_fragment

    private val reviewsCardAdapter by lazy {
        CompositeDelegateAdapter(
            ReviewsCardAdapter()
        )
    }

    override fun onViewBindingInflated(binding: ReviewsFragmentBinding) {
        binding.viewModel = viewModel
        binding.rvReviews.adapter = reviewsCardAdapter


        binding.mbReviews.setOnClickListener {

        }

        viewModel.reviews.observe(viewLifecycleOwner) {
            reviewsCardAdapter.swapData(it)
        }
    }

    override fun onResume() {
        super.onResume()
        binding.reviewsToolbar.apply {
            toolbar.setNavigationIcon(ru.apteka.components.R.drawable.ic_navigation_back)
            tvToolbarTitle.text = getString(R.string.reviews_title)
            toolbar.setNavigationOnClickListener {
                viewModel.navigationManager.generalNavController.popBackStack()
            }
        }
    }
}