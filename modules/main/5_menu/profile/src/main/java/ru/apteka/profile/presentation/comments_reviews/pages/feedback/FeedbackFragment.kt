package ru.apteka.profile.presentation.comments_reviews.pages.feedback

import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import ru.apteka.components.ui.BaseFragment
import ru.apteka.components.ui.delegate_adapter.CompositeDelegateAdapter
import ru.apteka.profile.R
import ru.apteka.profile.databinding.CommentsFeedbackPageBinding
import ru.apteka.profile.presentation.comments_reviews.CommentsAdapter


/**
 * Представляет фрагмент "Обратная связь".
 */
@AndroidEntryPoint
class FeedbackFragment :
    BaseFragment<FeedbackPageViewModel, CommentsFeedbackPageBinding>() {
    override val viewModel: FeedbackPageViewModel by viewModels()
    override val layoutId: Int = R.layout.comments_feedback_page

    private val feedbacksAdapter by lazy {
        CompositeDelegateAdapter(
            FeedbacksAdapter(this)
        )
    }

    override fun onViewBindingInflated(binding: CommentsFeedbackPageBinding) {
        binding.viewModel = viewModel

        binding.rvFeedbacks.adapter = feedbacksAdapter

        viewModel.feedbacks.observe(viewLifecycleOwner) {
            feedbacksAdapter.swapData(it)
        }
    }

    companion object {
        fun newInstance() = FeedbackFragment()
    }

}