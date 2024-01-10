package ru.apteka.social.presentation.auth.auth_confirm

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import ru.apteka.components.data.utils.setSoftInputModeResize
import ru.apteka.components.ui.BaseFragment
import ru.apteka.social.R
import ru.apteka.components.R as ComponentsR
import ru.apteka.social.databinding.AuthConfirmFragmentBinding


/**
 * Представляет фрагмент "Подтверждение авторизации".
 */
@AndroidEntryPoint
class AuthConfirmFragment : BaseFragment<AuthConfirmViewModel, AuthConfirmFragmentBinding>() {
    override val viewModel: AuthConfirmViewModel by viewModels()
    override val layoutId: Int = R.layout.auth_confirm_fragment

    private val args: AuthConfirmFragmentArgs by navArgs()

    override fun onViewBindingInflated(binding: AuthConfirmFragmentBinding) {
        viewModel.phoneNumber = args.phone
        binding.viewModel = viewModel

        viewModel.isNavigationToMain.observe(viewLifecycleOwner) {
            mActivity.finish()
        }
    }

    override fun onResume() {
        super.onResume()
        setSoftInputModeResize()
        binding.authConfirmToolbar.apply {
            toolbar.setNavigationIcon(ComponentsR.drawable.ic_navigation_back)
            tvToolbarTitle.text = getString(R.string.auth_confirm_title)
            toolbar.setNavigationOnClickListener {
                nController.popBackStack()
            }
        }
    }
}