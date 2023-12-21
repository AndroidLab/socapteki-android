package ru.apteka.favorites.presentation.stocks.presentation.stocks

import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import ru.apteka.components.ui.BaseFragment
import ru.apteka.stocks.R
import ru.apteka.stocks.databinding.StocksFragmentBinding


/**
 * Представляет фрагмент "Акции".
 */
@AndroidEntryPoint
class StocksFragment : BaseFragment<StocksViewModel, StocksFragmentBinding>() {
    override val viewModel: StocksViewModel by viewModels()
    override val layoutId: Int = R.layout.stocks_fragment

    override fun onViewBindingInflated(binding: StocksFragmentBinding) {
        binding.viewModel = viewModel

    }


    override fun onResume() {
        super.onResume()
        viewModel.navigationManager.onBottomAppBarShowed(true)
        binding.favoritesToolbar.tvToolbarTitle.text = getString(R.string.stocks_title)
    }
}