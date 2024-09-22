package ru.apteka.components.data.models

import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import ru.apteka.components.data.utils.getPhoneRaw
import ru.tinkoff.decoro.MaskImpl
import ru.tinkoff.decoro.slots.PredefinedSlots
import ru.tinkoff.decoro.slots.Slot
import ru.tinkoff.decoro.watchers.MaskFormatWatcher


/**
 * Представляет модель поля ввода номера телефона.
 */
class PhoneInputModel {
    /**
     * Возвращает или устанавливает номер телефона.
     */
    val phone = MutableLiveData("")

    /**
     * Возвращает номер телефона без маски.
     */
    val phoneRaw
        get() = getPhoneRaw(phone.value!!)

    companion object {
        /**
         * Устанавливает маску ввода номера.
         * @param phoneMask [String] - Маска.
         */
        @JvmStatic
        @BindingAdapter("app:phoneMask")
        fun TextView.setPhoneMask(phoneMask: String) {
            MaskFormatWatcher(
                MaskImpl(
                    Slot.copySlotArray(PredefinedSlots.RUS_PHONE_NUMBER).apply {
                        this[3].flags = this[3].flags or Slot.RULE_FORBID_CURSOR_MOVE_LEFT
                    },
                    true
                )
            ).installOnAndFill(this)
        }
    }
}