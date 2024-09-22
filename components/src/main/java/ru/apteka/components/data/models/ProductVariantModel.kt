package ru.apteka.components.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue


@Parcelize
class ProductVariantModel(
    val title: String,
    val variants: @RawValue List<VariantItem>,
    val _onItemSelected: (VariantItem?) -> Unit
) : SelectableModel<ProductVariantModel.VariantItem>(
    items = variants,
    onItemSelected = _onItemSelected
), Parcelable {
    @Parcelize
    data class VariantItem(
        val name: String,
    ) : SelectableItem(), Parcelable

}