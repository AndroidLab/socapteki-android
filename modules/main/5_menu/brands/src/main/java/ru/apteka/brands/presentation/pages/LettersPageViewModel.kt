package ru.apteka.brands.presentation.pages

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import ru.apteka.brands.data.model.BrandModel
import ru.apteka.brands.data.model.LettersCardModel
import ru.apteka.brands.data.model.LettersItemModel
import ru.apteka.components.data.services.RequestHandler
import ru.apteka.components.data.services.message_notice_service.IMessageNoticeService
import ru.apteka.components.data.services.navigation_manager.NavigationManager
import ru.apteka.components.data.utils.debounce
import ru.apteka.components.data.utils.launchIO
import ru.apteka.components.ui.BaseViewModel
import javax.inject.Inject


/**
 * Представляет модель представления "Страница бренды".
 */
@HiltViewModel
class LettersPageViewModel @Inject constructor(
    private val requestHandler: RequestHandler,
    navigationManager: NavigationManager,
    messageNoticeService: IMessageNoticeService
) : BaseViewModel(
    navigationManager,
    messageNoticeService
) {

    private val fakeBrands = listOf(
        BrandModel().apply {
            title = "А"
            items = listOf(
                LettersItemModel(
                    item = "Авангард 1"
                ) {
                    Log.d("myL", it)
                },
                LettersItemModel(
                    item = "Авангард 2"
                ) {
                    Log.d("myL", it)
                },
                LettersItemModel(
                    item = "Авангард 3"
                ) {
                    Log.d("myL", it)
                },
                LettersItemModel(
                    item = "Авангард 4"
                ) {
                    Log.d("myL", it)
                },
            )
        },
        BrandModel().apply {
            title = "Б"
            items = listOf(
                LettersItemModel(
                    item = "Биокад 1"
                ) {
                    Log.d("myL", it)
                },
                LettersItemModel(
                    item = "Биокад 2"
                ) {
                    Log.d("myL", it)
                },
                LettersItemModel(
                    item = "Биокад 3"
                ) {
                    Log.d("myL", it)
                },
                LettersItemModel(
                    item = "Биокад 4"
                ) {
                    Log.d("myL", it)
                },
            )
        },
        BrandModel().apply {
            title = "В"
            items = listOf(
                LettersItemModel(
                    item = "Валексфарм 1"
                ) {
                    Log.d("myL", it)
                },
                LettersItemModel(
                    item = "Валексфарм 2"
                ) {
                    Log.d("myL", it)
                },
                LettersItemModel(
                    item = "Валексфарм 3"
                ) {
                    Log.d("myL", it)
                },
                LettersItemModel(
                    item = "Валексфарм 4"
                ) {
                    Log.d("myL", it)
                },
            )
        },
        BrandModel().apply {
            title = "Г"
            items = listOf(
                LettersItemModel(
                    item = "Галичфарм 1"
                ) {
                    Log.d("myL", it)
                },
                LettersItemModel(
                    item = "Галичфарм 2"
                ) {
                    Log.d("myL", it)
                },
                LettersItemModel(
                    item = "Галичфарм 3"
                ) {
                    Log.d("myL", it)
                },
                LettersItemModel(
                    item = "Галичфарм 4"
                ) {
                    Log.d("myL", it)
                },
            )
        },
        BrandModel().apply {
            title = "Д"
            items = listOf(
                LettersItemModel(
                    item = "Дарница 1"
                ) {
                    Log.d("myL", it)
                },
                LettersItemModel(
                    item = "Дарница 2"
                ) {
                    Log.d("myL", it)
                },
                LettersItemModel(
                    item = "Дарница 3"
                ) {
                    Log.d("myL", it)
                },
                LettersItemModel(
                    item = "Дарница 4"
                ) {
                    Log.d("myL", it)
                },
            )
        },
    )

    private val _letters = MutableLiveData<List<LettersCardModel>>(emptyList())

    /**
     * Возвращает список брендов или производителей.
     */
    val letters: LiveData<List<LettersCardModel>> = _letters

    private val _isSearchProgress = MutableLiveData(false)

    /**
     * Возвращает флаг прогресса поиска.
     */
    val isSearchProgress: LiveData<Boolean> = _isSearchProgress

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
            _letters.postValue(
                fakeBrands
            )
        }
    }


    private suspend fun lettersSearch(value: String) {
        viewModelScope.launchIO {
            _isSearchProgress.postValue(true)
            delay(1500)
            _letters.postValue(
                fakeBrands.filter { it.items.any { it.item.contains(value, true) } }.map {
                    BrandModel().apply {
                        title = it.title
                        items = it.items.filter { it.item.contains(value, true) }
                    }
                }
            )
            _isSearchProgress.postValue(false)
        }
    }

    /**
     * Возвращает бренды.
     */
    fun getLetters(type: String) {
        viewModelScope.launchIO {
            _isLoading.postValue(true)
            delay(1500)
            _letters.postValue(fakeBrands)
            _isLoading.postValue(false)
        }
    }

}