package ru.apteka.pharmacies_map_api.api


/**
 * Перечисляет возможные типы взаимодействия с картой.
 */
enum class TypeInteraction {
    /**
     * Выбор аптеки.
     */
    SELECT_PHARMACY,

    /**
     * Прокладывание маршрута к аптеке.
     */
    NAVIGATION
}