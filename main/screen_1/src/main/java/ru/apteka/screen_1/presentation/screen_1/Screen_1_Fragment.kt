package ru.apteka.screen_1.presentation.screen_1

import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import ru.apteka.common.ui.BaseFragment
import ru.apteka.screen_1.R
import ru.apteka.screen_1.databinding.Screen1FragmentBinding
import ru.tinkoff.decoro.MaskImpl
import ru.tinkoff.decoro.parser.UnderscoreDigitSlotsParser
import ru.tinkoff.decoro.slots.PredefinedSlots
import ru.tinkoff.decoro.slots.Slot
import ru.tinkoff.decoro.watchers.MaskFormatWatcher

/**
 * Screen_1_fragment
 */
@AndroidEntryPoint
class Screen_1_Fragment : BaseFragment<Screen_1_ViewModel, Screen1FragmentBinding>() {
    override val viewModel: Screen_1_ViewModel by viewModels()
    override val layoutId: Int = R.layout.screen_1_fragment

    override fun onViewBindingInflated(binding: Screen1FragmentBinding) {
        binding.viewModel = viewModel


    }
}