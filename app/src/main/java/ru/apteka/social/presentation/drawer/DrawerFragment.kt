package ru.apteka.social.presentation.drawer

import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import ru.apteka.common.ui.BaseFragment
import ru.apteka.social.R
import ru.apteka.social.databinding.DrawerFragmentBinding

/**
 * Представляет фрагмент 'Drawer'.
 */
@AndroidEntryPoint
class DrawerFragment : BaseFragment<DrawerViewModel, DrawerFragmentBinding>() {
    override val viewModel by viewModels<DrawerViewModel>()
    override val layoutId: Int = R.layout.drawer_fragment


    override fun onViewBindingInflated(binding: DrawerFragmentBinding) {


    }

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
    }

}