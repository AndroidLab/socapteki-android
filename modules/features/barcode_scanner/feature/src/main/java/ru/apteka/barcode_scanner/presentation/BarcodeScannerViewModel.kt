package ru.apteka.barcode_scanner.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import ru.apteka.components.data.models.ProductModel
import ru.apteka.components.data.services.RequestHandler
import ru.apteka.components.data.services.message_notice_service.MessageService
import ru.apteka.components.data.services.navigation_manager.NavigationManager
import ru.apteka.components.data.utils.launchIO
import ru.apteka.components.ui.BaseViewModel
import java.util.UUID
import javax.inject.Inject

/**
 * Представляет модель представления "Сканирование баркода".
 */
@HiltViewModel
class BarcodeScannerViewModel @Inject constructor(
    private val requestHandler: RequestHandler,
    navigationManager: NavigationManager,
    messageService: MessageService
) : BaseViewModel(
    navigationManager,
    messageService
) {
    private val badCode = "800003674602"

    private val _isScanError = MutableLiveData(false)

    /**
     * Возвращает флаг ошибки сканирования.
     */
    val isScanError: LiveData<Boolean> = _isScanError

    private val _product = MutableLiveData<ProductModel?>(null)

    val product: LiveData<ProductModel?> = _product

    fun getProductByCode(code: String) {
        viewModelScope.launchIO {
            _isLoading.postValue(true)
            delay(2500)
            if (code == badCode) {
                _isScanError.postValue(true)
            } else {
                _product.postValue(
                    ProductModel(
                        id = UUID.randomUUID(),
                        image = "",
                        isFavorite = false,
                        price = "2 500p",
                        title = "Какой то товар",
                        rating = "4.7",
                        comments = 50,
                    )
                )
            }
            _isLoading.postValue(false)
        }
    }
}
