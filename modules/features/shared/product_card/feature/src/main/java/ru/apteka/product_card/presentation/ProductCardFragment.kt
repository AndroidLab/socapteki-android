package ru.apteka.product_card.presentation

import android.animation.ValueAnimator
import android.graphics.Color
import android.util.Log
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.view.doOnLayout
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint
import ru.apteka.components.data.models.ProductModel
import ru.apteka.components.data.utils.dp
import ru.apteka.components.data.utils.getProductCardViewAdapter
import ru.apteka.components.data.utils.navigateWithAnim
import ru.apteka.components.data.utils.screenHeight
import ru.apteka.components.data.utils.setVisibleWithInteractionEnabled
import ru.apteka.components.data.utils.visibleIf
import ru.apteka.components.ui.BaseFragment
import ru.apteka.components.ui.delegate_adapter.CompositeDelegateAdapter
import ru.apteka.product_card.R
import ru.apteka.product_card.databinding.ProductCardFragmentBinding
import ru.apteka.product_card_api.api.PRODUCT_CARD_ARGUMENT_PRODUCT
import kotlin.math.abs
import ru.apteka.components.R as ComponentsR
import ru.apteka.pharmacies_map_api.R as pharmaciesMapApiR


/**
 * Представляет фрагмент "Карточка товара".
 */
@AndroidEntryPoint
class ProductCardFragment : BaseFragment<ProductCardViewModel, ProductCardFragmentBinding>() {
    override val viewModel: ProductCardViewModel by viewModels()
    override val layoutId: Int = R.layout.product_card_fragment

    private val _args: ProductCardFragmentArgs by navArgs()

    private val priceImageAdapter by lazy {
        CompositeDelegateAdapter(
            PriceImageAdapter()
        )
    }

    private val similarProductsAdapter by lazy {
        getProductCardViewAdapter(
            this,
            ::onProductsCardClick,
        )
    }

    private val withProductProductsDayAdapter by lazy {
        getProductCardViewAdapter(
            this,
            ::onProductsCardClick,
        )
    }

    override fun onViewBindingInflated(binding: ProductCardFragmentBinding) {
        binding.viewModel = viewModel

        binding.rvProductCardImages.adapter = priceImageAdapter
        binding.productCardAnnalogs.rv.adapter = similarProductsAdapter
        binding.pricePageWithProduct.rv.adapter = withProductProductsDayAdapter

        var isScrollSelect = false
        binding.productCardTabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (!isScrollSelect) {
                    when (tab?.position) {
                        0 -> binding.nsvProductCard.smoothScrollTo(
                            binding.productCardDesc.x.toInt(),
                            binding.productCardDesc.y.toInt() - binding.productCardTabs.height
                        )

                        1 -> binding.nsvProductCard.smoothScrollTo(
                            binding.productCardPrice.x.toInt(),
                            binding.productCardPrice.y.toInt() - binding.productCardTabs.height
                        )

                        2 -> binding.nsvProductCard.smoothScrollTo(
                            binding.productCardAnnalogs.horizontalListBlock.x.toInt(),
                            binding.productCardAnnalogs.horizontalListBlock.y.toInt() - binding.productCardTabs.height
                        )

                        3 -> binding.nsvProductCard.smoothScrollTo(
                            binding.productCardInstructions.x.toInt(),
                            binding.productCardInstructions.y.toInt() - binding.productCardTabs.height
                        )

                        4 -> binding.nsvProductCard.smoothScrollTo(
                            binding.productCardComments.x.toInt(),
                            binding.productCardComments.y.toInt() - binding.productCardTabs.height
                        )
                    }
                }
                isScrollSelect = false
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })

        binding.nsvProductCard.setOnScrollChangeListener { view, i, i2, i3, i4 ->
            if (i2 > binding.productCardDesc1.y && binding.productCardTabs.visibility == View.INVISIBLE) {
                tabsAnimatorShow.start()
            }
            if (i2 < binding.productCardReleaseForm.y && binding.productCardTabs.visibility == View.VISIBLE) {
                binding.productCardTabs.translationY = -binding.productCardTabs.height / 2f
                binding.productCardTabs.setVisibleWithInteractionEnabled(false)
            }


            val scrollLayoutOffsetTriggerPoint = i2 + screenHeight / 3
            if (scrollLayoutOffsetTriggerPoint > binding.productCardDesc.y && scrollLayoutOffsetTriggerPoint < binding.productCardPrice.y) {
                if (!binding.productCardTabs.getTabAt(0)!!.isSelected) {
                    isScrollSelect = true
                    binding.productCardTabs.getTabAt(0)?.select()
                }
            }

            if (scrollLayoutOffsetTriggerPoint > binding.productCardPrice.y && scrollLayoutOffsetTriggerPoint < binding.productCardAnnalogs.horizontalListBlock.y) {
                if (!binding.productCardTabs.getTabAt(1)!!.isSelected) {
                    isScrollSelect = true
                    binding.productCardTabs.getTabAt(1)?.select()
                }
            }

            if (scrollLayoutOffsetTriggerPoint > binding.productCardAnnalogs.horizontalListBlock.y && scrollLayoutOffsetTriggerPoint < binding.productCardInstructions.y) {
                if (!binding.productCardTabs.getTabAt(2)!!.isSelected) {
                    isScrollSelect = true
                    binding.productCardTabs.getTabAt(2)?.select()
                }
            }

            if (scrollLayoutOffsetTriggerPoint > binding.productCardInstructions.y && scrollLayoutOffsetTriggerPoint < binding.productCardComments.y) {
                if (!binding.productCardTabs.getTabAt(3)!!.isSelected) {
                    isScrollSelect = true
                    binding.productCardTabs.getTabAt(3)?.select()
                }
            }

            if (scrollLayoutOffsetTriggerPoint > binding.productCardComments.y) {
                if (!binding.productCardTabs.getTabAt(4)!!.isSelected) {
                    isScrollSelect = true
                    binding.productCardTabs.getTabAt(4)?.select()
                }
            }

        }

        binding.productCardAptekiLocation1.setOnClickListener {
            viewModel.navigationManager.generalNavController.navigateWithAnim(pharmaciesMapApiR.id.pharmacies_map_graph)
        }
        binding.productCardAptekiLocation2.setOnClickListener {
            viewModel.navigationManager.generalNavController.navigateWithAnim(pharmaciesMapApiR.id.pharmacies_map_graph)
        }

        binding.productCardReleaseForm.setOnClickListener {
            Log.d("myL", "productCardReleaseForm")
        }

        binding.productCardPharmaciesInMap.setOnClickListener {
            Log.d("myL", "productCardPharmaciesInMap")
        }


        binding.tvProductCardSendComment.setOnClickListener {
            binding.btnProductCardSendComment.performClick()
        }

        binding.btnProductCardSendComment.setOnClickListener {

        }

        viewModel.product.observe(viewLifecycleOwner) {
            if (it != null) {
                priceImageAdapter.swapData(
                    it.images
                )
            }
        }

        viewModel.similarProducts.observe(viewLifecycleOwner) {
            similarProductsAdapter.swapData(it)
        }

        viewModel.withProductProducts.observe(viewLifecycleOwner) {
            withProductProductsDayAdapter.swapData(it)
        }

    }

    private fun onProductsCardClick(product: ProductModel) {
        viewModel.navigationManager.generalNavController.popBackStack()
        viewModel.navigationManager.generalNavController.navigateWithAnim(
            ru.apteka.product_card_api.R.id.product_card_graph, bundleOf(
                PRODUCT_CARD_ARGUMENT_PRODUCT to product
            )
        )
    }

    /**
     * Перенаправляет к инструкциям.
     */
    val goToSimilarProducts: (View) -> Unit = {

    }

    /**
     * Перенаправляет к аптекам.
     */
    val goToAvailabilityApteki: (View) -> Unit = {

    }

    /**
     * Перенаправляет к отзывам.
     */
    val goToReviews: (View) -> Unit = {

    }

    private val tabsAnimatorShow: ValueAnimator
        get() {
            binding.productCardTabs.setVisibleWithInteractionEnabled(true)
            return ValueAnimator.ofFloat(
                -binding.productCardTabs.height.toFloat(),
                binding.productCardTabs.height.toFloat() - 1.dp
            ).apply {
                addUpdateListener { valueAnimator ->
                    binding.productCardTabs.translationY = valueAnimator.animatedValue as Float
                }
                duration = 350
            }
        }

    override fun onResume() {
        super.onResume()
        binding.productCardTabs.doOnLayout {
            it.translationY = -binding.productCardTabs.height.toFloat()
        }


        binding.toolbar.apply {
            binding.toolbar.setNavigationIcon(ComponentsR.drawable.ic_navigation_back)
            binding.toolbar.subtitle = _args.product.desc
            var maxOffsetForRecycler: Int? = null
            var maxOffsetForTitle: Int? = null
            var maxOffsetForFab: Int? = null
            var descStartTranslationY: Float? = null
            var fabStartTranslationX: Float? = null
            binding.appbar.addOnOffsetChangedListener { appBarLayout, verticalOffset ->
                if (maxOffsetForRecycler == null) {
                    maxOffsetForRecycler = binding.appbar.height - binding.toolbar.height * 2
                }
                if (maxOffsetForTitle == null) {
                    maxOffsetForTitle = binding.appbar.height - binding.toolbar.height
                }
                if (maxOffsetForFab == null) {
                    maxOffsetForFab = binding.appbar.height - binding.toolbar.height
                }
                if (descStartTranslationY == null) {
                    descStartTranslationY = binding.tvProductCardDesc.translationY
                }
                if (fabStartTranslationX == null) {
                    fabStartTranslationX = binding.flProductCardAptekiLocation.translationX
                }

                val absVerticalOffset = abs(verticalOffset)
                val rvAlpha = 1 - absVerticalOffset * 1f / maxOffsetForRecycler!!
                binding.rvProductCardImages.alpha = if (rvAlpha < 0f) 0f else rvAlpha
                binding.rvProductCardImages.visibleIf(rvAlpha > 0)

                if (maxOffsetForTitle!! - absVerticalOffset < binding.toolbar.height) {
                    binding.tvProductCardDesc.visibility = View.VISIBLE
                    val descOffsetPercent =
                        (maxOffsetForTitle!! - absVerticalOffset) * 1f / binding.toolbar.height

                    binding.tvProductCardDesc.alpha = 1 - descOffsetPercent
                    binding.tvProductCardDesc.translationY =
                        descStartTranslationY!! * descOffsetPercent
                    binding.flProductCardAptekiLocation.translationY = (-50).dp * descOffsetPercent
                } else {
                    binding.tvProductCardDesc.visibility = View.INVISIBLE
                    binding.flProductCardAptekiLocation.translationY = (-50).dp.toFloat()
                }

                if (binding.rvProductCardImages.visibility == View.GONE) {
                    binding.toolbar.setBackgroundResource(ComponentsR.color.white)
                } else {
                    binding.toolbar.setBackgroundColor(Color.TRANSPARENT)
                }

                if (maxOffsetForFab!! - absVerticalOffset > binding.toolbar.height) {
                    binding.productCardAptekiLocation2.show()
                    val fabOffsetTranslationX = 1 - absVerticalOffset * 1f / maxOffsetForRecycler!!
                    binding.flProductCardAptekiLocation.translationX =
                        fabStartTranslationX!! * fabOffsetTranslationX
                } else {
                    binding.productCardAptekiLocation2.hide()
                }

            }
            binding.toolbar.setNavigationOnClickListener {
                viewModel.navigationManager.generalNavController.popBackStack()
            }
        }
    }
}