package ru.apteka.favorites.presentation.favorites.presentation.favorites

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import androidx.core.view.children
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import dagger.hilt.android.AndroidEntryPoint
import ru.apteka.common.data.composite_delegate_adapter.CompositeDelegateAdapter
import ru.apteka.common.data.utils.MarginItemDecoration
import ru.apteka.common.data.utils.dp
import ru.apteka.common.ui.BaseFragment
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
            FavoriteCardViewAdapter(this)
        )
    }

    override fun onViewBindingInflated(binding: FavoritesFragmentBinding) {
        binding.viewModel = viewModel
        binding.rv.adapter = adapter
        binding.rv.addItemDecoration(MarginItemDecoration(horizontal = 0, vertical = 6.dp))

        viewModel.favorites.observe(viewLifecycleOwner) {
            adapter.swapData(it)
        }
    }
}