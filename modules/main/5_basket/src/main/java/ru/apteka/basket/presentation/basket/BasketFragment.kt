package ru.apteka.basket.presentation.basket

import androidx.appcompat.content.res.AppCompatResources
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import ru.apteka.components.ui.BaseFragment
import ru.apteka.basket.R
import ru.apteka.components.R as ComponentsR
import ru.apteka.basket.databinding.BasketFragmentBinding
import ru.apteka.components.databinding.ToolbarMenuBinding

/**
 * Представляет фрагмент "Корзина".
 */
@AndroidEntryPoint
class BasketFragment : BaseFragment<BasketViewModel, BasketFragmentBinding>() {
    override val viewModel: BasketViewModel by viewModels()
    override val layoutId: Int = R.layout.basket_fragment

    override fun onViewBindingInflated(binding: BasketFragmentBinding) {

    }

    override fun onResume() {
        super.onResume()
        binding.basketToolbar.apply {
            toolbar.setNavigationIcon(ComponentsR.drawable.ic_navigation_menu)
            toolbar.setLogo(ComponentsR.drawable.logo)
            toolbar.setNavigationOnClickListener {
                viewModel.navigationManager.drawerLayout.open()
            }
            toolbarCustomViewContainer.removeAllViews()
            toolbarCustomViewContainer.addView(
                DataBindingUtil.inflate<ToolbarMenuBinding>(
                    layoutInflater,
                    ru.apteka.components.R.layout.toolbar_menu,
                    null,
                    false
                ).apply {
                    ivMenuSearch.setOnClickListener {

                    }
                    ivMenuDoctor.setOnClickListener {

                    }
                    ivMenuAuth.setOnClickListener {
                        viewModel.navigationManager.navigateToAuthActivity()
                    }
                }.root
            )
        }

    }

}