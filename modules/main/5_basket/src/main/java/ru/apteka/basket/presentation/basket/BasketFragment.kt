package ru.apteka.basket.presentation.basket

import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import ru.apteka.basket.R
import ru.apteka.main_common.R as MainCommonR
import ru.apteka.basket.databinding.BasketFragmentBinding
import ru.apteka.components.data.utils.Skeleton
import ru.apteka.components.data.utils.dp
import ru.apteka.components.ui.adapters.ProductCardViewAdapter
import ru.apteka.components.ui.delegate_adapter.CompositeDelegateAdapter
import ru.apteka.components.ui.delegate_adapter.SkeletonAdapter
import ru.apteka.main_common.ui.MainScreenBaseFragment

/**
 * Представляет фрагмент "Корзина".
 */
@AndroidEntryPoint
class BasketFragment : MainScreenBaseFragment<BasketViewModel, BasketFragmentBinding>() {
    override val viewModel: BasketViewModel by viewModels()
    override val layoutId: Int = R.layout.basket_fragment

    private val skeletons = listOf(Skeleton(), Skeleton(), Skeleton())

    private val productsBasketAdapter by lazy {
        CompositeDelegateAdapter(
            ProductsBasketAdapter(
                this
            )
        )
    }

    private val productsWatchedRecentlyAdapter by lazy {
        CompositeDelegateAdapter(
            ProductCardViewAdapter(
                this,
                ::onProductsCardClick
            ),
            SkeletonAdapter(166.dp, 340.dp)
        )
    }

    override fun onViewBindingInflated(binding: BasketFragmentBinding) {
        binding.viewModel = viewModel
        binding.basketToPromotion.setOnClickListener {

        }

        binding.basketToCatalog.setOnClickListener {
            viewModel.navigationManager.bottomNavBar.selectedItemId =
                MainCommonR.id.catalog_graph
        }

        binding.basketWatchedRecently.header.btn.setOnClickListener {

        }

        binding.rvBasket.adapter = productsBasketAdapter
        viewModel.basketService.products.observe(viewLifecycleOwner) {
            productsBasketAdapter.swapData(it)
        }

        binding.basketWatchedRecently.rv.adapter = productsWatchedRecentlyAdapter
        viewModel.productsWatchedRecently.observe(viewLifecycleOwner) {
            productsWatchedRecentlyAdapter.swapData(
                it.ifEmpty { skeletons }
            )
        }

    }

    private fun onProductsCardClick() {

    }

    override fun onResume() {
        super.onResume()
        fillMainScreensToolbar(binding.basketToolbar)
        binding.basketToolbar.toolbar.title = getString(R.string.basket_title)
    }

}