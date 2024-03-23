package ru.apteka.listing.data.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import ru.apteka.components.data.models.ProductCardModel
import ru.apteka.components.data.utils.ScopedLiveData
import ru.apteka.components.data.utils.single_live_event.SingleLiveEvent
import ru.apteka.components.data.utils.subListOrNull


/**
 * Описывает свойства и методы фильтра.
 */
sealed interface IFilter {
    /**
     * Возвращает тип фильтра.
     */
    val type: FilterType

    /**
     * Возвращает название фильтра
     */
    val title: String

    /**
     * Возвращает обработчик изменения фильтра.
     */
    val onChanged: (IFilter) -> Unit

    /**
     * Возвращает флаг, что в фильтре есть какие то изменения.
     */
    val anySelected: LiveData<Boolean>

    /**
     * Возвращает флаг, что в фильтре произошли изменения относительно предыдущего состояния (Используется для изменения состояния кнопки).
     */
    val isChanged: LiveData<Boolean>

    /**
     * Возвращает флаг доступности выбора фильтров.
     */
    val enabled: MutableLiveData<Boolean>

    /**
     * Возвращает кол-во доступных продуктов.
     */
    val productsCount: MutableLiveData<Int>

    /**
     * Возвращает кол-во уже найденных продуктов с текущими фильтрами.
     */
    val foundProducts: LiveData<List<ProductCardModel>>

    /**
     * Возвращает строковое представоение фильтра.
     */
    val stringValue: LiveData<String>

    /**
     * Возвращает флаг, что работа с фильтром завершена.
     */
    val editingCompleted: SingleLiveEvent<Unit>

    /**
     * Сбрасывает фильтр на дефолтные настройки.
     */
    fun reset()

    /**
     * Отменяет изменения и закрывает диалог фильтров.
     */
    fun cancel() {
        editingCompleted.call()
    }

    fun apply() {
        editingCompleted.call()
    }


    data class FilterPriceModel(
        override val type: FilterType,
        override val title: String,
        override val foundProducts: LiveData<List<ProductCardModel>>,
        override val onChanged: (IFilter) -> Unit,
        val minPrice: Int,
        val maxPrice: Int,
    ) : IFilter {
        private var _selectedMinPrice: Int = minPrice
        private var _selectedMaxPrice: Int = maxPrice

        val selectedMinProgress: MutableLiveData<Int> = MutableLiveData(_selectedMinPrice)
        val selectedMaxProgress: MutableLiveData<Int> = MutableLiveData(_selectedMaxPrice)

        override val isChanged = MediatorLiveData(false).apply {
            fun checkChange() {
                value =
                    selectedMinProgress.value!! != _selectedMinPrice || selectedMaxProgress.value!! != _selectedMaxPrice
            }

            addSource(selectedMinProgress) {
                checkChange()
            }

            addSource(selectedMaxProgress) {
                checkChange()
            }
        }

        override val anySelected = ScopedLiveData(false)

        override val editingCompleted = SingleLiveEvent<Unit>()

        override val productsCount: MutableLiveData<Int> = MutableLiveData(55)

        override val stringValue = ScopedLiveData("")

        override val enabled = MutableLiveData(true)

        override fun reset() {
            _selectedMinPrice = minPrice
            _selectedMaxPrice = maxPrice
            selectedMinProgress.value = _selectedMinPrice
            selectedMaxProgress.value = _selectedMaxPrice
            apply()
            stringValue.setValue("")
        }

        override fun cancel() {
            selectedMinProgress.value = _selectedMinPrice
            selectedMaxProgress.value = _selectedMaxPrice
            super.cancel()
        }

        override fun apply() {
            _selectedMinPrice = selectedMinProgress.value!!
            _selectedMaxPrice = selectedMaxProgress.value!!
            anySelected.setValue(_selectedMinPrice != minPrice || _selectedMaxPrice != maxPrice)

            if (_selectedMinPrice == minPrice && _selectedMaxPrice == maxPrice) {
                stringValue.setValue("")
            } else {
                stringValue.setValue(": ${_selectedMinPrice}-${_selectedMaxPrice}")
            }
            super.apply()
        }

        fun onChange() {
            if (isChanged.value == true) {
                onChanged(this)
            }
        }
    }

    data class FilterReleaseFormModel(
        override val type: FilterType,
        override val title: String,
        override val foundProducts: LiveData<List<ProductCardModel>>,
        override val onChanged: (IFilter) -> Unit,
        val items: List<ReleaseFormModel>
    ) : IFilter {
        data class ReleaseFormModel(
            val title: String,
            val desk: String? = null,
            val enabled: MutableLiveData<Boolean> = MutableLiveData(false),
            val isSelected: MutableLiveData<Boolean> = MutableLiveData(false),
            val onItemClick: (ReleaseFormModel) -> Unit = { isSelected.value = !isSelected.value!! }
        )

        private val selectedItems: MutableList<Boolean> = items.map {
            it.isSelected.value!!
        }.toMutableList()

        override val isChanged = MediatorLiveData(false).apply {
            items.forEach {
                addSource(it.isSelected) {
                    value = items.zip(selectedItems).any {
                        it.first.isSelected.value!! != it.second
                    }
                    if (value == true) {
                        onChange()
                    } else {
                        productsCount.value = foundProducts.value!!.size
                    }
                }
            }
        }

        override val anySelected = ScopedLiveData(false)

        override val editingCompleted = SingleLiveEvent<Unit>()

        override val productsCount: MutableLiveData<Int> = MutableLiveData(55)

        override val stringValue = ScopedLiveData("")

        override val enabled = MutableLiveData(true)

        override fun reset() {
            items.forEachIndexed { index, releaseFormModel ->
                releaseFormModel.isSelected.value = false
                selectedItems[index] = false
            }
            apply()
            stringValue.setValue("")
        }

        override fun cancel() {
            selectedItems.forEachIndexed { index, b ->
                items[index].isSelected.value = b
            }
            super.cancel()
        }

        override fun apply() {
            items.forEachIndexed { index, releaseFormModel ->
                selectedItems[index] = releaseFormModel.isSelected.value!!
            }
            anySelected.setValue(selectedItems.any { it })

            val filterStringValue = items.filter { it.isSelected.value!! }.joinToString { it.title }
            if (filterStringValue.isEmpty()) {
                stringValue.setValue("")
            } else {
                stringValue.setValue(": $filterStringValue")
            }
            super.apply()
        }

        val mediator = MediatorLiveData(true).apply {
            fun setEnabled() {
                items.onEach {
                    it.enabled.value = productsCount.value!! > -1 && enabled.value!!
                }
            }

            addSource(productsCount) {
                setEnabled()
            }
            addSource(enabled) {
                setEnabled()
            }
        }

        fun onChange() {
            //if (isChanged.value == true) {
            onChanged(this)
            //}
        }
    }

    data class FilterManufacturerModel(
        override val type: FilterType,
        override val title: String,
        override val foundProducts: LiveData<List<ProductCardModel>>,
        override val onChanged: (IFilter) -> Unit,
        val items: List<ManufacturerModel>
    ) : IFilter {
        data class ManufacturerModel(
            val title: String,
            val enabled: MutableLiveData<Boolean> = MutableLiveData(false),
            val isSelected: MutableLiveData<Boolean> = MutableLiveData(false),
            val onItemClick: (ManufacturerModel) -> Unit = {
                isSelected.value = !isSelected.value!!
            }
        )

        private val selectedItems: MutableList<Boolean> = items.map {
            it.isSelected.value!!
        }.toMutableList()

        /**
         * Возвращает пункты для отображения.
         */
        val filterItems = ScopedLiveData(items.subListOrNull(0, 4)!!)

        /**
         * Показывает все пункты.
         */
        fun showAllItems() {
            filterItems.setValue(items)
        }

        override val isChanged = MediatorLiveData(false).apply {
            items.forEach {
                addSource(it.isSelected) {
                    value = items.zip(selectedItems).any {
                        it.first.isSelected.value!! != it.second
                    }
                    if (value == true) {
                        onChange()
                    } else {
                        productsCount.value = foundProducts.value!!.size
                    }
                }
            }
        }

        override val anySelected = ScopedLiveData(false)

        override val editingCompleted = SingleLiveEvent<Unit>()

        override val productsCount: MutableLiveData<Int> = MutableLiveData(55)

        override val stringValue = ScopedLiveData("")

        override val enabled = MutableLiveData(true)

        override fun reset() {
            items.forEachIndexed { index, releaseFormModel ->
                releaseFormModel.isSelected.value = false
                selectedItems[index] = false
            }
            apply()
        }

        override fun cancel() {
            selectedItems.forEachIndexed { index, b ->
                items[index].isSelected.value = b
            }
            super.cancel()
        }

        override fun apply() {
            items.forEachIndexed { index, releaseFormModel ->
                selectedItems[index] = releaseFormModel.isSelected.value!!
            }
            anySelected.setValue(selectedItems.any { it })

            val filterStringValue = items.filter { it.isSelected.value!! }.joinToString { it.title }
            if (filterStringValue.isEmpty()) {
                stringValue.setValue("")
            } else {
                stringValue.setValue(": $filterStringValue")
            }
            super.apply()
        }

        val mediator = MediatorLiveData(true).apply {
            fun setEnabled() {
                items.onEach {
                    it.enabled.value = productsCount.value!! > -1 && enabled.value!!
                }
            }

            addSource(productsCount) {
                setEnabled()
            }
            addSource(enabled) {
                setEnabled()
            }
        }

        fun onChange() {
            //if (isChanged.value == true) {
            onChanged(this)
            //}
        }
    }

    data class FilterDiscountsModel(
        override val type: FilterType,
        override val title: String,
        override val foundProducts: LiveData<List<ProductCardModel>>,
        override val onChanged: (IFilter) -> Unit,
        val items: List<DiscountItemModel>,
    ) : IFilter {
        data class DiscountItemModel(
            val title: String,
            val enabled: MutableLiveData<Boolean> = MutableLiveData(false),
            val isSelected: MutableLiveData<Boolean> = MutableLiveData(false),
            val onItemClick: (DiscountItemModel) -> Unit = {
                isSelected.value = !isSelected.value!!
            }
        )

        private val selectedItems: MutableList<Boolean> = items.map {
            it.isSelected.value!!
        }.toMutableList()

        override val isChanged = MediatorLiveData(false).apply {
            items.forEach {
                addSource(it.isSelected) {
                    value = items.zip(selectedItems).any {
                        it.first.isSelected.value!! != it.second
                    }
                    if (value == true) {
                        onChange()
                    } else {
                        productsCount.value = foundProducts.value!!.size
                    }
                }
            }
        }

        override val anySelected = ScopedLiveData(false)

        override val editingCompleted = SingleLiveEvent<Unit>()

        override val productsCount: MutableLiveData<Int> = MutableLiveData(55)

        override val stringValue = ScopedLiveData("")

        override val enabled = MutableLiveData(true)

        override fun reset() {
            items.forEachIndexed { index, releaseFormModel ->
                releaseFormModel.isSelected.value = false
                selectedItems[index] = false
            }
            apply()
        }

        override fun cancel() {
            selectedItems.forEachIndexed { index, b ->
                items[index].isSelected.value = b
            }
            super.cancel()
        }

        override fun apply() {
            items.forEachIndexed { index, releaseFormModel ->
                selectedItems[index] = releaseFormModel.isSelected.value!!
            }
            anySelected.setValue(selectedItems.any { it })

            super.apply()
        }

        val mediator = MediatorLiveData(true).apply {
            fun setEnabled() {
                items.onEach {
                    it.enabled.value = productsCount.value!! > -1 && enabled.value!!
                }
            }

            addSource(productsCount) {
                setEnabled()
            }
            addSource(enabled) {
                setEnabled()
            }
        }

        fun onChange() {
            //if (isChanged.value == true) {
            onChanged(this)
            //}
        }
    }

    data class FilterNosologyModel(
        override val type: FilterType,
        override val title: String,
        override val foundProducts: LiveData<List<ProductCardModel>>,
        override val onChanged: (IFilter) -> Unit,
        val items: List<NosologyModel>,
    ) : IFilter {
        data class NosologyModel(
            val title: String,
        ) {
            fun onItemClick() {

            }
        }

        fun showAll() {

        }

        override val isChanged = MediatorLiveData(false)

        override val anySelected = ScopedLiveData(false)

        override val editingCompleted = SingleLiveEvent<Unit>()

        override val productsCount: MutableLiveData<Int> = MutableLiveData(0)

        override val stringValue = ScopedLiveData("")

        override val enabled = MutableLiveData(true)

        override fun reset() {
            apply()
        }

        override fun cancel() {
            super.cancel()
        }

        override fun apply() {
            super.apply()
        }
    }

    data class FilterBrandModel(
        override val type: FilterType,
        override val title: String,
        override val foundProducts: LiveData<List<ProductCardModel>>,
        override val onChanged: (IFilter) -> Unit,
        val items: List<BrandItemModel>,
    ) : IFilter {
        data class BrandItemModel(
            val title: String,
            val enabled: MutableLiveData<Boolean> = MutableLiveData(false),
            val isSelected: MutableLiveData<Boolean> = MutableLiveData(false),
            val onItemClick: (BrandItemModel) -> Unit = { isSelected.value = !isSelected.value!! }
        )

        private val selectedItems: MutableList<Boolean> = items.map {
            it.isSelected.value!!
        }.toMutableList()

        /**
         * Возвращает пункты для отображения.
         */
        val filterItems = ScopedLiveData(items.subListOrNull(0, 4)!!)

        /**
         * Показывает все пункты.
         */
        fun showAllItems() {
            filterItems.setValue(items)
        }

        override val isChanged = MediatorLiveData(false).apply {
            items.forEach {
                addSource(it.isSelected) {
                    value = items.zip(selectedItems).any {
                        it.first.isSelected.value!! != it.second
                    }
                    if (value == true) {
                        onChange()
                    } else {
                        productsCount.value = foundProducts.value!!.size
                    }
                }
            }
        }

        override val anySelected = ScopedLiveData(false)

        override val editingCompleted = SingleLiveEvent<Unit>()

        override val productsCount: MutableLiveData<Int> = MutableLiveData(55)

        override val stringValue = ScopedLiveData("")

        override val enabled = MutableLiveData(true)

        override fun reset() {
            items.forEachIndexed { index, releaseFormModel ->
                releaseFormModel.isSelected.value = false
                selectedItems[index] = false
            }
            apply()
        }

        override fun cancel() {
            selectedItems.forEachIndexed { index, b ->
                items[index].isSelected.value = b
            }
            super.cancel()
        }

        override fun apply() {
            items.forEachIndexed { index, releaseFormModel ->
                selectedItems[index] = releaseFormModel.isSelected.value!!
            }
            anySelected.setValue(selectedItems.any { it })

            val filterStringValue = items.filter { it.isSelected.value!! }.joinToString { it.title }
            if (filterStringValue.isEmpty()) {
                stringValue.setValue("")
            } else {
                stringValue.setValue(": $filterStringValue")
            }
            super.apply()
        }

        val mediator = MediatorLiveData(true).apply {
            fun setEnabled() {
                items.onEach {
                    it.enabled.value = productsCount.value!! > -1 && enabled.value!!
                }
            }

            addSource(productsCount) {
                setEnabled()
            }
            addSource(enabled) {
                setEnabled()
            }
        }

        fun onChange() {
            //if (isChanged.value == true) {
            onChanged(this)
            //}
        }
    }

    data class FilterCountryModel(
        override val type: FilterType,
        override val title: String,
        override val foundProducts: LiveData<List<ProductCardModel>>,
        override val onChanged: (IFilter) -> Unit,
        val items: List<CountryItemModel>,
    ) : IFilter {
        data class CountryItemModel(
            val title: String,
            val enabled: MutableLiveData<Boolean> = MutableLiveData(false),
            val isSelected: MutableLiveData<Boolean> = MutableLiveData(false),
            val onItemClick: (CountryItemModel) -> Unit = { isSelected.value = !isSelected.value!! }
        )

        private val selectedItems: MutableList<Boolean> = items.map {
            it.isSelected.value!!
        }.toMutableList()

        /**
         * Возвращает пункты для отображения.
         */
        val filterItems = ScopedLiveData(items.subListOrNull(0, 4)!!)

        /**
         * Показывает все пункты.
         */
        fun showAllItems() {
            filterItems.setValue(items)
        }

        override val isChanged = MediatorLiveData(false).apply {
            items.forEach {
                addSource(it.isSelected) {
                    value = items.zip(selectedItems).any {
                        it.first.isSelected.value!! != it.second
                    }
                    if (value == true) {
                        onChange()
                    } else {
                        productsCount.value = foundProducts.value!!.size
                    }
                }
            }
        }

        override val anySelected = ScopedLiveData(false)

        override val editingCompleted = SingleLiveEvent<Unit>()

        override val productsCount: MutableLiveData<Int> = MutableLiveData(55)

        override val stringValue = ScopedLiveData("")

        override val enabled = MutableLiveData(true)

        override fun reset() {
            items.forEachIndexed { index, releaseFormModel ->
                releaseFormModel.isSelected.value = false
                selectedItems[index] = false
            }
            apply()
        }

        override fun cancel() {
            selectedItems.forEachIndexed { index, b ->
                items[index].isSelected.value = b
            }
            super.cancel()
        }

        override fun apply() {
            items.forEachIndexed { index, releaseFormModel ->
                selectedItems[index] = releaseFormModel.isSelected.value!!
            }
            anySelected.setValue(selectedItems.any { it })

            val filterStringValue = items.filter { it.isSelected.value!! }.joinToString { it.title }
            if (filterStringValue.isEmpty()) {
                stringValue.setValue("")
            } else {
                stringValue.setValue(": $filterStringValue")
            }
            super.apply()
        }

        val mediator = MediatorLiveData(true).apply {
            fun setEnabled() {
                items.onEach {
                    it.enabled.value = productsCount.value!! > -1 && enabled.value!!
                }
            }

            addSource(productsCount) {
                setEnabled()
            }
            addSource(enabled) {
                setEnabled()
            }
        }

        fun onChange() {
            //if (isChanged.value == true) {
            onChanged(this)
            //}
        }
    }

    data class FilterActiveSubstanceModel(
        override val type: FilterType,
        override val title: String,
        override val foundProducts: LiveData<List<ProductCardModel>>,
        override val onChanged: (IFilter) -> Unit,
        val items: List<ActiveSubstanceItemModel>,
    ) : IFilter {
        data class ActiveSubstanceItemModel(
            val title: String,
            val enabled: MutableLiveData<Boolean> = MutableLiveData(false),
            val isSelected: MutableLiveData<Boolean> = MutableLiveData(false),
            val onItemClick: (ActiveSubstanceItemModel) -> Unit = {
                isSelected.value = !isSelected.value!!
            }
        )

        private val selectedItems: MutableList<Boolean> = items.map {
            it.isSelected.value!!
        }.toMutableList()

        /**
         * Возвращает пункты для отображения.
         */
        val filterItems = ScopedLiveData(items.subListOrNull(0, 4)!!)

        /**
         * Показывает все пункты.
         */
        fun showAllItems() {
            filterItems.setValue(items)
        }

        override val isChanged = MediatorLiveData(false).apply {
            items.forEach {
                addSource(it.isSelected) {
                    value = items.zip(selectedItems).any {
                        it.first.isSelected.value!! != it.second
                    }
                    if (value == true) {
                        onChange()
                    } else {
                        productsCount.value = foundProducts.value!!.size
                    }
                }
            }
        }

        override val anySelected = ScopedLiveData(false)

        override val editingCompleted = SingleLiveEvent<Unit>()

        override val productsCount: MutableLiveData<Int> = MutableLiveData(55)

        override val stringValue = ScopedLiveData("")

        override val enabled = MutableLiveData(true)

        override fun reset() {
            items.forEachIndexed { index, releaseFormModel ->
                releaseFormModel.isSelected.value = false
                selectedItems[index] = false
            }
            apply()
        }

        override fun cancel() {
            selectedItems.forEachIndexed { index, b ->
                items[index].isSelected.value = b
            }
            super.cancel()
        }

        override fun apply() {
            items.forEachIndexed { index, releaseFormModel ->
                selectedItems[index] = releaseFormModel.isSelected.value!!
            }
            anySelected.setValue(selectedItems.any { it })

            val filterStringValue = items.filter { it.isSelected.value!! }.joinToString { it.title }
            if (filterStringValue.isEmpty()) {
                stringValue.setValue("")
            } else {
                stringValue.setValue(": $filterStringValue")
            }
            super.apply()
        }

        val mediator = MediatorLiveData(true).apply {
            fun setEnabled() {
                items.onEach {
                    it.enabled.value = productsCount.value!! > -1 && enabled.value!!
                }
            }

            addSource(productsCount) {
                setEnabled()
            }
            addSource(enabled) {
                setEnabled()
            }
        }

        fun onChange() {
            //if (isChanged.value == true) {
            onChanged(this)
            //}
        }
    }

    data class FilterAllModel(
        val filters: List<IFilter>,
        override val type: FilterType = FilterType.ALL,
        override val title: String = "Все фильтры",
        override val foundProducts: LiveData<List<ProductCardModel>>,
        override val onChanged: (IFilter) -> Unit,
    ) : IFilter {

        override val anySelected = ScopedLiveData(false)

        override val isChanged: LiveData<Boolean> = MediatorLiveData(false).apply {
            filters.forEach {
                addSource(it.isChanged) {
                    value = filters.any { it.isChanged.value!! }
                    if (value == true) {
                        onChange()
                    } else {
                        productsCount.value = foundProducts.value!!.size
                    }
                }
            }
        }

        val countSelected = MediatorLiveData<Int?>(null).apply {
            fun checkAnySelected() {
                postValue(
                    filters.filter { it.anySelected.value!! }.size.let { if (it > 0) it else null }
                )
            }

            addSource(anySelected) {
                checkAnySelected()
            }

            filters.forEach {
                addSource(it.anySelected) {
                    checkAnySelected()
                }
            }
        }

        override val editingCompleted = SingleLiveEvent<Unit>()

        override val productsCount: MutableLiveData<Int> = MutableLiveData(55)

        override val stringValue = ScopedLiveData("")

        override val enabled = MutableLiveData(true)

        override fun reset() {
            filters.onEach {
                it.reset()
            }
            apply()
        }

        override fun cancel() {
            filters.onEach {
                it.cancel()
            }
            super.cancel()
        }

        override fun apply() {
            super.apply()
            filters.onEach {
                it.apply()
            }
            anySelected.setValue(filters.any { it.anySelected.value!! })
        }

        val mediator = MediatorLiveData(true).apply {
            addSource(productsCount) { count ->
                filters.onEach {
                    it.enabled.value = count > -1
                }
            }
        }

        fun onChange() {
            //if (isChanged.value == true) {
            onChanged(this)
            //}
        }
    }

}