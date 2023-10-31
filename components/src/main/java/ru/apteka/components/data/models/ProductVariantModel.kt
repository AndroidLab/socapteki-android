package ru.apteka.components.data.models

import android.os.Parcelable
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue


@Parcelize
class ProductVariantModel(
    val title: String,
    val variants: List<VariantItem>
) : Parcelable {
    @Parcelize
    data class VariantItem(
        val name: String,
        val isSelected: @RawValue MutableLiveData<Boolean> = MutableLiveData(false),
        val onItemClick: () -> Unit = {
            isSelected.value = true
        }
    ) : Parcelable

    @IgnoredOnParcel
    val mediator = MediatorLiveData(true).apply {
        variants.forEachIndexed { index, variantItem ->
            if (index == 0) {
                variantItem.isSelected.postValue(true)
            }
            addSource(variantItem.isSelected) {
                if (it == true) {
                    variants.forEach {
                        if (it != variantItem) {
                            it.isSelected.value = false
                        }
                    }
                }
            }
        }
    }

}