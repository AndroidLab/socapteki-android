package ru.apteka.home.presentation.comments_reviews.pages.reviews

import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import ru.apteka.components.ui.BaseFragment
import ru.apteka.home.R
import ru.apteka.home.databinding.CommentsReviewsReviewsPageBinding
import ru.apteka.home.presentation.comments_reviews.pages.comments.CommentsPageFragment


/**
 * Представляет фрагмент "Страница отзывов".
 */
@AndroidEntryPoint
class ReviewsPageFragment :
    BaseFragment<ReviewsPageViewModel, CommentsReviewsReviewsPageBinding>() {
    override val viewModel: ReviewsPageViewModel by viewModels()
    override val layoutId: Int = R.layout.comments_reviews_reviews_page


    override fun onViewBindingInflated(binding: CommentsReviewsReviewsPageBinding) {
        binding.viewModel = viewModel


    }

    companion object {
        fun newInstance() = CommentsPageFragment()
    }

}