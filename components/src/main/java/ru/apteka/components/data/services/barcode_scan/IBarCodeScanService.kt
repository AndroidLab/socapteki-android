package ru.apteka.components.data.services.barcode_scan

import kotlinx.coroutines.flow.SharedFlow
import ru.apteka.components.data.services.message_notice_service.models.DialogModel
import ru.apteka.components.data.services.message_notice_service.models.MessageModel
import ru.apteka.components.data.services.message_notice_service.models.ToastModel
import javax.inject.Singleton

/**
 * Описывает свойства и методы вызова сообщений.
 */
interface IBarCodeScanService {

    /**
     * Показывает экран сканирования баркода.
     */
    fun showBarcodeScan()

    /**
     * Возвращает результат сканирования баркода.
     */
    val barcodeResult: SharedFlow<String>

}