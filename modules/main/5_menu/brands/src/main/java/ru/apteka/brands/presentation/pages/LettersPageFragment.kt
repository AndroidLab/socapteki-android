package ru.apteka.brands.presentation.pages

import android.util.Log
import androidx.core.os.bundleOf
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import ru.apteka.brands.R
import ru.apteka.brands.databinding.LettersPageBinding
import ru.apteka.brands.presentation.LettersCardAdapter
import ru.apteka.components.data.utils.equalsWithDeviation
import ru.apteka.components.data.utils.playAnimation
import ru.apteka.components.ui.BaseFragment
import ru.apteka.components.ui.delegate_adapter.CompositeDelegateAdapter


/**
 * Представляет фрагмент "Страница бренды".
 */
@AndroidEntryPoint
class LettersPageFragment :
    BaseFragment<LettersPageViewModel, LettersPageBinding>() {
    override val viewModel: LettersPageViewModel by viewModels()
    override val layoutId: Int = R.layout.letters_page

    private val lettersAdapter by lazy {
        CompositeDelegateAdapter(
            LettersCardAdapter(::onAllByLetter)
        )
    }

    private val lettersType
        get() = arguments?.getString(TYPE)

    override fun onViewBindingInflated(binding: LettersPageBinding) {
        binding.viewModel = viewModel

        binding.rvLetters.adapter = lettersAdapter

        viewModel.letters.observe(viewLifecycleOwner) {
            lettersAdapter.swapData(it)
        }

        binding.etBrands.doAfterTextChanged {
            viewModel.onTextChange.invoke(it.toString())
        }

        viewModel.getLetters(lettersType!!)
    }

    private fun onAllByLetter(letter: String) {
        Log.d("myL", "letter " + letter)
    }

    companion object {
        private const val TYPE = "TYPE"
        const val BRAND = "BRAND"
        const val MANUFACTURE = "MANUFACTURE"

        fun newInstance(type: String) = LettersPageFragment().apply {
            arguments = bundleOf(
                TYPE to type
            )
        }
    }

}