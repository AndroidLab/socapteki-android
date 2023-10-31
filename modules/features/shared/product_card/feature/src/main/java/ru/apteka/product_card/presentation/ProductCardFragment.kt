package ru.apteka.product_card.presentation

import android.R.layout
import android.text.TextUtils
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.RatingBar
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.os.bundleOf
import androidx.core.view.children
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.request.transition.ViewPropertyTransition.Animator
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.internal.CollapsingTextHelper
import dagger.hilt.android.AndroidEntryPoint
import ru.apteka.components.data.models.ProductModel
import ru.apteka.components.data.utils.dp
import ru.apteka.components.data.utils.getProductCardViewAdapter
import ru.apteka.components.data.utils.navigateWithAnim
import ru.apteka.components.data.utils.playAnimation
import ru.apteka.components.data.utils.skeletons
import ru.apteka.components.data.utils.visibleIf
import ru.apteka.components.ui.BaseFragment
import ru.apteka.components.ui.delegate_adapter.CompositeDelegateAdapter
import ru.apteka.product_card.R
import ru.apteka.pharmacies_map_api.R as pharmaciesMapApiR
import ru.apteka.product_card.databinding.ProductCardFragmentBinding
import ru.apteka.product_card_api.api.PRODUCT_CARD_ARGUMENT_PRODUCT
import java.lang.reflect.Field
import kotlin.math.abs
import ru.apteka.components.R as ComponentsR


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
        binding.pricePageSimilarProducts.rv.adapter = similarProductsAdapter
        binding.pricePageWithProduct.rv.adapter = withProductProductsDayAdapter



        binding.productCardAptekiLocation1.setOnClickListener {
            viewModel.navigationManager.generalNavController.navigateWithAnim(pharmaciesMapApiR.id.pharmacies_map_graph)
        }

        binding.productCardAptekiLocation2.setOnClickListener {
            viewModel.navigationManager.generalNavController.navigateWithAnim(pharmaciesMapApiR.id.pharmacies_map_graph)
        }

        binding.productCardDescTab.tab.setOnClickListener {
            binding.nsvProductCard.smoothScrollTo(
                binding.productCardDesc.x.toInt(),
                binding.productCardDesc.y.toInt() - binding.productCardDescTab.tab.height
            )
        }

        binding.productCardInstructionsTab.tab.setOnClickListener {
            binding.nsvProductCard.smoothScrollTo(
                binding.productCardInstructions.x.toInt(),
                binding.productCardInstructions.y.toInt() - binding.productCardInstructionsTab.tab.height
            )
        }

        binding.productCardCommentsTab.tab.setOnClickListener {
            binding.nsvProductCard.smoothScrollTo(
                binding.productCardComments.x.toInt(),
                binding.productCardComments.y.toInt() - binding.productCardCommentsTab.tab.height
            )
        }


        binding.pricePageReleaseForm.setOnClickListener {

        }

        viewModel.product.observe(viewLifecycleOwner) {
            if (it != null) {
                priceImageAdapter.swapData(
                    it.images
                )
            }
        }

        viewModel.similarProducts.observe(viewLifecycleOwner) {
            similarProductsAdapter.swapData(
                it.ifEmpty { skeletons }
            )
        }

        viewModel.withProductProducts.observe(viewLifecycleOwner) {
            withProductProductsDayAdapter.swapData(
                it.ifEmpty { skeletons }
            )
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

    override fun onResume() {
        super.onResume()
        binding.productCardAptekiLocation2.addOnHideAnimationListener(object :
            android.animation.Animator.AnimatorListener {
            override fun onAnimationStart(p0: android.animation.Animator) {}
            override fun onAnimationEnd(p0: android.animation.Animator) {
                binding.productCardAptekiLocation1.visibility = View.VISIBLE
                binding.lavProductCardAptekiLocation1.playAnimation(0f, .5f)
            }

            override fun onAnimationCancel(p0: android.animation.Animator) {}
            override fun onAnimationRepeat(p0: android.animation.Animator) {}
        })

        binding.productCardAptekiLocation2.addOnShowAnimationListener(object :
            android.animation.Animator.AnimatorListener {
            override fun onAnimationStart(p0: android.animation.Animator) {
                binding.productCardAptekiLocation1.visibility = View.GONE
            }

            override fun onAnimationEnd(p0: android.animation.Animator) {}
            override fun onAnimationCancel(p0: android.animation.Animator) {}
            override fun onAnimationRepeat(p0: android.animation.Animator) {}
        })

        binding.toolbar.apply {
            binding.toolbar.setNavigationIcon(ComponentsR.drawable.ic_navigation_back)
            binding.toolbar.subtitle = _args.product.desc
            var maxOffset = 0
            binding.appbar.addOnOffsetChangedListener { appBarLayout, verticalOffset ->
                if (maxOffset == 0) {
                    maxOffset = binding.appbar.height - binding.toolbar.height
                }
                val absVerticalOffset = abs(verticalOffset)
                val rvAlpha = 1 - absVerticalOffset * 1f / maxOffset
                binding.rvProductCardImages.alpha = rvAlpha
                binding.tvProductCardDesc.x =
                    16.dp + (absVerticalOffset * 50f / maxOffset).dp.toFloat()
                binding.rvProductCardImages.visibleIf(rvAlpha > 0)
                binding.productCardAptekiLocation1.visibleIf(binding.productCardAptekiLocation2.visibility == View.INVISIBLE)
                binding.productCardToolBarSeparator.visibleIf(rvAlpha == 0f)

                if (maxOffset - absVerticalOffset < maxOffset / 5) {
                    binding.productCardTabs.visibility = View.VISIBLE
                    val tabsAlpha = 1 - (maxOffset - absVerticalOffset) * 1f / (maxOffset / 5)
                    binding.productCardTabs.alpha = tabsAlpha
                } else {
                    binding.productCardTabs.visibility = View.GONE
                }
            }
            binding.toolbar.setNavigationOnClickListener {
                viewModel.navigationManager.generalNavController.popBackStack()
            }
        }
    }
}