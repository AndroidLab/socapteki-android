package ru.apteka.brands.presentation.pages

import androidx.core.os.bundleOf
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import ru.apteka.brands.data.model.BrandModel
import ru.apteka.brands.data.model.LettersCardModel
import ru.apteka.brands.data.model.LettersItemModel
import ru.apteka.components.data.services.RequestHandler
import ru.apteka.components.data.services.message_notice_service.IMessageService
import ru.apteka.components.data.services.navigation_manager.NavigationManager
import ru.apteka.components.data.utils.ScopedLiveData
import ru.apteka.components.data.utils.debounce
import ru.apteka.components.data.utils.launchIO
import ru.apteka.components.data.utils.navigateWithAnim
import ru.apteka.components.ui.BaseViewModel
import ru.apteka.listing_api.api.LISTING_ARGUMENT
import javax.inject.Inject
import ru.apteka.listing_api.R as ListingApiR

/**
 * Представляет модель представления "Страница бренды".
 */
@HiltViewModel
class LettersPageViewModel @Inject constructor(
    private val requestHandler: RequestHandler,
    navigationManager: NavigationManager,
    messageService: IMessageService
) : BaseViewModel(
    navigationManager,
    messageService
) {

    private val fakeBrands = listOf(
        BrandModel(
            title = "А",
            items = listOf(
                LettersItemModel(
                    item = "Авангард 1"
                ) {
                    navigateToListing("Авангард 1")
                },
                LettersItemModel(
                    item = "Авангард 2"
                ) {
                    navigateToListing("Авангард 2")
                },
                LettersItemModel(
                    item = "Авангард 3"
                ) {
                    navigateToListing("Авангард 3")
                },
                LettersItemModel(
                    item = "Авангард 4"
                ) {
                    navigateToListing("Авангард 4")
                },
                LettersItemModel(
                    item = "Авангард 5"
                ) {
                    navigateToListing("Авангард 5")
                },
                LettersItemModel(
                    item = "Авангард 6"
                ) {
                    navigateToListing("Авангард 6")
                },
                LettersItemModel(
                    item = "Авангард 7"
                ) {
                    navigateToListing("Авангард 7")
                },
                LettersItemModel(
                    item = "Авангард 8"
                ) {
                    navigateToListing("Авангард 8")
                },
            )
        ),
        BrandModel(
            title = "Б",
            items = listOf(
                LettersItemModel(
                    item = "Биокад 1"
                ) {
                    navigateToListing("Биокад 1")
                },
                LettersItemModel(
                    item = "Биокад 2"
                ) {
                    navigateToListing("Биокад 2")
                },
                LettersItemModel(
                    item = "Биокад 3"
                ) {
                    navigateToListing("Биокад 3")
                },
                LettersItemModel(
                    item = "Биокад 4"
                ) {
                    navigateToListing("Биокад 4")
                },
                LettersItemModel(
                    item = "Биокад 5"
                ) {
                    navigateToListing("Биокад 5")
                },
                LettersItemModel(
                    item = "Биокад 6"
                ) {
                    navigateToListing("Биокад 6")
                },
                LettersItemModel(
                    item = "Биокад 7"
                ) {
                    navigateToListing("Биокад 7")
                },
                LettersItemModel(
                    item = "Биокад 8"
                ) {
                    navigateToListing("Биокад 8")
                },
            )
        ),
        BrandModel(
            title = "В",
            items = listOf(
                LettersItemModel(
                    item = "Валексфарм 1"
                ) {
                    navigateToListing("Валексфарм 1")
                },
                LettersItemModel(
                    item = "Валексфарм 2"
                ) {
                    navigateToListing("Валексфарм 2")
                },
                LettersItemModel(
                    item = "Валексфарм 3"
                ) {
                    navigateToListing("Валексфарм 3")
                },
                LettersItemModel(
                    item = "Валексфарм 4"
                ) {
                    navigateToListing("Валексфарм 4")
                },
                LettersItemModel(
                    item = "Валексфарм 5"
                ) {
                    navigateToListing("Валексфарм 5")
                },
                LettersItemModel(
                    item = "Валексфарм 6"
                ) {
                    navigateToListing("Валексфарм 6")
                },
                LettersItemModel(
                    item = "Валексфарм 7"
                ) {
                    navigateToListing("Валексфарм 7")
                },
                LettersItemModel(
                    item = "Валексфарм 8"
                ) {
                    navigateToListing("Валексфарм 8")
                },
            )
        ),
        BrandModel(
            title = "Г",
            items = listOf(
                LettersItemModel(
                    item = "Галичфарм 1"
                ) {
                    navigateToListing("Галичфарм 1")
                },
                LettersItemModel(
                    item = "Галичфарм 2"
                ) {
                    navigateToListing("Галичфарм 2")
                },
                LettersItemModel(
                    item = "Галичфарм 3"
                ) {
                    navigateToListing("Галичфарм 3")
                },
                LettersItemModel(
                    item = "Галичфарм 4"
                ) {
                    navigateToListing("Галичфарм 4")
                },
            )
        ),
        BrandModel(
            title = "Д",
            items = listOf(
                LettersItemModel(
                    item = "Дарница 1"
                ) {
                    navigateToListing("Дарница 1")
                },
                LettersItemModel(
                    item = "Дарница 2"
                ) {
                    navigateToListing("Дарница 2")
                },
                LettersItemModel(
                    item = "Дарница 3"
                ) {
                    navigateToListing("Дарница 3")
                },
                LettersItemModel(
                    item = "Дарница 4"
                ) {
                    navigateToListing("Дарница 4")
                },
            )
        ),
    )

    /**
     * Возвращает список брендов или производителей.
     */
    val letters = ScopedLiveData(emptyList<LettersCardModel>())

    /**
     * Возвращает флаг прогресса поиска.
     */
    val isSearchProgress = ScopedLiveData(false)

    /**
     * Возвращает или устанавливает значение для поиска.
     */
    var valueQuery = ""

    /**
     * Возвращает текст поиска.
     */
    val onTextChange = viewModelScope.debounce<String>(1500L) { value ->
        if (value.isNotBlank()) {
            if (value != valueQuery) {
                valueQuery = value
                lettersSearch(value)
            }
        } else {
            valueQuery = ""
            letters.postValue(
                fakeBrands
            )
        }
    }

    private suspend fun lettersSearch(value: String) {
        viewModelScope.launchIO {
            isSearchProgress.postValue(true)
            delay(1500)
            letters.postValue(
                fakeBrands.filter { it.items.any { it.item.contains(value, true) } }.map {
                    BrandModel(
                        title = it.title,
                        items = it.items.filter { it.item.contains(value, true) }
                    )
                }
            )
            isSearchProgress.postValue(false)
        }
    }

    /**
     * Возвращает бренды.
     */
    fun getLetters(type: String) {
        viewModelScope.launchIO {
            isLoading.postValue(true)
            delay(1500)
            letters.postValue(fakeBrands)
            isLoading.postValue(false)
        }
    }

    private fun navigateToListing(title: String) {
        navigationManager.generalNavController.navigateWithAnim(
            ListingApiR.id.listing_graph,
            bundleOf(
                LISTING_ARGUMENT to title
            )
        )
    }
}
