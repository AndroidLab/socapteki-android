package ru.apteka.components.data.services.message_notice_service

import androidx.databinding.ViewDataBinding
import ru.apteka.components.data.services.message_notice_service.models.BottomSheetModel
import ru.apteka.components.data.services.message_notice_service.models.DialogModel
import javax.inject.Singleton

/**
 * Описывает свойства и методы вызова нижней таблицы.
 */
@Singleton
interface IBottomSheetService {

    /**
     * Показывает нижнюю таблицу.
     */
    fun show(bottomSheet: BottomSheetModel)

    /**
     * Закрывает нижнюю таблицу.
     */
    fun close()

}