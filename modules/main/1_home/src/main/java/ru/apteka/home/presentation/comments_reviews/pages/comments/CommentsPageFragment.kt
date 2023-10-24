package ru.apteka.home.presentation.comments_reviews.pages.comments

import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import ru.apteka.components.ui.BaseFragment
import ru.apteka.home.R
import ru.apteka.home.databinding.CommentsReviewsCommentsPageBinding


/**
 * Представляет фрагмент "Страница комментариев".
 */
@AndroidEntryPoint
class CommentsPageFragment :
    BaseFragment<CommentsPageViewModel, CommentsReviewsCommentsPageBinding>() {
    override val viewModel: CommentsPageViewModel by viewModels()
    override val layoutId: Int = R.layout.comments_reviews_comments_page


    override fun onViewBindingInflated(binding: CommentsReviewsCommentsPageBinding) {
        binding.viewModel = viewModel


    }

    companion object {
        fun newInstance() = CommentsPageFragment()
    }

}