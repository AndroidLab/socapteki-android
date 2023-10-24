package ru.apteka.components.ui

import androidx.databinding.ViewDataBinding
import ru.apteka.components.databinding.ToolbarBinding


/**
 * Представляет фрагмент "Базовый экран для фича экранов".
 */
abstract class FeatureBaseFragment<TViewModel : BaseViewModel, TDataBinding : ViewDataBinding> :
    BaseFragment<TViewModel, TDataBinding>() {


    /**
     * Заполняет базовый тоолбар.
     */
    fun fillFeatureScreensToolbar(
        toolbarBinding: ToolbarBinding
    ) {
        toolbarBinding.apply {
            toolbar.setNavigationIcon(ru.apteka.components.R.drawable.ic_navigation_back)
            toolbar.setNavigationOnClickListener {
                viewModel!!.navigationManager.generalNavController.popBackStack()
            }
        }
    }


}