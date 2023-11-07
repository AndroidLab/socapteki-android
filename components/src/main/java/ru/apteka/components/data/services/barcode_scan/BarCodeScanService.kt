package ru.apteka.components.data.services.barcode_scan

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import ru.apteka.components.data.services.message_notice_service.models.DialogModel
import ru.apteka.components.data.utils.launchIO
import javax.inject.Inject
import javax.inject.Singleton


/**
 * Представляет сервис сканирования баркодов.
 */
@Singleton
class BarCodeScanService @Inject constructor() : IBarCodeScanService {

    private val _scanStart =
        MutableSharedFlow<Boolean>()

    /**
     * Возвращает флаг старта сканирования баркода.
     */
    val scanStart: SharedFlow<Boolean> = _scanStart.asSharedFlow()

    override fun showBarcodeScan() {
        GlobalScope.launchIO {
            _scanStart.emit(true)
        }
    }

    private val _barcodeResult = MutableSharedFlow<String>()

    override val barcodeResult: SharedFlow<String> = _barcodeResult.asSharedFlow()

    /**
     * Отправляет результат сканирования.
     */
    fun sendBarcodeResult(code: String) {
        GlobalScope.launchIO {
            _barcodeResult.emit(code)
        }
    }

}