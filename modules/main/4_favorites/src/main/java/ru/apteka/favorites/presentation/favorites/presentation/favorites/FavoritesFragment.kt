package ru.apteka.favorites.presentation.favorites.presentation.favorites

import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import ru.apteka.components.ui.composite_delegate_adapter.CompositeDelegateAdapter
import ru.apteka.components.ui.BaseFragment
import ru.apteka.favorites.R
import ru.apteka.favorites.databinding.FavoritesFragmentBinding


/**
 * Представляет фрагмент "Избранное".
 */
@AndroidEntryPoint
class FavoritesFragment : BaseFragment<FavoritesViewModel, FavoritesFragmentBinding>() {
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
}