package ru.apteka.product_card.presentation.product_card_write_review

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import ru.apteka.components.data.utils.setSoftInputModeAdjustPan
import ru.apteka.components.ui.BaseFragment
import ru.apteka.product_card.R
import ru.apteka.product_card.databinding.WriteReviewFragmentBinding


/**
 * Представляет фрагмент "Карточка товара, написать отзыв".
 */
@AndroidEntryPoint
class WriteReviewFragment : BaseFragment<WriteReviewViewModel, WriteReviewFragmentBinding>() {
    override val viewModel: WriteReviewViewModel by viewModels()
    override val layoutId: Int = R.layout.write_review_fragment

    private val args: WriteReviewFragmentArgs by navArgs()

    override fun onViewBindingInflated(binding: WriteReviewFragmentBinding) {
        viewModel.product.value = args.product
        binding.viewModel = viewModel

    }


    override fun onResume() {
        super.onResume()
        setSoftInputModeAdjustPan()
        viewModel.navigationManager.onBottomAppBarShowed(false)
        binding.writeReviewToolbar.apply {
            toolbar.setNavigationIcon(ru.apteka.components.R.drawable.ic_navigation_close)
            tvToolbarTitle.text = getString(R.string.write_review_title)
            toolbar.setNavigationOnClickListener {
                viewModel.navigationManager.generalNavController.popBackStack()
            }
        }

    }
}