package ru.apteka.legal_documents.presentation.sale_goods

import dagger.hilt.android.AndroidEntryPoint
import ru.apteka.components.data.services.navigation_manager.NavigationManager
import ru.apteka.components.ui.BaseFragment
import ru.apteka.licenses.R
import ru.apteka.licenses.databinding.SaleGoodsFragmentBinding
import javax.inject.Inject


/**
 * Представляет фрагмент "Условия продажи товаров".
 */
@AndroidEntryPoint
class SaleGoodsFragment : BaseFragment<Nothing, SaleGoodsFragmentBinding>() {
    override val layoutId: Int = R.layout.sale_goods_fragment

    @Inject
    lateinit var navigationManager: NavigationManager

    override fun onViewBindingInflated(binding: SaleGoodsFragmentBinding) {

    }


    override fun onResume() {
        super.onResume()
        navigationManager.onBottomAppBarShowed(false)
        binding.saleGoodsToolbar.apply {
            toolbar.setNavigationIcon(ru.apteka.components.R.drawable.ic_navigation_back)
            tvToolbarTitle.text = getString(R.string.legal_documents_sale_goods_title)
            toolbar.setNavigationOnClickListener {
                navigationManager.currentBottomNavControllerLiveData.value!!.popBackStack()
            }
        }
    }
}