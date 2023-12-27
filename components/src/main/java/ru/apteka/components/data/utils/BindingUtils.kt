package ru.apteka.components.data.utils

import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.LayoutRes
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.ContextCompat
import androidx.core.view.*
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.findFragment
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.github.florent37.expansionpanel.ExpansionHeader
import com.github.florent37.expansionpanel.ExpansionLayout
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.card.MaterialCardView
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import ru.apteka.components.BR
import ru.apteka.components.R


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
@BindingAdapter("app:extraText", "app:extraTextArg", "app:isHtml", requireAll = false)
fun TextView.setExtraText(
    value: Any?,
    extraTextArg: List<Any>?,
    isHtml: Boolean = false
) {
    val _text = if (extraTextArg == null) {
        context.getStringFrom(value ?: "")
    } else {
        String.format(context.getStringFrom(value ?: ""), *extraTextArg.toTypedArray())
    }
    text = if (isHtml) {
        getSpannedFromHtml(_text)
    } else {
        _text
    }
}

/**
 * Устанавливает размер текста.
 * @param textView [TextView] - Отображение.
 * @param value [Float] - Значение для преобразования в sp.
 */
@BindingAdapter("app:extraTextSize")
fun TextView.setExtraTextSize(value: Float) {
    setTextSize(TypedValue.COMPLEX_UNIT_SP, value)
}

/**
 * Устанавливает стиль текста.
 * @param textView [TextView] - Отображение.
 * @param value [Int] - Значение стиля.
 */
@BindingAdapter("app:extraTextStyle")
fun setExtraTextStyle(textView: TextView, value: Int?) {
    textView.setTypeface(
        null, when (value) {
            0 -> Typeface.NORMAL
            1 -> Typeface.BOLD
            2 -> Typeface.ITALIC
            else -> Typeface.NORMAL
        }
    )
}

/**
 * Устанавливает отступы.
 * @param view [View]
 * @param top [Int]
 * @param bottom [Int]
 */
@BindingAdapter(
    "app:layoutMarginStart",
    "app:layoutMarginTop",
    "app:layoutMarginEnd",
    "app:layoutMarginBottom",
    requireAll = false
)
fun View.setLayoutMargin(
    start: Int? = null,
    top: Int? = null,
    end: Int? = null,
    bottom: Int? = null
) {
    if (start != null || top != null || end != null || bottom != null) {
        val lp = layoutParams as ViewGroup.MarginLayoutParams
        if (start != null) {
            layoutParams =
                lp.apply {
                    setMargins(
                        start.dp,
                        marginTop,
                        marginRight,
                        marginBottom
                    )
                }
        }
        if (top != null) {
            layoutParams =
                lp.apply {
                    setMargins(
                        marginLeft,
                        top.dp,
                        marginRight,
                        marginBottom
                    )
                }
        }
        if (end != null) {
            layoutParams =
                lp.apply {
                    setMargins(
                        marginLeft,
                        marginTop,
                        end.dp,
                        marginBottom
                    )
                }
        }
        if (bottom != null) {
            layoutParams =
                lp.apply {
                    setMargins(
                        marginLeft,
                        marginTop,
                        marginRight,
                        bottom.dp
                    )
                }
        }
    }
}

/**
 * Устанавливает отступы.
 * @param view [View]
 * @param paddingTop [Int]
 * @param paddingBottom [Int]
 */
@BindingAdapter(
    "app:layoutPaddingStart",
    "app:layoutPaddingTop",
    "app:layoutPaddingEnd",
    "app:layoutPaddingBottom",
    requireAll = false
)
fun setLayoutPadding(
    view: View,
    paddingStart: Int?,
    paddingTop: Int?,
    paddingEnd: Int?,
    paddingBottom: Int?
) {
    if (paddingStart != null || paddingTop != null || paddingEnd != null || paddingBottom != null) {
        if (paddingStart != null) {
            view.setPadding(
                paddingStart.dp,
                view.paddingTop,
                view.paddingRight,
                view.paddingBottom
            )
        }
        if (paddingTop != null) {
            view.setPadding(
                view.paddingLeft,
                paddingTop.dp,
                view.paddingRight,
                view.paddingBottom
            )
        }
        if (paddingEnd != null) {
            view.setPadding(
                view.paddingLeft,
                view.paddingTop,
                paddingEnd.dp,
                view.paddingBottom
            )
        }
        if (paddingBottom != null) {
            view.setPadding(
                view.paddingLeft,
                view.paddingTop,
                view.paddingRight,
                paddingBottom.dp
            )
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
    lp.width = if (layoutWidth == -1 || layoutWidth == -2) {
        layoutWidth
    } else {
        layoutWidth?.dp ?: ViewGroup.LayoutParams.MATCH_PARENT
    }
    lp.height = if (layoutHeight == -1 || layoutHeight == -2) {
        layoutHeight
    } else {
        layoutHeight?.dp ?: ViewGroup.LayoutParams.WRAP_CONTENT
    }
    view.layoutParams = lp
}

/**
 * Устанавливает адаптер выбора для автокомплита.
 * @param autoCompleteItems Элементы выбора.
 * @param onAutoCompleteItemClick Обработчик клика на пункте списка.
 */
@BindingAdapter(
    value = ["app:autoCompleteItems", "app:onAutoCompleteItemClick"],
    requireAll = false
)
fun MaterialAutoCompleteTextView.setAutoCompleteItems(
    autoCompleteItems: List<String>?,
    onAutoCompleteItemClick: AdapterView.OnItemClickListener?
) {
    setAdapter(
        ArrayAdapter(context, R.layout.item_auto_complete_holder, autoCompleteItems ?: emptyList())
    )
    /*if (inputType != InputType.TYPE_NULL) {
        showDropDown()
    }*/
    onItemClickListener = onAutoCompleteItemClick
}

/**
 * Устанавливает SingleLine helper для [TextInputLayout].
 */
@BindingAdapter("app:helperTextSingleLine")
fun TextInputLayout.setHelperTextSingleLine(value: Boolean?) {
    findViewById<TextView>(R.id.textinput_helper_text)?.let {
        it.isSingleLine = true
    }
}


/**
 * Устанавливает ширину бордера CardView.
 * @param cardView [MaterialCardView].
 * @param borderWidth [Int].
 */
@BindingAdapter("app:borderWidth")
fun setBorderWidth(cardView: MaterialCardView, borderWidth: Int?) {
    if (borderWidth == null) {
        cardView.strokeWidth = 0
    } else {
        cardView.strokeWidth = borderWidth.dp
    }
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
@BindingAdapter("app:glideImageRes", "app:useStub", requireAll = false)
fun ImageView.setGlideImage(
    glideImageRes: Any?,
    useStub: Drawable? = ContextCompat.getDrawable(context, R.drawable.image_stub)
) {
    glideImageRes?.let {
        Glide
            .with(context)
            .load(it)
            .placeholder(useStub)
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
 * Устанавливает margin для итема списка.
 */
@BindingAdapter("app:marginItemHorizontal", "app:marginItemVertical", requireAll = false)
fun RecyclerView.marginItemDecoration(
    marginItemHorizontal: Int,
    marginItemVertical: Int
) {
    addItemDecoration(
        MarginItemDecoration(
            horizontal = marginItemHorizontal.dp,
            vertical = marginItemVertical.dp
        )
    )
}


/**
 * Устанавливает доскроливание с привязкой к центру.
 */
@BindingAdapter("app:linearSnapHelper")
fun RecyclerView.linearSnapHelper(
    linearSnapHelper: Boolean?
) {
    if (linearSnapHelper == true) {
        LinearSnapHelper().attachToRecyclerView(this)
    }
}

/**
 * Устанавливает постраничный индикатор.
 */
@BindingAdapter(
    "app:circlePagerIndicator",
    "app:colorActive",
    "app:colorInactive",
    requireAll = false
)
fun RecyclerView.circlePagerIndicator(
    verticalOffset: Int?,
    colorActive: Int?,
    colorInactive: Int?
) {
    if (verticalOffset != null) {
        addItemDecoration(
            CirclePagerIndicatorDecoration(
                verticalOffset,
                colorActive ?: Color.WHITE,
                colorInactive ?: ContextCompat.getColor(context, R.color.page_indicator_inactive)
            )
        )
    }
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
    items: Collection<T>?,
    @LayoutRes template: Int?,
    lifecycleOwner: LifecycleOwner?
) {
    val _lifecycleOwner: LifecycleOwner? = lifecycleOwner
        ?: try {
            findFragment<Fragment>().viewLifecycleOwner
        } catch (e: Throwable) {
            null
        }

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
                viewBinding.lifecycleOwner = _lifecycleOwner
                viewBinding.setVariable(BR.bindingItem, item)
                viewBinding.executePendingBindings()
            }
            val lastView = this.children.last()
            if (lastView is ViewGroup) {
                val separator = lastView.findViewById<View>(R.id.separator)
                lastView.removeView(separator)
            }
            return
        }
        if (this.childCount == items.size) {
            this.children.forEachIndexed { index, view ->
                val viewBinding = DataBindingUtil.getBinding<ViewDataBinding>(view)
                viewBinding?.lifecycleOwner = _lifecycleOwner
                viewBinding?.setVariable(BR.bindingItem, items.toList()[index])
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
            inflateTemplateByItems(items, template, _lifecycleOwner)
        }
    } else {
        this.removeAllViews()
    }
}


/**
 * Возмможность скролла коллапсинг тоол бара.
 */
@BindingAdapter("app:canScroll")
fun AppBarLayout.canScroll(
    canScroll: Boolean?
) {
    (layoutParams as CoordinatorLayout.LayoutParams).behavior =
        AppBarLayout.Behavior().apply {
            setDragCallback(object : AppBarLayout.Behavior.DragCallback() {
                override fun canDrag(appBarLayout: AppBarLayout): Boolean {
                    return canScroll == null || canScroll
                }
            })
        }
}

/**
 * Возмможность скролла коллапсинг тоол бара.
 */
@BindingAdapter("app:expansionIsExpand", "app:expansionIsExpandDelay", requireAll = false)
fun ExpansionLayout.expansionIsExpand(isExpand: Boolean?, expandDelay: Long?) {
    if (isExpand == true) {
        GlobalScope.launchMain {
            if (expandDelay != null) delay(expandDelay)
            expand(true)
        }
    } else {
        GlobalScope.launchMain {
            if (expandDelay != null) delay(expandDelay)
            collapse(true)
        }
    }
}


/**
 * Устанавливает флаг возможности клика по вью и всем его потомкам.
 */
@BindingAdapter("app:isUserInteractionEnabled")
fun View.setUserInteractionEnabled(enabled: Boolean) {
    isEnabled = enabled
    if (this is ViewGroup && this.childCount > 0) {
        this.children.forEach {
            it.setUserInteractionEnabled(enabled)
        }
    }
}

/**
 * Устанавливает флаг видимости с выключением клика по вью и всем его потомкам.
 */
@BindingAdapter("app:isVisibleWithInteractionEnabled")
fun View.setVisibleWithInteractionEnabled(isVisible: Boolean) {
    visibility = if (isVisible) View.VISIBLE else View.INVISIBLE
    setUserInteractionEnabled(isVisible)
}

/**
 * Устанавливает цвет для заголовка и индикатора.
 */
@BindingAdapter("app:expansionChangeColor")
fun ExpansionHeader.setChangeSelectionColor(
    expansionChangeColor: Boolean?
) {
    if (expansionChangeColor == true) {
        val title = findViewWithTag<TextView>("title")
        addListener { expansionLayout, expanded ->
            title?.setTextColor(
                ContextCompat.getColor(
                    context,
                    if (expanded) R.color.color_primary else R.color.dark_black
                )
            )
            headerIndicator?.let {
                (it as? ImageView)?.setImageTint(
                    ContextCompat.getColor(
                        context,
                        if (expanded) R.color.color_primary else R.color.dark_grey
                    )
                )
            }
        }
    }
}

/**
 * Маштабирует отображение по оси X и Y.
 */
@BindingAdapter("app:scale")
fun View.scale(value: Float) {
    scaleX = value
    scaleY = value
}