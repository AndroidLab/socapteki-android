package ru.apteka.catalog.data.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import ru.apteka.components.data.utils.single_live_event.SingleLiveEvent


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
     * Возвращает флаг, что в фильтре есть какие то изменения.
     */
    val anySelected: LiveData<Boolean>

    /**
     * Возвращает флаг, что в фильтре произошли изменения относительно предыдущего состояния (Используется для изменения состояния кнопки).
     */
    val isChanged: LiveData<Boolean>

    /**
     * Возвращает флаг, что работа с фильтром завершена.
     */
    val editingCompleted: SingleLiveEvent<Unit>

    fun reset()

    fun cancel() {
        editingCompleted.call()
    }

    fun apply() {
        editingCompleted.call()
    }


    data class FilterPriceModel(
        override val type: FilterType,
        override val title: String,
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

        private val _anySelected = MutableLiveData(false)

        override val anySelected: LiveData<Boolean> = _anySelected

        override val editingCompleted = SingleLiveEvent<Unit>()

        override fun reset() {
            _selectedMinPrice = minPrice
            _selectedMaxPrice = maxPrice
            selectedMinProgress.value = _selectedMinPrice
            selectedMaxProgress.value = _selectedMaxPrice
            apply()
        }

        override fun cancel() {
            selectedMinProgress.value = _selectedMinPrice
            selectedMaxProgress.value = _selectedMaxPrice
            super.cancel()
        }

        override fun apply() {
            _selectedMinPrice = selectedMinProgress.value!!
            _selectedMaxPrice = selectedMaxProgress.value!!
            _anySelected.value = _selectedMinPrice != minPrice || _selectedMaxPrice != maxPrice
            super.apply()
        }
    }

    data class FilterReleaseFormModel(
        override val type: FilterType,
        override val title: String,
        val items: List<ReleaseFormModel>
    ) : IFilter {
        data class ReleaseFormModel(
            val title: String,
            val desk: String? = null,
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
                }
            }
        }

        private val _anySelected = MutableLiveData(false)

        override val anySelected: LiveData<Boolean> = _anySelected

        override val editingCompleted = SingleLiveEvent<Unit>()

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
            _anySelected.value = selectedItems.any { it }

            super.apply()
        }
    }

    data class FilterManufacturerModel(
        override val type: FilterType,
        override val title: String,
        val items: List<ManufacturerModel>
    ) : IFilter {
        data class ManufacturerModel(
            val title: String,
            val isSelected: MutableLiveData<Boolean> = MutableLiveData(false),
            val onItemClick: (ManufacturerModel) -> Unit = {
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
                }
            }
        }

        private val _anySelected = MutableLiveData(false)

        override val anySelected: LiveData<Boolean> = _anySelected

        override val editingCompleted = SingleLiveEvent<Unit>()

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
            _anySelected.value = selectedItems.any { it }

            super.apply()
        }
    }

    data class FilterDiscountsModel(
        override val type: FilterType,
        override val title: String,
        val desc: String,
    ) : IFilter {
        private var isSelected = false

        val isSelectedLiveData: MutableLiveData<Boolean> = MutableLiveData(false)

        override val isChanged = MediatorLiveData(false).apply {
            addSource(isSelectedLiveData) {
                value = it != isSelected
            }
        }

        private val _anySelected = MutableLiveData(false)

        override val anySelected: LiveData<Boolean> = _anySelected

        override val editingCompleted = SingleLiveEvent<Unit>()

        override fun reset() {
            isSelected = false
            isSelectedLiveData.value = isSelected
            apply()
        }

        override fun cancel() {
            isSelectedLiveData.value = isSelected
            super.cancel()
        }

        override fun apply() {
            isSelected = isSelectedLiveData.value!!
            _anySelected.value = isSelected
            super.apply()
        }
    }

    data class FilterNosologyModel(
        override val type: FilterType,
        override val title: String,
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

        private val _anySelected = MutableLiveData(false)

        override val anySelected: LiveData<Boolean> = _anySelected

        override val editingCompleted = SingleLiveEvent<Unit>()

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
        val items: List<BrandItemModel>,
    ) : IFilter {
        data class BrandItemModel(
            val title: String,
            val isSelected: MutableLiveData<Boolean> = MutableLiveData(false),
            val onItemClick: (BrandItemModel) -> Unit = { isSelected.value = !isSelected.value!! }
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
                }
            }
        }

        private val _anySelected = MutableLiveData(false)

        override val anySelected: LiveData<Boolean> = _anySelected

        override val editingCompleted = SingleLiveEvent<Unit>()

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
            _anySelected.value = selectedItems.any { it }

            super.apply()
        }
    }

    data class FilterCountryModel(
        override val type: FilterType,
        override val title: String,
        val items: List<CountryItemModel>,
    ) : IFilter {
        data class CountryItemModel(
            val title: String,
            val isSelected: MutableLiveData<Boolean> = MutableLiveData(false),
            val onItemClick: (CountryItemModel) -> Unit = { isSelected.value = !isSelected.value!! }
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
                }
            }
        }

        private val _anySelected = MutableLiveData(false)

        override val anySelected: LiveData<Boolean> = _anySelected

        override val editingCompleted = SingleLiveEvent<Unit>()

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
            _anySelected.value = selectedItems.any { it }

            super.apply()
        }
    }

    data class FilterActiveSubstanceModel(
        override val type: FilterType,
        override val title: String,
        val items: List<ActiveSubstanceItemModel>,
    ) : IFilter {
        data class ActiveSubstanceItemModel(
            val title: String,
            val isSelected: MutableLiveData<Boolean> = MutableLiveData(false),
            val onItemClick: (ActiveSubstanceItemModel) -> Unit = {
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
                }
            }
        }

        private val _anySelected = MutableLiveData(false)

        override val anySelected: LiveData<Boolean> = _anySelected

        override val editingCompleted = SingleLiveEvent<Unit>()

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
            _anySelected.value = selectedItems.any { it }

            super.apply()
        }
    }

    data class FilterAllModel(
        val filters: List<IFilter>,
        override val type: FilterType = FilterType.ALL,
        override val title: String = "Все фильтры"
    ) : IFilter {

        private val _anySelected = MutableLiveData(false)

        override val anySelected: LiveData<Boolean> = _anySelected

        override val isChanged: LiveData<Boolean> = MediatorLiveData(false).apply {
            filters.forEach {
                addSource(it.isChanged) {
                    value = filters.any { it.isChanged.value!! }
                }
            }
        }

        val globalAnySelected = MediatorLiveData<Boolean>().apply {
            fun checkAnySelected() {
                postValue(
                    anySelected.value!! || filters.any { it.anySelected.value!! }
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
            _anySelected.value = filters.any { it.anySelected.value!! }
        }

    }

}