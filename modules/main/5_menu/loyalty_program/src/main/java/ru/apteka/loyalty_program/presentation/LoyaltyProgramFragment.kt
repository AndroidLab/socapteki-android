package ru.apteka.loyalty_program.presentation

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import ru.apteka.components.ui.BaseFragment
import ru.apteka.components.ui.adapters.PagerAdapter
import ru.apteka.loyalty_program.R
import ru.apteka.loyalty_program.databinding.LoyaltyProgramFragmentBinding


/**
 * Представляет фрагмент "Программа лояльности".
 */
@AndroidEntryPoint
class LoyaltyProgramFragment :
    BaseFragment<LoyaltyProgramViewModel, LoyaltyProgramFragmentBinding>() {

    override val viewModel: LoyaltyProgramViewModel by viewModels()
    override val layoutId: Int = R.layout.loyalty_program_fragment

    override fun onViewBindingInflated(binding: LoyaltyProgramFragmentBinding) {
        binding.viewModel = viewModel

        binding.tvLoyaltyProgramFullRules.setOnClickListener {

        }

        binding.loyaltyProgramPager.adapter = PagerAdapter(
            requireActivity(),
            arrayListOf(
                LoyaltyProgramPageFragment.getInstance(
                    title = getString(R.string.loyalty_program_page_1),
                    items = listOf(
                        "получайте бонусные баллы с каждой покупки",
                        "оплачивайте до 90% стоимости своих покупок бонусными баллами",
                        "получайте до 10% повышенными баллами за покупку акционных товаров",
                    )
                ) as Fragment,
                LoyaltyProgramPageFragment.getInstance(
                    title = getString(R.string.loyalty_program_page_2),
                    items = listOf(
                        "Совершите покупку в любой аптеке “Социальная Аптека”",
                        "Зарегистрируйте на кассе карту лояльности и назовите свои данные: имя, дата рождения и номер телефона",
                        "Продиктуйте фармацевту код из SMS, чтобы активировать карту",
                    )
                ) as Fragment,
                LoyaltyProgramPageFragment.getInstance(
                    title = getString(R.string.loyalty_program_page_3),
                    items = listOf(
                        "Совершите покупку в любой аптеке “Социальная Аптека”",
                        "Назовите номер телефона, к которому привязана карта лояльности",
                        "Получите баллы за покупку",
                    )
                ) as Fragment,
                LoyaltyProgramPageFragment.getInstance(
                    title = getString(R.string.loyalty_program_page_4),
                    items = listOf(
                        "возвращаем 0,5% бонусами за любую покупку",
                        "возвращаем 5% бонусами за любую покупку в День Рождения и 7 дней после",
                        "возвращаем до 10% бонусами за покупку акционных товаров",
                    )
                ) as Fragment,
                LoyaltyProgramPageFragment.getInstance(
                    title = getString(R.string.loyalty_program_page_5),
                    items = listOf(
                        "Совершите покупку в любой аптеке “Социальная Аптека”",
                        "Сообщите фармацевту, что хотите оплатить часть покупки накопленными баллами\n бонус = 1 рубль",
                        "Оплатите баллами до 90% стоимости покупки, назвав номер телефона, к которому привязана карта лояльности",
                    )
                ) as Fragment,
            )
        )
        TabLayoutMediator(
            binding.loyaltyProgramTabLayout,
            binding.loyaltyProgramPager
        ) { tab, pos ->
            tab.text = listOf(
                getString(R.string.loyalty_program_page_1),
                getString(R.string.loyalty_program_page_2),
                getString(R.string.loyalty_program_page_3),
                getString(R.string.loyalty_program_page_4),
                getString(R.string.loyalty_program_page_5),
            )[pos]
        }.attach()

        binding.flLoyaltyProgramRegistration.setOnClickListener {

        }
    }

    override fun onResume() {
        super.onResume()
        binding.loyaltyProgramToolbar.apply {
            tvToolbarTitle.text = getString(R.string.loyalty_program_title)
        }
    }
}