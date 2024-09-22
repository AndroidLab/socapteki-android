package ru.apteka.home.presentation.bonus

import dagger.hilt.android.lifecycle.HiltViewModel
import ru.apteka.components.data.models.DiscountModel
import ru.apteka.components.data.services.message_notice_service.IMessageService
import ru.apteka.components.data.services.navigation_manager.NavigationManager
import ru.apteka.components.data.utils.ScopedLiveData
import ru.apteka.components.ui.BaseViewModel
import ru.apteka.home.data.models.BonusTicketModel
import javax.inject.Inject

/**
 * Представляет модель представления "Бонусная программа".
 */
@HiltViewModel
class BonusViewModel @Inject constructor(
    navigationManager: NavigationManager,
    messageService: IMessageService
) : BaseViewModel(
    navigationManager,
    messageService
) {

    private val bonusProgramTicketsFakes = listOf(
        BonusTicketModel(
            image = "https://social-apteka.ru/upload/ammina.optimizer/jpg-webp/q80/upload/resize_cache/iblock/7e0/gjdf42sxmtg8gl5e5b3cii97u7n5ucje/160_160_0/alrg10mg.webp",
            title = "Аллергостин таб п/пл/о 10мг N10 (Полисан)",
            date = "с 16.11 по 29.11",
            discount = DiscountModel(
                oldPrice = "",
                percent = "-15%"
            ),
            isActivated = false
        ),
        BonusTicketModel(
            image = "https://social-apteka.ru/upload/ammina.optimizer/jpeg-webp/q80/upload/resize_cache/iblock/own/320_320_0/PREV_16921-1.webp",
            title = "Цетиризин таб 10мг N30 (Вертекс)",
            date = "с 16.11 по 29.11",
            discount = DiscountModel(
                oldPrice = "",
                percent = "-15%"
            ),
            isActivated = false
        ),
        BonusTicketModel(
            image = "https://social-apteka.ru/upload/ammina.optimizer/jpg-webp/q80/upload/resize_cache/iblock/own/320_320_0/PREV_17186-1.webp",
            title = "Лоратадин таб 10мг N30 (Вертекс)",
            date = "с 16.11 по 29.11",
            discount = DiscountModel(
                oldPrice = "",
                percent = "-20%"
            ),
            isActivated = false
        ),
        BonusTicketModel(
            image = "https://social-apteka.ru/upload/ammina.optimizer/jpeg-webp/q80/upload/resize_cache/iblock/489/320_320_0/6f7d71bb56414ce7bb2262fc139033b4.webp",
            title = "Аллергостин таб п/пл/о 10мг N10 (Полисан)",
            date = "с 16.11 по 29.11",
            discount = DiscountModel(
                oldPrice = "",
                percent = "-5%"
            ),
            isActivated = false
        ),
        BonusTicketModel(
            image = "https://social-apteka.ru/upload/ammina.optimizer/jpg-webp/q80/upload/resize_cache/iblock/150/320_320_0/1502eaf7297014c4e8614da9c1a63cef.webp",
            title = "Витамир Ледишарм витамины д/волос таб N30 (Квадрат-С)",
            date = "с 16.11 по 29.11",
            discount = DiscountModel(
                oldPrice = "",
                percent = "-12%"
            ),
            isActivated = false
        ),
        BonusTicketModel(
            image = "https://social-apteka.ru/upload/ammina.optimizer/jpg-webp/q80/upload/resize_cache/iblock/eb6/320_320_0/eb6fd745730f175591a7a411bb127cb3.webp",
            title = "Аллергостин таб п/пл/о 10мг N10 (Полисан)",
            date = "с 16.11 по 29.11",
            discount = DiscountModel(
                oldPrice = "",
                percent = "-3%"
            ),
            isActivated = false
        ),
    )


    /**
     * Возвращает список тикетов.
     */
    val tickets = ScopedLiveData(bonusProgramTicketsFakes)

}
