package ru.apteka.profile.presentation.comments_reviews.pages.reviews_online_pharmacy

import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import ru.apteka.components.ui.BaseFragment
import ru.apteka.components.ui.delegate_adapter.CompositeDelegateAdapter
import ru.apteka.profile.R
import ru.apteka.profile.databinding.CommentsReviewsOnlinePharmacyPageBinding
import ru.apteka.profile.presentation.comments_reviews.CommentsAdapter


/**
 * Представляет фрагмент "Страница отзывы об интернет-аптеке".
 */
@AndroidEntryPoint
class ReviewsOnlinePharmacyPageFragment :
    BaseFragment<ReviewsOnlinePharmacyViewModel, CommentsReviewsOnlinePharmacyPageBinding>() {
    override val viewModel: ReviewsOnlinePharmacyViewModel by viewModels()
    override val layoutId: Int = R.layout.comments_reviews_online_pharmacy_page

    private val commentsAdapter by lazy {
        CompositeDelegateAdapter(
            CommentsAdapter(this)
        )
    }

    override fun onViewBindingInflated(binding: CommentsReviewsOnlinePharmacyPageBinding) {
        binding.viewModel = viewModel

        binding.rvReviewsOnlinePharmacy.adapter = commentsAdapter

        viewModel.comments.observe(viewLifecycleOwner) {
            commentsAdapter.swapData(it)
        }
    }

    companion object {
        fun newInstance() = ReviewsOnlinePharmacyPageFragment()
    }

}