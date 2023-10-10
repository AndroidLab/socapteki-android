package ru.apteka.common.ui

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.databinding.BindingAdapter
import ru.apteka.common.R
import ru.apteka.common.databinding.LoaderLayoutBinding

/**
 * Представляет компоновку экрана для показа загрузки.
 */
class LoaderLayout : FrameLayout {
    private val binding = LoaderLayoutBinding.inflate(LayoutInflater.from(context), this, true)
    private var _hideContent = true
    private lateinit var _content: View

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        val typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.LoaderLayout)
        _hideContent = typedArray.getBoolean(R.styleable.LoaderLayout_hideContent, false)
        typedArray.recycle()
    }

    /**
     * Устанавливает статус загрузки.
     * @param isLoading Флаг загрузки.
     */
    fun setLoading(isLoading: Boolean) {
        binding.isLoading = isLoading
        if (_hideContent) {
            setContentVisibility(isLoading)
        }
    }

    private fun setContentVisibility(isVisible: Boolean) {
        _content.visibility = if (isVisible) INVISIBLE else VISIBLE
    }

    override fun onViewAdded(child: View) {
        if (childCount <= 2) {
            super.onViewAdded(child)
            if (childCount == 2) {
                _content = child
                binding.loaderProgressLayout.bringToFront()
            }
        } else {
            throw LoaderLayoutException()
        }
    }

    /**
     * Представляет класс исключения для LoadLayout.
     */
    private class LoaderLayoutException :
        Exception("LoaderLayout can contain only one layout element")
}

/**
 * Устанавливает флаг загрузки.
 * @param loaderLayout [LoaderLayout] - Контейнер с контентом для загрузки.
 * @param isLoading [Boolean] - Флаг загрузки.
 */
@BindingAdapter("northis:isLoading")
fun setLoading(loaderLayout: LoaderLayout, isLoading: Boolean) {
    loaderLayout.setLoading(isLoading)
}