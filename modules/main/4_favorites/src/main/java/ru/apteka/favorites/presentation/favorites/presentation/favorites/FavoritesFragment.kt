package ru.apteka.favorites.presentation.favorites.presentation.favorites

import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import ru.apteka.components.ui.delegate_adapter.CompositeDelegateAdapter
import ru.apteka.favorites.R
import ru.apteka.favorites.databinding.FavoritesFragmentBinding
import ru.apteka.main_common.ui.MainScreenBaseFragment


/**
 * Представляет фрагмент "Избранное".
 */
@AndroidEntryPoint
class FavoritesFragment : MainScreenBaseFragment<FavoritesViewModel, FavoritesFragmentBinding>() {
    override val viewModel: FavoritesViewModel by viewModels()
    override val layoutId: Int = R.layout.favorites_fragment

    private val adapter by lazy {
        CompositeDelegateAdapter(
            FavoriteCardViewAdapter(
                this,
                ::onAdvertCardClick
            )
        )
    }

    override fun onViewBindingInflated(binding: FavoritesFragmentBinding) {
        binding.viewModel = viewModel
        binding.rvFavorites.adapter = adapter

        viewModel.favorites.observe(viewLifecycleOwner) {
            adapter.swapData(it)
        }
    }

    private fun onAdvertCardClick() {

    }

    override fun onResume() {
        super.onResume()
        fillMainScreensToolbar(binding.favoritesToolbar)
        binding.favoritesToolbar.toolbar.title = getString(R.string.favorites_title)
    }
}