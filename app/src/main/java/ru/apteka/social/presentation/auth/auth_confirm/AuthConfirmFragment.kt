package ru.apteka.social.presentation.auth.auth_confirm

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import ru.apteka.components.ui.BaseFragment
import ru.apteka.social.R
import ru.apteka.social.databinding.AuthConfirmFragmentBinding
import ru.tinkoff.decoro.MaskImpl
import ru.tinkoff.decoro.parser.UnderscoreDigitSlotsParser
import ru.tinkoff.decoro.slots.PredefinedSlots
import ru.tinkoff.decoro.watchers.MaskFormatWatcher


/**
 * Представляет фрагмент "Подтверждение авторизации".
 */
@AndroidEntryPoint
class AuthConfirmFragment : BaseFragment<AuthConfirmViewModel, AuthConfirmFragmentBinding>() {
    override val viewModel: AuthConfirmViewModel by viewModels()
    override val layoutId: Int = R.layout.auth_confirm_fragment

    private val args: AuthConfirmFragmentArgs by navArgs()

    override fun onViewBindingInflated(binding: AuthConfirmFragmentBinding) {
        viewModel.phoneNumber = args.phoneNumber
        binding.viewModel = viewModel
        binding.tvAuthConfirmPhone.text =
            MaskImpl(PredefinedSlots.RUS_PHONE_NUMBER, true).apply {
                insertFront(args.phoneNumber)
                toString()
            }.toString()
        binding.tvAuthConfirmPhoneChange.setOnClickListener {

        }
        MaskFormatWatcher(
            MaskImpl.createTerminated(
                UnderscoreDigitSlotsParser().parseSlots("_ _ _ _")
            )
        ).installOn(binding.etAuthConfirmCode)

        viewModel.isNavigationToMain.observe(viewLifecycleOwner) {
            //startActivity(Intent(requireContext(), MainActivity::class.java))
            mActivity.finish()
        }
    }
}