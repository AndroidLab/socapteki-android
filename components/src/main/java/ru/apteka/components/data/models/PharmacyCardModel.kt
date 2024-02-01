package ru.apteka.components.data.models


/**
 * Представляет модель для карточки аптеки.
 * @param pharmacy Аптека.
 */
data class PharmacyCardModel(
    val pharmacy: PharmacyModel
) {
    /**
     * Возвращает или устанавливает модель обработки избранного.
     */
    lateinit var favorite: PharmacyFavoriteModel
}
