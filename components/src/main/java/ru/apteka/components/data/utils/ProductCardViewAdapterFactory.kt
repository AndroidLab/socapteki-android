package ru.apteka.components.data.utils

import androidx.lifecycle.LifecycleOwner
import ru.apteka.components.data.models.ProductModel
import ru.apteka.components.ui.adapters.ProductCardViewAdapter
import ru.apteka.components.ui.delegate_adapter.CompositeDelegateAdapter

/**
 * Возвращает адаптер для карточек продукции.
 * @param lifeOwner Владелец ЖЦ.
 * @param onItemClick Обработчик клика по пункту.
 * @param isHorizontal Флаг ориентации списка.
 */
fun getProductCardViewAdapter(
    lifeOwner: LifecycleOwner,
    onItemClick: (ProductModel) -> Unit,
    isHorizontal: Boolean = true
) = CompositeDelegateAdapter(
    ProductCardViewAdapter(
        lifeOwner,
        onItemClick,
        isHorizontal
    )
)