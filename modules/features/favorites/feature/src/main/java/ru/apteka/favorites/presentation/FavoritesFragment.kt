package ru.apteka.favorites.presentation

import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import ru.apteka.components.data.utils.NAVIGATE_REQUEST_KEY_TO_CATALOG
import ru.apteka.components.ui.BaseFragment
import ru.apteka.components.ui.delegate_adapter.CompositeDelegateAdapter
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

        binding.mbFavorites.setOnClickListener {
            if (viewModel.favoriteService.products.value!!.isEmpty()) {
                setFragmentResult(NAVIGATE_REQUEST_KEY_TO_CATALOG, bundleOf())
                viewModel.navigationManager.generalNavController.popBackStack()
            } else {

            }
        }

        viewModel.favoriteService.products.observe(viewLifecycleOwner) {
            adapter.swapData(it)
        }
    }

    private fun onAdvertCardClick() {

    }

    override fun onResume() {
        super.onResume()
        binding.favoritesToolbar.apply {
            toolbar.setNavigationIcon(ru.apteka.components.R.drawable.ic_navigation_back)
            tvToolbarTitle.text = getString(R.string.favorites_title)
            toolbar.setNavigationOnClickListener {
                viewModel.navigationManager.generalNavController.popBackStack()
            }
        }
    }
}