package ru.apteka.product_card.presentation.product_card

import android.animation.ValueAnimator
import android.graphics.Color
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.view.doOnLayout
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint
import ru.apteka.components.data.models.ProductModel
import ru.apteka.components.data.services.message_notice_service.models.BodyContentModel
import ru.apteka.components.data.services.message_notice_service.models.CommonDialogModel
import ru.apteka.components.data.services.message_notice_service.models.DialogButtonModel
import ru.apteka.components.data.services.message_notice_service.models.DialogModel
import ru.apteka.components.data.services.message_notice_service.showCommonDialog
import ru.apteka.components.data.utils.dp
import ru.apteka.components.data.utils.navigateWithAnim
import ru.apteka.components.data.utils.screenHeight
import ru.apteka.components.data.utils.setVisibleWithInteractionEnabled
import ru.apteka.components.data.utils.visibleIf
import ru.apteka.components.ui.BaseFragment
import ru.apteka.components.ui.adapters.ProductCardViewAdapter
import ru.apteka.components.ui.delegate_adapter.CompositeDelegateAdapter
import ru.apteka.listing_api.api.LISTING_ARGUMENT
import ru.apteka.pharmacies_map_api.api.PHARMACIES_MAP_TYPE_INTERACTION
import ru.apteka.pharmacies_map_api.api.TypeInteraction
import ru.apteka.product_card.R
import ru.apteka.product_card.databinding.ProductCardFragmentBinding
import ru.apteka.product_card.databinding.ProductCardManufacturerProgramDialogBinding
import ru.apteka.product_card_api.api.PRODUCT_CARD_ARGUMENT_PRODUCT
import kotlin.math.abs
import ru.apteka.components.R as ComponentsR
import ru.apteka.pharmacies_map_api.R as PharmaciesMapApiR
import ru.apteka.listing_api.R as ListingApiR


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

    private val analoguesProductsAdapter by lazy {
        CompositeDelegateAdapter(
            ProductCardViewAdapter(
                this,
                ::onProductsCardClick,
                true
            )
        )
    }

    /*private val withProductProductsDayAdapter by lazy {
        getProductCardViewAdapter(
            this,
            ::onProductsCardClick,
        )
    }*/

    override fun onViewBindingInflated(binding: ProductCardFragmentBinding) {
        viewModel.product.value = _args.product
        binding.viewModel = viewModel

        binding.rvProductCardImages.adapter = priceImageAdapter
        binding.rvProductCardAnalogues.adapter = analoguesProductsAdapter
        //binding.pricePageWithProduct.rv.adapter = withProductProductsDayAdapter

        var isScrollSelect = false
        binding.productCardTabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (!isScrollSelect) {
                    when (tab?.position) {
                        0 -> binding.nsvProductCard.smoothScrollTo(
                            binding.productCardPrice.x.toInt(),
                            binding.productCardPrice.y.toInt() - binding.productCardTabs.height
                        )

                        1 -> binding.nsvProductCard.smoothScrollTo(
                            binding.productCardDesc.x.toInt(),
                            binding.productCardDesc.y.toInt() - binding.productCardTabs.height
                        )

                        2 -> binding.nsvProductCard.smoothScrollTo(
                            binding.clProductCardAnalogues.x.toInt(),
                            binding.clProductCardAnalogues.y.toInt() - binding.productCardTabs.height
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
            if (i2 > binding.productCardPrice.y && binding.productCardTabs.visibility == View.INVISIBLE) {
                tabsAnimatorShow.start()
            }

            if (i2 < binding.productCardDesc.y && binding.productCardTabs.visibility == View.VISIBLE) {
                binding.productCardTabs.translationY = -binding.productCardTabs.height / 2f
                binding.productCardTabs.setVisibleWithInteractionEnabled(false)
            }


            val scrollLayoutOffsetTriggerPoint = i2 + screenHeight / 3
            if (scrollLayoutOffsetTriggerPoint > binding.productCardPrice.y && scrollLayoutOffsetTriggerPoint < binding.productCardDesc.y) {
                if (!binding.productCardTabs.getTabAt(0)!!.isSelected) {
                    isScrollSelect = true
                    binding.productCardTabs.getTabAt(0)?.select()
                }
            }

            if (scrollLayoutOffsetTriggerPoint > binding.productCardDesc.y && scrollLayoutOffsetTriggerPoint < binding.clProductCardAnalogues.y) {
                if (!binding.productCardTabs.getTabAt(1)!!.isSelected) {
                    isScrollSelect = true
                    binding.productCardTabs.getTabAt(1)?.select()
                }
            }

            if (scrollLayoutOffsetTriggerPoint > binding.clProductCardAnalogues.y && scrollLayoutOffsetTriggerPoint < binding.productCardInstructions.y) {
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

        binding.productCardPharmaciesLocation1.setOnClickListener {
            viewModel.navigationManager.generalNavController.navigateWithAnim(
                PharmaciesMapApiR.id.pharmacies_map_graph, bundleOf(
                    PHARMACIES_MAP_TYPE_INTERACTION to TypeInteraction.NAVIGATION
                )
            )
        }
        binding.productCardPharmaciesLocation2.setOnClickListener {
            viewModel.navigationManager.generalNavController.navigateWithAnim(
                PharmaciesMapApiR.id.pharmacies_map_graph, bundleOf(
                    PHARMACIES_MAP_TYPE_INTERACTION to TypeInteraction.NAVIGATION
                )
            )
        }

        binding.productCardReleaseForm.setOnClickListener {
            viewModel.navigationManager.generalNavController.navigateWithAnim(
                ListingApiR.id.listing_graph, bundleOf(
                    LISTING_ARGUMENT to "Форма выпуска"
                )
            )
        }

        binding.productCardPharmaciesInMap.setOnClickListener {
            viewModel.navigationManager.generalNavController.navigateWithAnim(
                PharmaciesMapApiR.id.pharmacies_map_graph, bundleOf(
                    PHARMACIES_MAP_TYPE_INTERACTION to TypeInteraction.NAVIGATION
                )
            )
        }

        binding.llProductCardDoubleBonuses.setOnClickListener {
            showCommonDialog(
                commonDialogModel = CommonDialogModel(
                    fragmentManager = parentFragmentManager,
                    dialogModel = DialogModel(
                        bodyContent = BodyContentModel(
                            layoutId = R.layout.product_card_double_bonuses_dialog,
                        ),
                        buttonCancel = DialogButtonModel(
                            text = ComponentsR.string.cancel
                        ),
                        buttonConfirm = DialogButtonModel(
                            text = ComponentsR.string.more_detailed
                        ) {

                        }
                    )
                )
            )
        }

        binding.llProductCardManufacturerProgram.setOnClickListener {
            showCommonDialog(
                commonDialogModel = CommonDialogModel(
                    fragmentManager = parentFragmentManager,
                    dialogModel = DialogModel(
                        bodyContent = BodyContentModel(
                            layoutId = R.layout.product_card_manufacturer_program_dialog,
                        ) { dialog, binding ->
                            binding as ProductCardManufacturerProgramDialogBinding
                            binding.mbProductCardManufacturerProgram.setOnClickListener {
                                dialog.dismiss()
                            }
                        }
                    )
                )
            )
        }

        binding.tvProductCardAnaloguesAll.setOnClickListener {
            viewModel.navigationManager.generalNavController.navigateWithAnim(
                ListingApiR.id.listing_graph, bundleOf(
                    LISTING_ARGUMENT to "Аналоги лекарства"
                )
            )
        }


        binding.withProduct1.productCardItem.setOnClickListener {
            viewModel.navigationManager.generalNavController.navigateWithAnim(
                ru.apteka.product_card_api.R.id.product_card_graph, bundleOf(
                    PRODUCT_CARD_ARGUMENT_PRODUCT to viewModel.withProducts.value!![0].product
                )
            )
        }

        binding.withProduct2.productCardItem.setOnClickListener {
            viewModel.navigationManager.generalNavController.navigateWithAnim(
                ru.apteka.product_card_api.R.id.product_card_graph, bundleOf(
                    PRODUCT_CARD_ARGUMENT_PRODUCT to viewModel.withProducts.value!![1].product
                )
            )
        }

        binding.tvWithProductAll.setOnClickListener {
            viewModel.navigationManager.generalNavController.navigateWithAnim(
                ListingApiR.id.listing_graph, bundleOf(
                    LISTING_ARGUMENT to "С этим товаром покупают"
                )
            )
        }

        binding.tvProductCardSendComment.setOnClickListener {
            binding.btnProductCardSendComment.performClick()
        }

        binding.btnProductCardSendComment.setOnClickListener {
            viewModel.navigationManager.generalNavController.navigateWithAnim(
                ProductCardFragmentDirections.toProductCardWriteReviewFragment(_args.product)
            )
        }

        viewModel.product.observe(viewLifecycleOwner) {
            if (it != null) {
                priceImageAdapter.swapData(
                    it.images
                )
            }
        }

        viewModel.analoguesProducts.observe(viewLifecycleOwner) {
            analoguesProductsAdapter.swapData(it)
        }

        /*viewModel.withProductProducts.observe(viewLifecycleOwner) {
            withProductProductsDayAdapter.swapData(it)
        }*/

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
        viewModel.navigationManager.onBottomAppBarShowed(false)
        binding.productCardTabs.doOnLayout {
            it.translationY = -binding.productCardTabs.height.toFloat()
        }


        binding.toolbar.apply {
            binding.toolbar.setNavigationIcon(ComponentsR.drawable.ic_navigation_back)
            binding.toolbar.subtitle = _args.product.title
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
                    binding.productCardPharmaciesLocation2.show()
                    val fabOffsetTranslationX = 1 - absVerticalOffset * 1f / maxOffsetForRecycler!!
                    binding.flProductCardAptekiLocation.translationX =
                        fabStartTranslationX!! * fabOffsetTranslationX
                } else {
                    binding.productCardPharmaciesLocation2.hide()
                }

            }
            binding.toolbar.setNavigationOnClickListener {
                viewModel.navigationManager.generalNavController.popBackStack()
            }
        }
    }
}