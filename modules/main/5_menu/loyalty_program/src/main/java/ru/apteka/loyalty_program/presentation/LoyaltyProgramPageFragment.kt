package ru.apteka.loyalty_program.presentation

import android.os.Parcelable
import androidx.core.os.bundleOf
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.parcelize.Parcelize
import ru.apteka.components.ui.BaseFragment
import ru.apteka.loyalty_program.R
import ru.apteka.loyalty_program.databinding.LoyaltyProgramPageFragmentBinding


/**
 * Представляет фрагмент "Страница ".
 */
@AndroidEntryPoint
class LoyaltyProgramPageFragment private constructor() :
    BaseFragment<Nothing, LoyaltyProgramPageFragmentBinding>() {

    override val layoutId: Int = R.layout.loyalty_program_page_fragment

    override fun onViewBindingInflated(binding: LoyaltyProgramPageFragmentBinding) {
        val data: PageData = arguments?.getParcelable(DATA)!!
        binding.title = data.title
        binding.list = data.items
    }

    companion object {
        private const val DATA = "DATA"

        fun getInstance(
            title: String,
            items: List<String>
        ) = LoyaltyProgramPageFragment().apply {
            arguments = bundleOf(
                DATA to PageData(
                    title = title,
                    items = items
                )
            )
        }
    }

    @Parcelize
    private class PageData(
        val title: String,
        val items: List<String>
    ) : Parcelable
}
