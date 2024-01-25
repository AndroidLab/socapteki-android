package ru.apteka.catalog.presentation.catalog_products

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.apteka.catalog.data.catalog_repository.CatalogRepository
import ru.apteka.catalog.data.models.SearchHistoryHeaderModel
import ru.apteka.catalog.data.models.SearchResultHeaderModel
import ru.apteka.catalog.data.models.SearchResultModel
import ru.apteka.catalog.data.services.SearchProductPreferences
import ru.apteka.components.data.services.RequestHandler
import ru.apteka.components.data.services.message_notice_service.IMessageService
import ru.apteka.components.data.services.navigation_manager.NavigationManager
import ru.apteka.components.data.utils.debounce
import ru.apteka.components.ui.BaseViewModel
import javax.inject.Inject


/**
 * Представляет модель представления "Поиск по каталогу".
 */
@HiltViewModel
class SearchProductsViewModel @Inject constructor(
    private val requestHandler: RequestHandler,
    private val catalogRepository: CatalogRepository,
    val searchProductPreferences: SearchProductPreferences,
    navigationManager: NavigationManager,
    messageService: IMessageService
) : BaseViewModel(
    navigationManager,
    messageService
) {
    private val _foundResults = MutableLiveData<List<Any>>(emptyList())

    /**
     * Возвращает найденные результаты.
     */
    val foundResults: LiveData<List<Any>> = _foundResults

    /**
     * Возвращает или устанавливает введенный текст для поиска.
     */
    val searchText = MutableLiveData("")

    /**
     * Возвращает или устанавливает текст для поиска.
     */
    var textQuery = ""


    /**
     * Возвращает текст поиска заказа.
     */
    val onSearchTextChange = viewModelScope.debounce<String>(1000L) { value ->
        if (value.isNotBlank()) {
            if (value != textQuery) {
                searchProducts(value)
            }
        } else {
            clearData()
        }
        textQuery = value
    }

    private val _isSearchProductsLoading = MutableLiveData(false)

    /**
     * Возвращает флаг поиска продукции.
     */
    val isSearchProductsLoading = _isSearchProductsLoading



    private suspend fun searchProducts(value: String) {
        val historyRequests = searchProductPreferences.getHistoryRequest(value).let {
            buildList {
                if (it.isNotEmpty()) {
                    add(
                        SearchHistoryHeaderModel {
                            searchProductPreferences.clear()
                            _foundResults.value = _foundResults.value!!.filter {
                                it !is SearchHistoryHeaderModel && it is SearchResultHeaderModel || (it is SearchResultModel && it.type != SearchResultModel.SearchResultType.HISTORY)
                            }
                        }
                    )
                    addAll(
                        it.map {
                            SearchResultModel(
                                type = SearchResultModel.SearchResultType.HISTORY,
                                text = it
                            )
                        }
                    )
                }
            }
        }
        _foundResults.postValue(historyRequests)

        requestHandler.handleApiRequest(
            onRequest = {
                catalogRepository.searchResult(value)
            },
            onSuccess = { searchResults ->
                _foundResults.postValue(
                    buildList {
                        addAll(historyRequests)
                        if (searchResults.isNotEmpty()) {
                            add(SearchResultHeaderModel())
                            addAll(
                                searchResults
                            )
                        }
                    }
                )
            },
            isLoading = _isSearchProductsLoading
        )
    }

    /**
     * Очищает данные.
     */
    private fun clearData() {
        _foundResults.postValue(emptyList())
    }

}