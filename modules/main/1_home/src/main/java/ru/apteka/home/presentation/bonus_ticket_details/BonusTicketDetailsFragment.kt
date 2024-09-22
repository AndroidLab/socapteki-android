package ru.apteka.home.presentation.bonus_ticket_details

import android.animation.Animator
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import androidx.core.os.bundleOf
import androidx.core.widget.doAfterTextChanged
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import ru.apteka.components.data.models.ProductModel
import ru.apteka.components.data.services.message_notice_service.models.BottomSheetModel
import ru.apteka.components.data.utils.equalsWithDeviation
import ru.apteka.components.data.utils.launchMain
import ru.apteka.components.data.utils.navigateWithAnim
import ru.apteka.components.data.utils.playAnimation
import ru.apteka.components.databinding.ProductsSortBinding
import ru.apteka.components.databinding.SearchToolbarViewBinding
import ru.apteka.components.ui.BaseFragment
import ru.apteka.components.ui.adapters.ProductCardViewAdapter
import ru.apteka.components.ui.delegate_adapter.CompositeDelegateAdapter
import ru.apteka.home.R
import ru.apteka.home.databinding.BonusHistoryFragmentBinding
import ru.apteka.home.databinding.BonusTicketDetailsFragmentBinding
import ru.apteka.home.presentation.bonus_history.BonusHistoryViewModel
import ru.apteka.product_card_api.api.PRODUCT_CARD_ARGUMENT_PRODUCT
import ru.apteka.components.R as ComponentsR


/**
 * Представляет фрагмент "Детали тикета".
 */
@SuppressLint("ResourceType")
@AndroidEntryPoint
class BonusTicketDetailsFragment : BaseFragment<BonusTicketDetailsViewModel, BonusTicketDetailsFragmentBinding>() {

    override val viewModel: BonusTicketDetailsViewModel by viewModels()
    override val layoutId: Int = R.layout.bonus_ticket_details_fragment

    private val args: BonusTicketDetailsFragmentArgs by navArgs()

    private val productsAdapter by lazy {
        CompositeDelegateAdapter(
            ProductCardViewAdapter(
                this,
                ::onProductsCardClick,
                false
            )
        )
    }

    private val bonusNavController by lazy {
        requireActivity().findNavController(R.id.bonus_nav_host)
    }

    override fun onViewBindingInflated(binding: BonusTicketDetailsFragmentBinding) {
        binding.viewModel = viewModel
        binding.ticketModel = args.ticket

        var llCollapsedHeight = 0
        binding.tvBonusTicketDescReadCompletely.setOnClickListener {
            if (binding.tvBonusTicketDescReadCompletely.text == getString(ComponentsR.string.read_completely)) {
                llCollapsedHeight = binding.llBonusTicketDesc.height
                binding.llBonusTicketDesc.layoutParams.height = llCollapsedHeight
                binding.tvBonusTicketDescReadCompletely.text =
                    getString(ComponentsR.string.hide)
                binding.tvBonusTicketDesc.maxLines = Int.MAX_VALUE
                lifecycleScope.launchMain {
                    delay(100)
                    ValueAnimator.ofInt(
                        binding.llBonusTicketDesc.height,
                        binding.tvBonusTicketDesc.layout.height
                        //llCollapsedHeight,
                        //tvCommentText.layout.height
                    ).apply {
                        addUpdateListener { valueAnimator ->
                            val lp = binding.llBonusTicketDesc.layoutParams
                            binding.llBonusTicketDesc.layoutParams =
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
                        binding.llBonusTicketDesc.height,
                        llCollapsedHeight
                    ).apply {
                        addUpdateListener { valueAnimator ->
                            val lp = binding.llBonusTicketDesc.layoutParams
                            binding.llBonusTicketDesc.layoutParams =
                                lp.apply { height = valueAnimator.animatedValue as Int }
                        }
                        addListener(
                            object : Animator.AnimatorListener {
                                override fun onAnimationStart(animation: Animator) {}
                                override fun onAnimationEnd(animation: Animator) {
                                    binding.tvBonusTicketDescReadCompletely.text =
                                        getString(ComponentsR.string.read_completely)
                                    binding.tvBonusTicketDesc.maxLines = 5
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

        binding.ticketDetailsProductsSort.setOnClickListener {
            viewModel.bottomSheetService.show(
                BottomSheetModel(
                    binding = ProductsSortBinding.inflate(layoutInflater, null, false)
                        .apply {
                            lifecycleOwner = viewLifecycleOwner
                            listingProductFilterModel = viewModel.sortModel
                        }
                )
            )
        }

        binding.rvBonusDetailsProducts.adapter = productsAdapter
        viewModel.products.observe(viewLifecycleOwner) {
            productsAdapter.swapData(
                it
            )
        }

        binding.tvBonusDetailsProductsMode.setOnClickListener {

        }

        binding.mbTicketActivate.setOnClickListener {

        }
    }

    private fun onProductsCardClick(product: ProductModel) {
        viewModel.navigationManager.generalNavController.navigateWithAnim(
            ru.apteka.product_card_api.R.id.product_card_graph, bundleOf(
                PRODUCT_CARD_ARGUMENT_PRODUCT to product
            )
        )
    }

    override fun onResume() {
        super.onResume()
        binding.bonusTicketDetailsToolbar.apply {
            toolbar.setNavigationIcon(ComponentsR.drawable.ic_navigation_back)
            toolbar.setNavigationOnClickListener {
                bonusNavController.popBackStack()
            }
            toolbarCustomViewContainer.removeAllViews()
            toolbarCustomViewContainer.apply {
                //layoutParams = Toolbar.LayoutParams(Toolbar.LayoutParams.MATCH_PARENT, Toolbar.LayoutParams.MATCH_PARENT)
                addView(
                    DataBindingUtil.inflate<SearchToolbarViewBinding>(
                        layoutInflater,
                        ComponentsR.layout.search_toolbar_view,
                        null,
                        false
                    ).apply {
                        lifecycleOwner = viewLifecycleOwner
                        isLoading = viewModel.isLoading
                        viewModel.isLoading.observe(viewLifecycleOwner) {
                            hint = "Поиск товаров по акции"
                        }
                        searchToolbarSearch.setOnClickListener {
                            etToolBarSearch.setText("")
                        }
                        val deviation = 0.01f
                        val startProgress = 0.1f
                        val middleProgress = 0.25f
                        val endProgress = 0.48f
                        etToolBarSearch.doAfterTextChanged {
                            /*viewModel.cityQuery.value = it.toString()
                            val progress = searchToolbarSearch.progress
                            if (it.isNullOrEmpty()) {
                                if (progress.equalsWithDeviation(middleProgress, deviation)) {
                                    searchToolbarSearch.playAnimation(0.4f, endProgress)
                                }
                            } else {
                                if (progress.equalsWithDeviation(
                                        startProgress,
                                        deviation
                                    ) || progress.equalsWithDeviation(endProgress, deviation)
                                ) {
                                    searchToolbarSearch.playAnimation(
                                        startProgress,
                                        middleProgress
                                    )
                                }
                            }*/
                        }
                    }.root
                )
            }
        }
    }
}
