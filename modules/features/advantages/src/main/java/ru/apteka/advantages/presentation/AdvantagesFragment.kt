package ru.apteka.advantages.presentation

import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import ru.apteka.advantages.R
import ru.apteka.advantages.databinding.AdvantagesFragmentBinding
import ru.apteka.components.ui.BaseFragment


/**
 * Представляет фрагмент "".
 */
@AndroidEntryPoint
class AdvantagesFragment : BaseFragment<AdvantagesViewModel, AdvantagesFragmentBinding>() {

    override val viewModel: AdvantagesViewModel by viewModels()
    override val layoutId: Int = R.layout.advantages_fragment

    override fun onViewBindingInflated(binding: AdvantagesFragmentBinding) {

    }
}