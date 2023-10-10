package ru.apteka.common.data.utils

import android.content.Context
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.text.Html
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.LayoutRes
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.content.ContextCompat
import androidx.core.view.*
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputLayout
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.google.android.flexbox.FlexboxLayout
import ru.apteka.resources.BR
import ru.apteka.resources.R
import java.util.Calendar

/**
 * Изменяет видимости представления.
 * @param value [Boolean] - флаг, отвечающий за видимость представления.
 */
@BindingAdapter("app:visibleIf")
fun View.visibleIf(value: Boolean?) {
    visibility = if (value == true) View.VISIBLE else View.GONE
}

/**
 * Устанавливает текст сообщения.
 * @param value [Any] Значение для преобразования в текст.
 * @param isHtml Флаг является ли текст html.
 */
@BindingAdapter("app:setText", "app:isHtml", requireAll = false)
fun TextView.setText(value: Any?, isHtml: Boolean = false) {
    val _text = if (value is Int && value != 0) {
        context.getString(value.toInt())
    } else {
        value.toString()
    }
    text = if (isHtml) {
        Html.fromHtml(_text, Html.FROM_HTML_MODE_COMPACT)
    } else {
        _text
    }
}

/**
 * Устанавливает размер текста.
 * @param view [View] - Отображение.
 * @param value [Float] - Значение для преобразования в sp.
 */
@BindingAdapter("app:setTextSize")
fun setTextSize(textView: TextView, value: Float) {
    textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, value)
}

/**
 * Устанавливает отступы.
 * @param view [View]
 * @param marginTop [Int]
 * @param marginBottom [Int]
 */
@BindingAdapter("app:layoutMarginTop", "app:layoutMarginBottom", requireAll = false)
fun setLayoutMargin(view: View, marginTop: Int?, marginBottom: Int?) {
    if (marginTop != null || marginBottom != null) {
        val lp = LinearLayout.LayoutParams(view.layoutParams.width, view.layoutParams.height)
        if (marginTop != null) {
            view.layoutParams =
                lp.apply {
                    setMargins(
                        view.marginLeft,
                        marginTop.dp,
                        view.marginRight,
                        view.marginBottom
                    )
                }
        }
        if (marginBottom != null) {
            view.layoutParams =
                lp.apply {
                    setMargins(
                        view.marginLeft,
                        view.marginTop,
                        view.marginRight,
                        marginBottom.dp
                    )
                }
        }
    }
}

/**
 * Устанавливает ширину и высоту разметки.
 * @param view [View]
 * @param layoutWidth [Int]
 * @param layoutHeight [Int]
 */
@BindingAdapter("app:extra_layout_width", "app:extra_layout_height", requireAll = false)
fun setLayoutHeight(view: View, layoutWidth: Int?, layoutHeight: Int?) {
    val lp = view.layoutParams
    lp.width = layoutWidth?.dp ?: ViewGroup.LayoutParams.MATCH_PARENT
    lp.height = layoutHeight?.dp ?: ViewGroup.LayoutParams.WRAP_CONTENT
    view.layoutParams = lp
}

/**
 * Устанавливает индикатор прогресса в поле автокомплита.
 * @param textInputLayout Поле ввода.
 * @param defEndIconDrawable Иконка по умолчанию.
 * @param liveSearchProgress Флаг отображения прогресса.
 */
@BindingAdapter("app:defEndIconDrawable", "app:liveSearchProgress", requireAll = false)
fun setLiveSearchProgress(
    textInputLayout: TextInputLayout,
    defEndIconDrawable: Drawable?,
    liveSearchProgress: Boolean?
) {
    if (liveSearchProgress == true) {
        textInputLayout.endIconDrawable = CircularProgressDrawable(textInputLayout.context).apply {
            setStyle(CircularProgressDrawable.DEFAULT)
            setColorSchemeColors(textInputLayout.context.getColor(R.color.color_primary))
            start()
        }
    } else {
        textInputLayout.endIconDrawable = defEndIconDrawable
    }
}


/**
 * Устанавливает адаптер списка.
 * @param view Отображение списка.
 * @param listAdapter Адаптер.
 */
@BindingAdapter("app:adapter")
fun setRecyclerViewAdapter(
    view: RecyclerView,
    listAdapter: ListAdapter<Any, RecyclerView.ViewHolder>?
) {
    view.adapter = listAdapter
}


/**
 * Устанавливает текст ошибки в [TextInputLayout].
 * @param layout Разметка ввода.
 * @param errorText Текст ошибки.
 */
@BindingAdapter("app:errorText")
fun setErrorTextToTextInputLayout(layout: TextInputLayout, errorText: String?) {
    layout.error = errorText
}

/**
 * Устанавливает изображение из ресурса.
 * @param glideImageRes Ресурс изображения.
 */
@BindingAdapter("app:glideImageRes")
fun ImageView.setGlideImage(
    glideImageRes: Any?
) {
    glideImageRes?.let {
        Glide
            .with(context)
            .load(it)
            //.placeholder(progressDrawable)
            .into(this)
    }
}

/**
 * Устанавливает цвет выделения текста в [EditText].
 * @param editText Виджет.
 * @param color Цвет выделения.
 */
@BindingAdapter("app:highlightColor")
fun setHighlightColor(
    editText: EditText,
    @ColorRes color: Int?
) {
    if (color != null) {
        editText.apply {
            highlightColor = ContextCompat.getColor(context, color)
        }
    }
}

/**
 * Устанавливает цвет векторного изображения в [ImageView].
 * @param color Цвет выделения.
 */
@BindingAdapter("app:imageTint")
fun ImageView.setImageTint(@ColorInt color: Int?) {
    if (color != null && color != 0) {
        setColorFilter(color)
    }
}


/**
 * Устанавливает форматированную дату в [TextView].
 * @param timeInMills Время в секундах.
 * @param lowerCase Регистр.
 */
@BindingAdapter("app:formatDateFromSec", "app:lowerCase")
fun TextView.setFormatDateFromSec(timeInSec: Int?, lowerCase: Boolean = false) {
    setFormatDateFromMills(timeInSec?.let { it * 1000L }, lowerCase)
}

/**
 * Устанавливает форматированную дату в [TextView].
 * @param timeInMills Время в мс.
 * @param lowerCase Регистр.
 */
@BindingAdapter("app:formatDateFromMills", "app:lowerCase")
fun TextView.setFormatDateFromMills(timeInMills: Long?, lowerCase: Boolean = false) {
    if (timeInMills != null) {
        val _text: String = getFormatDate(context, timeInMills).run {
            if (lowerCase)
                this.toString().lowercase()
            else
                this
        }
        text = _text
    }
}

/**
 * Возвращает форматированную дату.
 * @param context Контекст.
 * @param timeInMills Время в мс.
 */
fun getFormatDate(context: Context, timeInMills: Long) =
    Calendar((timeInMills / 1000).toInt()).formatDate(context)


/**
 * Устанавливает зачеркнутый текст.
 */
@BindingAdapter("app:isStrikethrough")
fun TextView.isStrikethrough(
    value: Boolean = false
) {
    paintFlags = if (value) paintFlags or Paint.STRIKE_THRU_TEXT_FLAG else paintFlags
}


/**
 * Создает представление из шаблона для каждого элемента списка.
 * @param items List<T>? Список элементов.
 * @param template Int? Идентификатор ресурса шаблона.
 * @param gap Int? Размер пространства между отображениями в dp.
 * @param lifecycleOwner [LifecycleOwner].
 */
@BindingAdapter(
    value = ["app:forItems", "app:useTemplate", "app:lifecycleOwner"],
    requireAll = false
)
fun <T> ViewGroup.inflateTemplateByItems(
    items: List<T>?,
    @LayoutRes template: Int?,
    lifecycleOwner: LifecycleOwner?
) {
    if (items != null && template != null) {
        if (items.isEmpty()) {
            this.removeAllViews()
            return
        }
        val inflater = LayoutInflater.from(context)
        if (this.childCount == 0) {
            items.forEach { item ->
                val viewBinding =
                    DataBindingUtil.inflate<ViewDataBinding>(inflater, template, this, true)
                viewBinding.lifecycleOwner = lifecycleOwner
                viewBinding.setVariable(BR.bindingItem, item)
                viewBinding.executePendingBindings()
            }
            return
        }
        if (this.childCount == items.size) {
            this.children.forEachIndexed { index, view ->
                val viewBinding = DataBindingUtil.getBinding<ViewDataBinding>(view)
                viewBinding?.lifecycleOwner = lifecycleOwner
                viewBinding?.setVariable(BR.bindingItem, items[index])
                viewBinding?.executePendingBindings()
            }
            return
        }
        if (this.childCount != items.size) {
            if (this.childCount > items.size) {
                this.removeViews(0, this.childCount - items.size)
            } else {
                for (i in (items.size - this.childCount) downTo 1) {
                    DataBindingUtil.inflate<ViewDataBinding>(
                        inflater,
                        template,
                        this,
                        true
                    )
                }
            }
            inflateTemplateByItems(items, template, lifecycleOwner)
        }
    } else {
        this.removeAllViews()
    }
}
