package ru.apteka.components.data.services.bottom_sheet_service

import androidx.databinding.ViewDataBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import ru.apteka.components.data.utils.launchIO
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Сервис уведомлений об ошибках.
 */
@Singleton
class BottomSheetService @Inject constructor() : IBottomSheetService {
    private val _bottomSheet =
        MutableSharedFlow<ViewDataBinding>()

    var bottomSheetDialog: BottomSheetDialogFragment? = null

    /**
     * Возвращает модель для показа нижней таблицы.
     */
    val bottomSheet: SharedFlow<ViewDataBinding> = _bottomSheet.asSharedFlow()


    override fun show(
        binding: ViewDataBinding
    ) {
        GlobalScope.launchIO {
            _bottomSheet.emit(binding)
        }
    }

    override fun close() {
        bottomSheetDialog?.dismiss()
    }
}