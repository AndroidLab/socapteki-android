package ru.apteka.components.data.services.bottom_sheet_service

import androidx.databinding.ViewDataBinding
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
    fun show(binding: ViewDataBinding)

    /**
     * Закрывает нижнюю таблицу.
     */
    fun close()

}