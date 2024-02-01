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
    private val subCatalogItemsFake = listOf(
        CatalogItem(
            title = "Алкоголизм, наркомания, курение",
        ),
        CatalogItem(
            title = "Аллергия",
        ),
        CatalogItem(
            title = "Анестезия, реанимация",
        ),
        CatalogItem(
            title = "Антибиотики",
        ),
        CatalogItem(
            title = "Боль, температура",
        ),
        CatalogItem(
            title = "Геморрой",
        ),
        CatalogItem(
            title = "Глаза",
        ),
        CatalogItem(
            title = "Глисты, вши, чесотка",
        ),
        CatalogItem(
            title = "Гомеопатия",
        ),
        CatalogItem(
            title = "Грипп и простуда",
        ),
        CatalogItem(
            title = "Диабет",
        ),
    )

    private val subCatalogItemsFake2 = listOf(
        CatalogItem(
            title = "Подкатегория 1 уровня 3",
        ),
        CatalogItem(
            title = "Подкатегория 2 уровня 3",
        ),
        CatalogItem(
            title = "Подкатегория 3 уровня 3",
        ),
        CatalogItem(
            title = "Подкатегория 4 уровня 3",
        ),
        CatalogItem(
            title = "Подкатегория 5 уровня 3",
        ),
        CatalogItem(
            title = "Подкатегория 6 уровня 3",
        ),
        CatalogItem(
            title = "Подкатегория 7 уровня 3",
        ),
        CatalogItem(
            title = "Подкатегория 8 уровня 3",
        ),
        CatalogItem(
            title = "Подкатегория 9 уровня 3",
        ),
        CatalogItem(
            title = "Подкатегория 10 уровня 3",
        ),
        CatalogItem(
            title = "Подкатегория 11 уровня 3",
        ),
    )

    var isLevel3: Boolean? = null

    /**
     * Возвращает пункт каталога.
     */
    var catalogItem: CatalogItem? by Delegates.observable(null) { d, old, new ->
        getSubCatalogItems()
    }

    private val subCatalogItems = MutableLiveData<List<CatalogItem>>(emptyList())

    private fun getSubCatalogItems() {
        subCatalogItems.value = if (isLevel3!!) {
            subCatalogItemsFake2
        } else {
            subCatalogItemsFake
        }
    }

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
