package ru.apteka.components.ui.composite_delegate_adapter

/**
 * Класс для хранения адаптеров и данных [CompositeDelegateAdapter].
 * @param adapters Список адаптеров.
 * @param data Данные для отображения.
 */
data class AdaptersState(
    private val adapters: List<IDelegateAdapter>,
    val data: List<Any> = emptyList()
) {

    private val adapterPositionsCache = Array(data.size) { -1 }

    /**
     * Возвращает позиию.
     * @param itemPosition Позиция пункта.
     */
    fun getAdapterPosition(itemPosition: Int): Int =
        adapterPositionsCache[itemPosition].takeIf { it != -1 }
            ?: adapters.indexOfFirst { it.isForViewType(data, itemPosition) }
                .takeIf { it != -1 }
                ?.also { adapterPositionsCache[itemPosition] = it }
            ?: error("Provide adapter for type ${data[itemPosition].javaClass} at position: $itemPosition")

    /**
     * Возвращает адаптер.
     * @param adapterPosition Позиция адаптера.
     */
    fun getAdapter(adapterPosition: Int): IDelegateAdapter = adapters[adapterPosition]

    /**
     * Возвращает адаптер по позиции пункта.
     * @param itemPosition Позиция пункта.
     */
    fun getAdapterByItemPosition(itemPosition: Int): IDelegateAdapter =
        adapters[getAdapterPosition(itemPosition)]
}
