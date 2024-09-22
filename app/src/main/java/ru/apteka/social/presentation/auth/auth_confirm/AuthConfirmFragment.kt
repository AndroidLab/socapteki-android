package ru.apteka.social.presentation.auth.auth_confirm

import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import ru.apteka.components.data.models.DeliveryAddressModel
import ru.apteka.components.data.services.message_notice_service.models.BottomSheetModel
import ru.apteka.components.data.services.message_notice_service.models.CommonBottomSheetModel
import ru.apteka.components.data.services.message_notice_service.showBottomSheet
import ru.apteka.components.data.utils.navigateWithAnim
import ru.apteka.components.data.utils.setExtraMargin
import ru.apteka.components.data.utils.setSoftInputModeResize
import ru.apteka.components.ui.BaseFragment
import ru.apteka.making_order.databinding.MakingOrderAddressBinding
import ru.apteka.pharmacies_map.databinding.PharmacyItemHolderBinding
import ru.apteka.product_card.presentation.product_card.ProductCardFragmentDirections
import ru.apteka.social.R
import ru.apteka.components.R as ComponentsR
import ru.apteka.social.databinding.AuthConfirmFragmentBinding
import ru.apteka.social.databinding.AuthMailSheetBinding


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

        var isFinishWhenCancel = true
        viewModel.isNavigationToMain.observe(viewLifecycleOwner) {
            showBottomSheet(
                commonBottomSheet = CommonBottomSheetModel(
                    fragmentManager = parentFragmentManager,
                    bottomSheetModel = BottomSheetModel(
                        layoutId = R.layout.auth_mail_sheet,
                        onLayoutInflate = { binding, dialog, behavior ->
                            binding as AuthMailSheetBinding
                            binding.authConfirmMailAddSheet.setOnClickListener {
                                isFinishWhenCancel = false
                                dialog.dismiss()
                                findNavController().navigateWithAnim(
                                    AuthConfirmFragmentDirections.toAuthMailFragment()
                                )
                            }

                            binding.authConfirmMailLaterSheet.setOnClickListener {
                                mActivity.finish()
                            }
                        },
                        onCancel = {
                            if (isFinishWhenCancel) {
                                mActivity.finish()
                            }
                        }
                    )
                )
            )
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