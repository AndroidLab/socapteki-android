package ru.apteka.favorites.presentation.favorites.presentation.favorites

import androidx.appcompat.content.res.AppCompatResources
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import ru.apteka.components.databinding.ToolbarMenuBinding
import ru.apteka.components.ui.BaseFragment
import ru.apteka.components.ui.delegate_adapter.CompositeDelegateAdapter
import ru.apteka.favorites.R
import ru.apteka.favorites.databinding.FavoritesFragmentBinding
import ru.apteka.components.R as ComponentsR


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

    override fun onResume() {
        super.onResume()
        binding.favoritesToolbar.apply {
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