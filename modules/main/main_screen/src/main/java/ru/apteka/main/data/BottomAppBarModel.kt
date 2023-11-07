package ru.apteka.main.data

import androidx.annotation.DrawableRes
import androidx.annotation.IdRes
import androidx.annotation.StringRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.apteka.main.R


/**
 * Представляет модель нижнего тул бара.
 */
class BottomAppBarModel {
    val item_1 = MenuItemModel(
        itemId = R.id.home_graph,
        icon = R.drawable.ic_home,
        title = R.string.menu_label_1,
        onItemClick = {
            _selectedItemId.value = it
            onItemSelectedListener?.onClick(it)
        }
    )

    val item_2 = MenuItemModel(
        itemId = R.id.catalog_graph,
        icon = R.drawable.ic_catalog,
        title = R.string.menu_label_2,
        onItemClick = {
            _selectedItemId.value = it
            onItemSelectedListener?.onClick(it)
        }
    )

    val item_3 = MenuItemModel(
        itemId = R.id.orders_graph,
        icon = R.drawable.ic_orders,
        title = R.string.menu_label_3,
        onItemClick = {
            _selectedItemId.value = it
            onItemSelectedListener?.onClick(it)
        }
    )

    val item_4 = MenuItemModel(
        itemId = R.id.favorites_graph,
        icon = R.drawable.ic_stocks,
        title = R.string.menu_label_4,
        onItemClick = {
            _selectedItemId.value = it
            onItemSelectedListener?.onClick(it)
        }
    )

    val item_5 = MenuItemModel(
        itemId = R.id.basket_graph,
        icon = R.drawable.ic_basket,
        title = R.string.menu_label_5,
        onItemClick = {
            _selectedItemId.value = it
            onItemSelectedListener?.onClick(it)
        }
    )



    private var onItemSelectedListener: OnItemSelectedListener? = null

    private val _selectedItemId = MutableLiveData(R.id.home_graph)

    /**
     * Возвращает индентификатор выбранного пункта.
     */
    val selectedItemId: LiveData<Int> = _selectedItemId

    /**
     * Возвращает обработчик выбора пункта нижнего навигационного меню.
     */
    val onSelectItemId: (itemId: Int) -> Unit = { itemId ->
        when (itemId) {
            R.id.home_graph -> item_1.onItemClick(itemId)
            R.id.catalog_graph -> item_2.onItemClick(itemId)
            R.id.orders_graph -> item_3.onItemClick(itemId)
            R.id.favorites_graph -> item_4.onItemClick(itemId)
            R.id.basket_graph -> item_5.onItemClick(itemId)
            else -> throw IllegalArgumentException("Пункт меню с идентификатором $itemId не найден.")
        }
    }

    /**
     * Устанавливает слушатель выбора пункта меню.
     */
    fun setOnItemSelectedListener(l: OnItemSelectedListener) {
        onItemSelectedListener = l
    }


    fun interface OnItemSelectedListener {
        fun onClick(itemId: Int)
    }

    data class MenuItemModel(
        @IdRes val itemId: Int,
        @DrawableRes val icon: Int,
        @StringRes val title: Int,
        val onItemClick: (Int) -> Unit
    )
}
