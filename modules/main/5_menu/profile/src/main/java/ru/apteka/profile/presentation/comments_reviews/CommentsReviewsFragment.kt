package ru.apteka.profile.presentation.comments_reviews

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import ru.apteka.components.ui.BaseFragment
import ru.apteka.components.ui.adapters.PagerAdapter
import ru.apteka.profile.R
import ru.apteka.profile.databinding.CommentsReviewsFragmentBinding
import ru.apteka.profile.presentation.comments_reviews.pages.product_reviews.ProductReviewsPageFragment
import ru.apteka.profile.presentation.comments_reviews.pages.feedback.FeedbackFragment
import ru.apteka.profile.presentation.comments_reviews.pages.reviews_online_pharmacy.ReviewsOnlinePharmacyPageFragment
import ru.apteka.components.R as ComponentsR


/**
 * Представляет фрагмент "Комментариии и подписки".
 */
@AndroidEntryPoint
class CommentsReviewsFragment :
    BaseFragment<CommentsReviewsViewModel, CommentsReviewsFragmentBinding>() {
    override val viewModel: CommentsReviewsViewModel by viewModels()
    override val layoutId: Int = R.layout.comments_reviews_fragment


    override fun onViewBindingInflated(binding: CommentsReviewsFragmentBinding) {
        binding.viewModel = viewModel

        binding.commentsReviewsFragmentPager.offscreenPageLimit = 1
        binding.commentsReviewsFragmentPager.adapter = PagerAdapter(
            requireActivity(),
            arrayListOf(
                ProductReviewsPageFragment.newInstance() as Fragment,
                ReviewsOnlinePharmacyPageFragment.newInstance() as Fragment,
                FeedbackFragment.newInstance() as Fragment,
            )
        )
        TabLayoutMediator(binding.tabLayout, binding.commentsReviewsFragmentPager) { tab, pos ->
            tab.text = listOf(
                getString(R.string.comments_product_reviews_page),
                getString(R.string.comments_reviews_online_pharmacy_page),
                getString(R.string.comments_feedback_page)
            )[pos]
        }.attach()
    }

    override fun onResume() {
        super.onResume()
        viewModel.navigationManager.onBottomAppBarShowed(false)
        binding.commentsReviewsToolbar.apply {
            toolbar.setNavigationIcon(ComponentsR.drawable.ic_navigation_back)
            tvToolbarTitle.text = getString(R.string.comments_reviews_title)
            toolbar.setNavigationOnClickListener {
                viewModel.navigationManager.currentBottomNavControllerLiveData.value!!.popBackStack()
            }
        }
    }


}