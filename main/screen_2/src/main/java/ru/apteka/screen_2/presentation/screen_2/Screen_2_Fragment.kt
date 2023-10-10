package ru.apteka.screen_2.presentation.screen_2

import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.apteka.common.ui.BaseFragment
import ru.apteka.screen_2.R
import ru.apteka.screen_2.databinding.Screen2FragmentBinding

/**
 * Screen_2_fragment
 */
@AndroidEntryPoint
class Screen_2_Fragment : BaseFragment<Nothing, Screen2FragmentBinding>() {

    override val layoutId: Int = R.layout.screen_2_fragment

    override fun onViewBindingInflated(binding: Screen2FragmentBinding) {
        binding.btnGoToScreen21.setOnClickListener {
            findNavController().navigate(
                Screen_2_FragmentDirections.toScreen21()
            )
        }
    }
}