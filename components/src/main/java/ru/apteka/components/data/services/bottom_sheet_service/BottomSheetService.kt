package ru.apteka.components.data.services.bottom_sheet_service

import androidx.databinding.ViewDataBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Сервис уведомлений об ошибках.
 */
@Singleton
class BottomSheetService @Inject constructor() : IBottomSheetService {
    private val _bottomSheet =
        MutableSharedFlow<ViewDataBinding?>(replay = 1)

    var bottomSheetDialog: BottomSheetDialogFragment? = null

    /**
     * Возвращает модель для показа нижней таблицы.
     */
    val bottomSheet: SharedFlow<ViewDataBinding?> = _bottomSheet.asSharedFlow()


    /**
     * Сбрасывает значение.
     */
    fun reset() {
        _bottomSheet.tryEmit(null)
    }

    override fun show(
        binding: ViewDataBinding
    ) {
        _bottomSheet.tryEmit(binding)
    }

    override fun close() {
        bottomSheetDialog?.dismiss()
        bottomSheetDialog = null
    }
}