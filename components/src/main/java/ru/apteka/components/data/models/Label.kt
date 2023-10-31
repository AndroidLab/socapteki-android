package ru.apteka.components.data.models

import android.content.Context
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import ru.apteka.components.R


enum class Label(@StringRes val text:Int, @ColorRes val textColor: Int, @ColorRes val backgroundColor: Int) {
    HIT_SALES(R.string.label_hit_sales, R.color.white, R.color.light_black),
    PRODUCT_DAY(R.string.label_product_day, R.color.white, R.color.dark_grey),
    WITHOUT_PRESCRIPTION(R.string.label_without_prescription, R.color.light_black, R.color.gold),
    CHECKED_SPECIALIST(R.string.label_checked_specialist, R.color.white, R.color.red),
    ADVERT(R.string.label_advert, R.color.light_black, R.color.light_blue);

    fun getTextColor(context: Context, label: Label) = ContextCompat.getColor(context, label.textColor)
    fun getBackgroundColor(context: Context, label: Label) = ContextCompat.getColor(context, label.backgroundColor)
}

