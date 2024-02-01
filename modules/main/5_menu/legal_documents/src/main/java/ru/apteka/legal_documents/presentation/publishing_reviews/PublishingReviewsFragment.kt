package ru.apteka.legal_documents.presentation.publishing_reviews

import dagger.hilt.android.AndroidEntryPoint
import ru.apteka.components.data.services.navigation_manager.NavigationManager
import ru.apteka.components.ui.BaseFragment
import ru.apteka.legal_documents.R
import ru.apteka.legal_documents.databinding.PublishingReviewsFragmentBinding
import javax.inject.Inject

/**
 * Представляет фрагмент "Правила публикации отзывов на сайте social-apteka.ru".
 */
@AndroidEntryPoint
class PublishingReviewsFragment : BaseFragment<Nothing, PublishingReviewsFragmentBinding>() {
    override val layoutId: Int = R.layout.publishing_reviews_fragment

    @Inject
    lateinit var navigationManager: NavigationManager

    override fun onViewBindingInflated(binding: PublishingReviewsFragmentBinding) {
    }

    override fun onResume() {
        super.onResume()
        navigationManager.onBottomAppBarShowed(false)
        binding.publishingReviewsToolbar.apply {
            toolbar.setNavigationIcon(ru.apteka.components.R.drawable.ic_navigation_back)
            tvToolbarTitle.text = getString(R.string.legal_documents_medicinal_products_title)
            toolbar.setNavigationOnClickListener {
                navigationManager.currentBottomNavControllerLiveData.value!!.popBackStack()
            }
        }
    }
}
