package ru.apteka.components.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.delay
import ru.apteka.components.R
import ru.apteka.components.data.utils.launchIO
import ru.apteka.components.data.utils.mainThread
import ru.apteka.components.data.utils.setSoftInputModeNothing

/**
 * Представляет базовый класс для фрагмента экрана.
 */
abstract class BaseFragment<TViewModel : ViewModel, TDataBinding : ViewDataBinding> : Fragment() {
    private var _binding: TDataBinding? = null

    open val binding get() = checkNotNull(_binding) { getString(R.string.binding_class_not_created) }

    /**
     * Возвращает модель представления.
     */
    open val viewModel: TViewModel? = null

    /**
     * Возвращает activity.
     */
    val mActivity: AppCompatActivity by lazy { requireActivity() as AppCompatActivity }


    /**
     * Возвращает навигационный контроллер.
     */
    val nController: NavController by lazy { findNavController() }

    private val imm by lazy {
        ContextCompat.getSystemService(
            requireContext(),
            InputMethodManager::class.java
        )
    }

    /**
     * Возвращает идентификатор layout.
     */
    abstract val layoutId: Int?

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = if (layoutId == null) {
        super.onCreateView(inflater, container, savedInstanceState)
    } else {
        _binding = DataBindingUtil.inflate(layoutInflater, layoutId!!, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if (layoutId != null) {
            onViewBindingInflated(_binding!!)
        }
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onStop() {
        super.onStop()
        keyBoardClose()
        setSoftInputModeNothing()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /**
     * Инициализация связывания.
     * @param binding Класс связывания.
     */
    open fun onViewBindingInflated(binding: TDataBinding) {}

    /**
     * Открывает клавиатуру.
     */
    fun keyBoardOpen(view: View) {
        lifecycleScope.launchIO {
            delay(500)
            mainThread {
                imm?.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
            }
        }
    }

    /**
     * Закрывает клавиатуру.
     */
    fun keyBoardClose() {
        imm?.hideSoftInputFromWindow(view?.windowToken, 0)
    }

}