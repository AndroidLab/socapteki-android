package ru.apteka.home.presentation.comments_reviews.pages.feedback

import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import ru.apteka.components.ui.BaseFragment
import ru.apteka.home.R
import ru.apteka.home.databinding.CommentsReviewsFeedbackPageBinding
import ru.apteka.home.databinding.CommentsReviewsReviewsPageBinding
import ru.apteka.home.presentation.comments_reviews.pages.comments.CommentsPageFragment


/**
 * Представляет фрагмент "Обратная связь".
 */
@AndroidEntryPoint
class FeedbackFragment :
    BaseFragment<FeedbackPageViewModel, CommentsReviewsFeedbackPageBinding>() {
    override val viewModel: FeedbackPageViewModel by viewModels()
    override val layoutId: Int = R.layout.comments_reviews_feedback_page


    override fun onViewBindingInflated(binding: CommentsReviewsFeedbackPageBinding) {
        binding.viewModel = viewModel


    }

    companion object {
        fun newInstance() = FeedbackFragment()
    }

}