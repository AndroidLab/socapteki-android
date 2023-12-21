package ru.apteka.basket.presentation.basket

import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import ru.apteka.basket.R
import ru.apteka.basket.databinding.BasketFragmentBinding
import ru.apteka.basket.databinding.BasketMenuBinding
import ru.apteka.components.data.models.ProductModel
import ru.apteka.components.data.utils.getProductCardViewAdapter
import ru.apteka.components.data.utils.navigateWithAnim
import ru.apteka.components.ui.BaseFragment
import ru.apteka.making_order_api.api.MAKING_ORDER_ARGUMENT_PRODUCT
import ru.apteka.product_card_api.api.PRODUCT_CARD_ARGUMENT_PRODUCT
import ru.apteka.making_order_api.R as MakingOrderApiR
import ru.apteka.product_card_api.R as ProductCardApiR
import ru.apteka.components.R as ComponentsR

/**
 * Представляет фрагмент "Корзина".
 */
@AndroidEntryPoint
class BasketFragment : BaseFragment<BasketViewModel, BasketFragmentBinding>() {
    override val viewModel: BasketViewModel by viewModels()
    override val layoutId: Int = R.layout.basket_fragment

    private val productsWatchedRecentlyAdapter by lazy {
        getProductCardViewAdapter(
            this,
            ::onProductsCardClick
        )
    }

    override fun onViewBindingInflated(binding: BasketFragmentBinding) {
        binding.viewModel = viewModel

        binding.basketToCatalog.setOnClickListener {
            viewModel.navigationManager.bottomAppBarModel.onItemSelected(ComponentsR.id.catalog_graph)
        }

        binding.basketToStocks.setOnClickListener {
            viewModel.navigationManager.bottomAppBarModel.onItemSelected(ComponentsR.id.stocks_graph)
        }

        binding.basketWatchedRecently.horizontalListBtn.setOnClickListener {

        }


        binding.basketWatchedRecently.rv.adapter = productsWatchedRecentlyAdapter
        viewModel.productsWatchedRecently.observe(viewLifecycleOwner) {
            productsWatchedRecentlyAdapter.swapData(it)
        }

        binding.basketMakingOrder.setOnClickListener {
            viewModel.navigationManager.generalNavController.navigateWithAnim(
                MakingOrderApiR.id.making_order_graph, bundleOf(
                    MAKING_ORDER_ARGUMENT_PRODUCT to viewModel.basketService.products.value!!.map { it.productCard.product }.toTypedArray()
                )
            )
        }

        viewModel.basketService.totalCount.observe(viewLifecycleOwner) {
            binding.basketToolbar.toolbarCustomViewContainer.removeAllViews()
            if (it > 0) {
                binding.basketToolbar.toolbarCustomViewContainer.addView(
                    DataBindingUtil.inflate<BasketMenuBinding>(
                        layoutInflater,
                        R.layout.basket_menu,
                        null,
                        false
                    ).apply {
                        ivMenuShare.setOnClickListener {

                        }
                    }.root
                )
            }
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
        viewModel.navigationManager.onBottomAppBarShowed(true)
        binding.basketToolbar.tvToolbarTitle.text = getString(R.string.basket_title)
    }

}