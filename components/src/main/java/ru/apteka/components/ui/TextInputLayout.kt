package ru.apteka.components.ui

import android.content.Context
import android.util.AttributeSet
import androidx.annotation.DrawableRes
import com.google.android.material.textfield.TextInputLayout
import ru.apteka.components.R

class TextInputLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : TextInputLayout(context, attrs, defStyleAttr) {

    @DrawableRes
    private var focusedDrawable: Int = R.drawable.text_input_white_background_focused_shape

    @DrawableRes
    private var unfocusedDrawable: Int = R.drawable.text_input_white_background_unfocused_shape

    @DrawableRes
    private var disabledDrawable: Int = R.drawable.text_input_white_background_disabled_shape

    @DrawableRes
    private var errorDrawable: Int = R.drawable.text_input_white_background_error_shape

    private val editTextFocusChangeListeners = mutableListOf<OnFocusChangeListener>()

    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.TextInputLayout)
        focusedDrawable = typedArray.getResourceId(
            R.styleable.TextInputLayout_textInputLayoutFocusedDrawable,
            focusedDrawable
        )
        unfocusedDrawable = typedArray.getResourceId(
            R.styleable.TextInputLayout_textInputLayoutUnfocusedDrawable,
            unfocusedDrawable
        )
        disabledDrawable = typedArray.getResourceId(
            R.styleable.TextInputLayout_textInputLayoutDisabledDrawable,
            disabledDrawable
        )
        errorDrawable = typedArray.getResourceId(
            R.styleable.TextInputLayout_textInputLayoutErrorDrawable,
            errorDrawable
        )
        typedArray.recycle()
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        editText!!.setOnFocusChangeListener { view, b ->
            editTextFocusChangeListeners.forEach {
                it.onFocusChange(view, b)
            }
            setEditTextDrawable()
        }
    }

    override fun setEnabled(enabled: Boolean) {
        super.setEnabled(enabled)
        setEditTextDrawable()
    }

    override fun setError(errorText: CharSequence?) {
        super.setError(errorText)
        setEditTextDrawable()
    }

    /**
     * Добавляет слушателей изменения фокуса.
     * @param l Слушатель изменения фокуса.
     */
    fun addEditTextFocusChangeListener(l: OnFocusChangeListener) {
        editTextFocusChangeListeners.add(l)
    }

    /**
     * Удаляет слушателей изменения фокуса.
     * @param l Слушатель изменения фокуса.
     */
    fun removeEditTextFocusChangeListener(l: OnFocusChangeListener) {
        editTextFocusChangeListeners.remove(l)
    }

    private fun setEditTextDrawable() {
        editText?.let { editText ->
            if (error.isNullOrEmpty()) {
                if (editText.isFocused) {
                    editText.setBackgroundResource(focusedDrawable)
                } else {
                    editText.setBackgroundResource(unfocusedDrawable)
                }
            } else {
                editText.setBackgroundResource(errorDrawable)
            }
            if (!isEnabled) {
                editText.setBackgroundResource(disabledDrawable)
            }
        }
    }

}