package ru.apteka.catalog.presentation.catalog

import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.apteka.components.data.services.RequestHandler
import ru.apteka.components.data.services.navigation_manager.NavigationManager
import ru.apteka.catalog.data.models.CatalogMenuItem
import ru.apteka.components.data.services.message_notice_service.IMessageService
import ru.apteka.components.ui.BaseViewModel
import javax.inject.Inject


/**
 * Представляет модель представления "Заказы".
 */
@HiltViewModel
class CatalogViewModel @Inject constructor(
    private val requestHandler: RequestHandler,
    navigationManager: NavigationManager,
    messageService: IMessageService
) : BaseViewModel(
    navigationManager,
    messageService
) {

    val catalogs = MutableLiveData(
        listOf(
            CatalogMenuItem(
                title = "Лекарства",
                image = "https://avatars.mds.yandex.net/i?id=73c7dc43ebb90f60671c7ff86f784a767c08a87f-10470894-images-thumbs&n=13"
            ),
            CatalogMenuItem(
                title = "Мама и малыш",
                image = "https://avatars.mds.yandex.net/i?id=f0b4ccedc6981a08308342ee986d8d53ffb0a53f-10136504-images-thumbs&n=13"
            ),
            CatalogMenuItem(
                title = "Витамины и БАДы",
                image = "https://avatars.mds.yandex.net/i?id=04e4af2ec28b114d2eee01c146ec5e5dafe80004-8261187-images-thumbs&n=13"
            ),
            CatalogMenuItem(
                title = "Гигиена",
                image = "https://avatars.mds.yandex.net/i?id=c543b9481df42301eb65b4f8c5e89ced9973d3b6-8283357-images-thumbs&n=13"
            ),
            CatalogMenuItem(
                title = "Красота и уход",
                image = "https://avatars.mds.yandex.net/i?id=fdc51ec1da1687e89e8256b1126afa21fa0565a6-5711615-images-thumbs&n=13"
            ),
            CatalogMenuItem(
                title = "Медтехника и товары",
                image = "https://avatars.mds.yandex.net/i?id=70fdd2654d6afbf794b3059615dd9b782380c974-10355051-images-thumbs&n=13"
            ),
        )
    )

    init {

    }


}