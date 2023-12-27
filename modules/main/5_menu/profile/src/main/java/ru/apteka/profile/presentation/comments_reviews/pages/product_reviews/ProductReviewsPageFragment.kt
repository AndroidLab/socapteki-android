package ru.apteka.profile.presentation.comments_reviews.pages.product_reviews

import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import ru.apteka.components.ui.BaseFragment
import ru.apteka.components.ui.delegate_adapter.CompositeDelegateAdapter
import ru.apteka.profile.R
import ru.apteka.profile.databinding.CommentsProductReviewsPageBinding
import ru.apteka.profile.presentation.comments_reviews.CommentsAdapter


/**
 * Представляет фрагмент "Страница отзывов о товаре".
 */
@AndroidEntryPoint
class ProductReviewsPageFragment :
    BaseFragment<ProductReviewsPageViewModel, CommentsProductReviewsPageBinding>() {
    override val viewModel: ProductReviewsPageViewModel by viewModels()
    override val layoutId: Int = R.layout.comments_product_reviews_page

    private val commentsAdapter by lazy {
        CompositeDelegateAdapter(
            CommentsAdapter(this)
        )
    }

    override fun onViewBindingInflated(binding: CommentsProductReviewsPageBinding) {
        binding.viewModel = viewModel
        binding.rvProductReviews.adapter = commentsAdapter

        viewModel.comments.observe(viewLifecycleOwner) {
            commentsAdapter.swapData(it)
        }
    }

    companion object {
        fun newInstance() = ProductReviewsPageFragment()
    }

}