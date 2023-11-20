package ru.apteka.components.data.services.message_notice_service

import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import ru.apteka.components.data.services.message_notice_service.models.BottomSheetModel
import ru.apteka.components.data.utils.launchIO
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Сервис уведомлений об ошибках.
 */
@Singleton
class BottomSheetService @Inject constructor() : IBottomSheetService {
    private val _bottomSheet =
        MutableSharedFlow<BottomSheetModel>()

    var bottomSheetDialog: BottomSheetDialogFragment? = null

    /**
     * Возвращает модель для показа нижней таблицы.
     */
    val bottomSheet: SharedFlow<BottomSheetModel> = _bottomSheet.asSharedFlow()


    override fun show(
        bottomSheet: BottomSheetModel
    ) {
        GlobalScope.launchIO {
            _bottomSheet.emit(bottomSheet)
        }
    }

    override fun close() {
        bottomSheetDialog?.dismiss()
    }
}