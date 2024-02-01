package ru.apteka.profile.data.models

import ru.apteka.components.data.models.PharmacyFavoriteModel
import ru.apteka.components.data.models.PharmacyModel


/**
 * Представляет карточку аптеки для профиля.
 */
data class ProfilePharmacyCardModel(
    val pharmacy: PharmacyModel
) {
    /**
     * Возвращает или устанавливает модель обработки избранного.
     */
    lateinit var favorite: PharmacyFavoriteModel
}
