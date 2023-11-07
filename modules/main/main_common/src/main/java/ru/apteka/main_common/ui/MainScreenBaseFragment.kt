package ru.apteka.main_common.ui

import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import ru.apteka.components.databinding.ToolbarBinding
import ru.apteka.components.databinding.ToolbarMenuBinding
import ru.apteka.components.ui.BaseFragment
import ru.apteka.main_common.R
import ru.apteka.components.R as ComponentsR


/**
 * Представляет фрагмент "Базовый экран для главных экранов".
 */
abstract class MainScreenBaseFragment<TViewModel : MainScreenBaseViewModel, TDataBinding : ViewDataBinding> :
    BaseFragment<TViewModel, TDataBinding>() {

    /**
     * Заполняет базовый тоолбар.
     */
    fun fillMainScreensToolbar(
        toolbarBinding: ToolbarBinding,
        onSearchClick: (() -> Unit)? = null
    ) {
        toolbarBinding.apply {
            toolbar.setNavigationIcon(R.drawable.ic_navigation_menu)
            toolbar.setNavigationOnClickListener {
                viewModel!!.navigationManager.showAppMenu()
            }
            toolbarBinding.toolbarCustomViewContainer.removeAllViews()
            toolbarCustomViewContainer.addView(
                DataBindingUtil.inflate<ToolbarMenuBinding>(
                    layoutInflater,
                    ComponentsR.layout.toolbar_menu,
                    null,
                    false
                ).apply {
                    ivMenuSearch.setOnClickListener {
                        onSearchClick?.invoke()
                    }
                }.root
            )
        }
    }

}