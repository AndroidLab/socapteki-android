package ru.apteka.catalog.presentation.subcatalog

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.apteka.catalog.data.models.CatalogItem
import ru.apteka.components.data.services.RequestHandler
import ru.apteka.components.data.services.message_notice_service.IMessageService
import ru.apteka.components.data.services.navigation_manager.NavigationManager
import ru.apteka.components.ui.BaseViewModel
import javax.inject.Inject
import kotlin.properties.Delegates

/**
 * Представляет модель представления "Подкаталог".
 */
@HiltViewModel
class SubCatalogViewModel @Inject constructor(
    private val requestHandler: RequestHandler,
    navigationManager: NavigationManager,
    messageService: IMessageService
) : BaseViewModel(
    navigationManager,
    messageService
) {
    /**
     * Возвращает пункт каталога.
     */
    var catalogItem: CatalogItem? by Delegates.observable(null) { d, old, new ->
        subCatalogItems.value = new!!.subItems.map {
            CatalogItem(title = it)
        }
    }

    private val subCatalogItems = MutableLiveData<List<CatalogItem>>(emptyList())

    /**
     * Возвращает или устанавливает введенный текст для поиска.
     */
    val searchText = MutableLiveData("")

    /**
     * Возвращает или устанавливает найденные подкатегории.
     */
    val subCatalogItemsFound = MediatorLiveData<List<Any>>().apply {
        fun setCatalogItems() {
            value = if (searchText.value!!.isEmpty()) {
                listOf(catalogItem!!.title) + (subCatalogItems.value ?: emptyList())
            } else {
                listOf(catalogItem!!.title) + (
                        subCatalogItems.value?.filter {
                            it.title.contains(
                                searchText.value!!,
                                true
                            )
                        }
                            ?: emptyList()
                        )
            }
        }

        addSource(subCatalogItems) {
            setCatalogItems()
        }

        addSource(searchText) {
            setCatalogItems()
        }
    }
}
