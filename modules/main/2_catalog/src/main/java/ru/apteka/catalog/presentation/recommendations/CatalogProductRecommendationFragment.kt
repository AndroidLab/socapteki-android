package ru.apteka.catalog.presentation.recommendations


import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import ru.apteka.catalog.R
import ru.apteka.catalog.databinding.CatalogProductRecommendationFragmentBinding
import ru.apteka.components.data.services.navigation_manager.NavigationManager
import ru.apteka.components.ui.BaseFragment
import javax.inject.Inject
import ru.apteka.components.R as ComponentsR


/**
 * Представляет фрагмент "Рекоммендации к продукту".
 */
@AndroidEntryPoint
class CatalogProductRecommendationFragment :
    BaseFragment<Nothing, CatalogProductRecommendationFragmentBinding>() {

    override val layoutId: Int = R.layout.catalog_product_recommendation_fragment

    @Inject
    lateinit var navigationManager: NavigationManager

    val _args: CatalogProductRecommendationFragmentArgs by navArgs()

    override fun onViewBindingInflated(binding: CatalogProductRecommendationFragmentBinding) {
        binding.title = _args.title
        binding.desk = _args.desk
    }

    override fun onResume() {
        super.onResume()
        binding.catalogProductRecommendationToolbar.apply {
            toolbar.setNavigationIcon(ComponentsR.drawable.ic_navigation_back)
            toolbar.setNavigationOnClickListener {
                navigationManager.currentBottomNavControllerLiveData.value!!.popBackStack()
            }
        }
    }

}