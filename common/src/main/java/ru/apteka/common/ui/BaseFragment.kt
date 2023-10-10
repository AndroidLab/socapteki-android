package ru.apteka.common.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import ru.apteka.resources.R as ResourcesR

/**
 * Представляет базовый класс для фрагмента экрана.
 */
abstract class BaseFragment<TViewModel : ViewModel, TDataBinding : ViewDataBinding> : Fragment() {
    private var _binding: TDataBinding? = null

    open val binding get() = checkNotNull(_binding) { getString(ResourcesR.string.binding_class_not_created) }

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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /**
     * Инициализация связывания.
     * @param binding Класс связывания.
     */
    open fun onViewBindingInflated(binding: TDataBinding) {}

}