package ru.apteka.product_card.presentation.product_card_manufacturer_program

import android.animation.Animator
import android.animation.ValueAnimator
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import ru.apteka.components.data.models.ProductModel
import ru.apteka.components.data.utils.launchMain
import ru.apteka.components.data.utils.navigateWithAnim
import ru.apteka.components.ui.BaseFragment
import ru.apteka.components.ui.adapters.ProductCardViewAdapter
import ru.apteka.components.ui.delegate_adapter.CompositeDelegateAdapter
import ru.apteka.product_card.R
import ru.apteka.product_card.databinding.ProductCardManufacturerProgramFragmentBinding
import ru.apteka.product_card_api.api.PRODUCT_CARD_ARGUMENT_PRODUCT
import ru.apteka.components.R as ComponentsR

/**
 * Представляет фрагмент "Карточка товара, программы производителя".
 */
@AndroidEntryPoint
class ProductCardManufacturerProgramFragment :
    BaseFragment<ProductCardManufacturerProgramViewModel, ProductCardManufacturerProgramFragmentBinding>() {
    override val viewModel: ProductCardManufacturerProgramViewModel by viewModels()
    override val layoutId: Int = R.layout.product_card_manufacturer_program_fragment

    private val productsAdapter by lazy {
        CompositeDelegateAdapter(
            ProductCardViewAdapter(
                this,
                ::onProductsCardClick,
                false
            )
        )
    }

    override fun onViewBindingInflated(binding: ProductCardManufacturerProgramFragmentBinding) {
        binding.viewModel = viewModel

        var llCollapsedHeight = 0
        binding.tvManufacturerProgramDescReadCompletely.setOnClickListener {
            if (binding.tvManufacturerProgramDescReadCompletely.text == getString(ComponentsR.string.read_completely)) {
                llCollapsedHeight = binding.llManufacturerProgramDesc.height
                binding.llManufacturerProgramDesc.layoutParams.height = llCollapsedHeight
                binding.tvManufacturerProgramDescReadCompletely.text =
                    getString(ComponentsR.string.hide)
                binding.tvManufacturerProgramDesc.maxLines = Int.MAX_VALUE
                lifecycleScope.launchMain {
                    delay(100)
                    ValueAnimator.ofInt(
                        binding.llManufacturerProgramDesc.height,
                        binding.tvManufacturerProgramDesc.layout.height
                        //llCollapsedHeight,
                        //tvCommentText.layout.height
                    ).apply {
                        addUpdateListener { valueAnimator ->
                            val lp = binding.llManufacturerProgramDesc.layoutParams
                            binding.llManufacturerProgramDesc.layoutParams =
                                lp.apply { height = valueAnimator.animatedValue as Int }
                        }
                        duration = 350
                        start()
                    }
                }
            } else {
                lifecycleScope.launchMain {
                    delay(100)
                    ValueAnimator.ofInt(
                        binding.llManufacturerProgramDesc.height,
                        llCollapsedHeight
                    ).apply {
                        addUpdateListener { valueAnimator ->
                            val lp = binding.llManufacturerProgramDesc.layoutParams
                            binding.llManufacturerProgramDesc.layoutParams =
                                lp.apply { height = valueAnimator.animatedValue as Int }
                        }
                        addListener(
                            object : Animator.AnimatorListener {
                                override fun onAnimationStart(animation: Animator) {}
                                override fun onAnimationEnd(animation: Animator) {
                                    binding.tvManufacturerProgramDescReadCompletely.text =
                                        getString(ComponentsR.string.read_completely)
                                    binding.tvManufacturerProgramDesc.maxLines = 5
                                }

                                override fun onAnimationCancel(animation: Animator) {}
                                override fun onAnimationRepeat(animation: Animator) {}
                            }
                        )
                        duration = 350
                        start()
                    }
                }
            }
        }

        binding.rvManufacturerProgramProducts.adapter = productsAdapter

        viewModel.products.observe(viewLifecycleOwner) {
            productsAdapter.swapData(
                it
            )
        }
    }

    private fun onProductsCardClick(product: ProductModel) {
        viewModel.navigationManager.generalNavController.navigateWithAnim(
            ru.apteka.product_card_api.R.id.product_card_graph,
            bundleOf(
                PRODUCT_CARD_ARGUMENT_PRODUCT to product
            )
        )
    }

    override fun onResume() {
        super.onResume()
        binding.writeReviewToolbar.apply {
            toolbar.setNavigationIcon(ComponentsR.drawable.ic_navigation_back)
            tvToolbarTitle.text = "ADM PROTEXIN LTD"
            toolbar.setNavigationOnClickListener {
                viewModel.navigationManager.generalNavController.popBackStack()
            }
        }
    }
}