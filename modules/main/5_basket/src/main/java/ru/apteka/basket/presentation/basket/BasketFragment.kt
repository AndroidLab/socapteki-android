package ru.apteka.basket.presentation.basket

import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import ru.apteka.basket.R
import ru.apteka.basket.databinding.BasketFragmentBinding
import ru.apteka.components.data.models.ProductModel
import ru.apteka.components.data.utils.dp
import ru.apteka.components.data.utils.getProductCardViewAdapter
import ru.apteka.components.data.utils.navigateWithAnim
import ru.apteka.components.data.utils.skeletons
import ru.apteka.components.ui.adapters.ProductCardViewAdapter
import ru.apteka.components.ui.delegate_adapter.CompositeDelegateAdapter
import ru.apteka.components.ui.delegate_adapter.SkeletonAdapter
import ru.apteka.main_common.ui.MainScreenBaseFragment
import ru.apteka.product_card_api.api.PRODUCT_CARD_ARGUMENT_PRODUCT
import ru.apteka.main_common.R as MainCommonR
import ru.apteka.product_card_api.R as ProductCardApiR

/**
 * Представляет фрагмент "Корзина".
 */
@AndroidEntryPoint
class BasketFragment : MainScreenBaseFragment<BasketViewModel, BasketFragmentBinding>() {
    override val viewModel: BasketViewModel by viewModels()
    override val layoutId: Int = R.layout.basket_fragment

    private val productsBasketAdapter by lazy {
        CompositeDelegateAdapter(
            ProductsBasketAdapter(
                this
            )
        )
    }

    private val productsWatchedRecentlyAdapter by lazy {
        getProductCardViewAdapter(
            this,
            ::onProductsCardClick,
            false
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

    private fun onProductsCardClick(product: ProductModel) {
        viewModel.navigationManager.generalNavController.navigateWithAnim(
            ProductCardApiR.id.product_card_graph, bundleOf(
                PRODUCT_CARD_ARGUMENT_PRODUCT to product
            )
        )
    }

    override fun onResume() {
        super.onResume()
        fillMainScreensToolbar(binding.basketToolbar)
        binding.basketToolbar.toolbar.title = getString(R.string.basket_title)
    }

}