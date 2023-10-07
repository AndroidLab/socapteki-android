package ru.apteka.components.ui


import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.util.AttributeSet
import android.util.TypedValue
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.inputmethod.InputMethodManager
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.core.widget.addTextChangedListener
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import ru.apteka.components.R
import ru.apteka.components.databinding.PinCodeItemBinding
import ru.apteka.components.databinding.PinCodeViewBinding
import ru.apteka.components.R as ComponentsR

/**
 * Представляет отображение ввода пинкода.
 */
class PinCodeView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {
    private val _binding = PinCodeViewBinding.inflate(LayoutInflater.from(context), this, true)

    private var cellsCount = 4
    private var cellSize = getDp(52)
    private var cellBorderSize = getDp(2)
    private var cellGapSize = getDp(16)
    private var cellCornerRadius = getDp(4)
    private var cellBackgroundColor = Color.WHITE
    private var cellBackgroundFocusColor = Color.WHITE
    private var cellBackgroundSuccessColor = context.getColor(ComponentsR.color.light_green)
    private var cellBackgroundErrorColor = context.getColor(ComponentsR.color.light_red)
    private var cellBorderColor = Color.GRAY
    private var cellBorderFocusColor = context.getColor(ComponentsR.color.color_primary)
    private var cellBorderSuccessColor = context.getColor(ComponentsR.color.color_primary)
    private var cellBorderErrorColor = context.getColor(ComponentsR.color.red)
    private var cellTextColor = Color.DKGRAY

    private val cells = mutableListOf<PinCodeItemBinding>()
    private val codes = mutableListOf<String>()
    private val imm by lazy { context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager }

    private val onInputListeners = mutableListOf<OnInputListener>()

    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.PinCodeView)
        cellsCount = typedArray.getDimensionPixelSize(R.styleable.PinCodeView_cellsCount, cellsCount)
        cellSize = typedArray.getDimensionPixelSize(R.styleable.PinCodeView_cellSize, cellSize)
        cellBorderSize =
            typedArray.getDimensionPixelSize(R.styleable.PinCodeView_cellBorderSize, cellBorderSize)
        cellGapSize = typedArray.getDimensionPixelSize(R.styleable.PinCodeView_cellGapSize, cellGapSize)
        cellCornerRadius =
            typedArray.getDimensionPixelSize(R.styleable.PinCodeView_cellCornerRadius, cellCornerRadius)
        cellBackgroundColor =
            typedArray.getColor(R.styleable.PinCodeView_cellBackgroundColor, cellBackgroundColor)
        cellBackgroundFocusColor = typedArray.getColor(
            R.styleable.PinCodeView_cellBackgroundFocusColor,
            cellBackgroundFocusColor
        )
        cellBackgroundSuccessColor = typedArray.getColor(
            R.styleable.PinCodeView_cellBackgroundSuccessColor,
            cellBackgroundSuccessColor
        )
        cellBackgroundErrorColor = typedArray.getColor(
            R.styleable.PinCodeView_cellBackgroundErrorColor,
            cellBackgroundErrorColor
        )
        cellBorderColor = typedArray.getColor(R.styleable.PinCodeView_cellBorderColor, cellBorderColor)
        cellBorderFocusColor =
            typedArray.getColor(R.styleable.PinCodeView_cellBorderFocusColor, cellBorderFocusColor)
        cellBorderSuccessColor =
            typedArray.getColor(R.styleable.PinCodeView_cellBorderSuccessColor, cellBorderSuccessColor)
        cellBorderErrorColor =
            typedArray.getColor(R.styleable.PinCodeView_cellBorderErrorColor, cellBorderErrorColor)
        cellTextColor = typedArray.getColor(R.styleable.PinCodeView_cellTextColor, cellTextColor)
        typedArray.recycle()

        repeat(cellsCount) {
            cells.add(
                PinCodeItemBinding.inflate(
                    LayoutInflater.from(context),
                    _binding.llCodeContainer,
                    true
                ).apply {
                    card.layoutParams = LinearLayout.LayoutParams(cellSize, cellSize)
                        .apply { if (cellsCount != it) marginEnd = cellGapSize }
                    card.strokeWidth = cellBorderSize
                    card.radius = cellCornerRadius.toFloat()
                    tv.textSize = cellSize / 5f
                    tv.setTextColor(cellTextColor)
                }
            )
        }
        initEvent()
        showCode()
    }

    /**
     * Устанавивает слушатель ввода пин кода.
     * @param onInputListener Слушатель ввода пин кода.
     */
    fun addOnInputListener(onInputListener: OnInputListener) {
        onInputListeners.add(onInputListener)
    }

    /**
     * Удалялет слушатель ввода пин кода.
     * @param onInputListener Слушатель ввода пин кода.
     */
    fun removeOnInputListener(onInputListener: OnInputListener) {
        onInputListeners.remove(onInputListener)
    }

    private fun initEvent() {
        _binding.etCode.addTextChangedListener { editable ->
            if (!editable.isNullOrEmpty()) {
                _binding.etCode.setText("")
                if (codes.size < cells.size) {
                    codes.add(editable.toString());
                    showCode()
                }
            }
        }

        _binding.etCode.setOnKeyListener { view, keyCode, keyEvent ->
            if (keyCode == KeyEvent.KEYCODE_DEL && keyEvent.action == KeyEvent.ACTION_DOWN && codes.size > 0) {
                codes.removeAt(codes.size - 1)
                showCode()
                return@setOnKeyListener true
            }
            return@setOnKeyListener false
        }
    }

    /**
     * Отображение введенного проверочного кода.
     */
    private fun showCode() {
        val codeValues = buildList {
            repeat(cellsCount) { index ->
                if (index < codes.size) {
                    add(codes[index])
                } else {
                    add("")
                }
            }
        }
        cells.forEachIndexed { index, pinCodeItemBinding ->
            pinCodeItemBinding.tv.text = codeValues[index]
        }
        setColor()
        callBack()
    }

    /**
     * Установите цвет выделения.
     */
    private fun setColor() {
        cells.forEach { pinCodeItemBinding ->
            pinCodeItemBinding.card.strokeColor = cellBorderColor
            pinCodeItemBinding.card.setCardBackgroundColor(cellBackgroundColor)
        }
        if (codes.size < cells.size) {
            cells[codes.size].card.apply {
                strokeColor = cellBorderFocusColor
                setCardBackgroundColor(cellBackgroundFocusColor)
            }
        }
    }

    /**
     * Устанавливает текст.
     * @param value Значение текста.
     */
    fun setPinCode(value: String) {
        if (value != getPinCode()) {
            value.forEachIndexed { index, c ->
                if (index < cells.size) {
                    codes.add(c.toString())
                }
            }
            showCode()
        }
    }

    /**
     * Устанавливает статус успешного ввода пинкода.
     * @param isSuccess Флаг успеха.
     */
    fun setSuccess(isSuccess: Boolean) {
        if (isSuccess) {
            cells.forEach { pinCodeItemBinding ->
                pinCodeItemBinding.card.strokeColor = cellBorderSuccessColor
                pinCodeItemBinding.card.setCardBackgroundColor(cellBackgroundSuccessColor)
            }
        } else {
            showCode()
        }
    }

    /**
     * Устанавливает статус ошибки ввода пинкода.
     * @param isError Флаг ошибки.
     */
    fun setError(isError: Boolean) {
        if (isError) {
            cells.forEach { pinCodeItemBinding ->
                pinCodeItemBinding.card.strokeColor = cellBorderErrorColor
                pinCodeItemBinding.card.setCardBackgroundColor(cellBackgroundErrorColor)
            }
        } else {
            showCode()
        }
    }

    private fun callBack() {
        onInputListeners.forEach {
            it.onInput(getPinCode(), codes.size == cells.size)
        }
    }

    /**
     * Описывает методы обратного вызова ввода кода.
     */
    fun interface OnInputListener {
        /**
         * Кэлбэк ввода.
         * @param code Введенный код.
         * @param isComplete Флаг завершения ввода.
         */
        fun onInput(code: String, isComplete: Boolean)
    }

    /**
     * Показать клавиатуру
     */
    fun showSoftInput() {
        _binding.etCode.postDelayed({
            imm.showSoftInput(_binding.etCode, 0)
        }, 200)
    }

    /**
     * Получить код подтверждения номера мобильного телефона
     * @return проверочный код.
     */
    fun getPinCode() = codes.joinToString("")

    private fun getDp(value: Int) =
        TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            value.toFloat(),
            Resources.getSystem().displayMetrics
        ).toInt()
}

/**
 * Устанавливает текст для [PinCodeView].
 * @param pinCodeView Виджет пин кода.
 * @param pinCodeValue Значение.
 */
@BindingAdapter("apteka:pinCodeValue")
fun setPinCodeValue(
    pinCodeView: PinCodeView,
    pinCodeValue: String?
) {
    if (pinCodeValue != null) {
        pinCodeView.setPinCode(pinCodeValue)
    }
}

/**
 * Возвращает код из [PinCodeView].
 * @param pinCodeView Виджет пин кода.
 */
@InverseBindingAdapter(attribute = "apteka:pinCodeValue", event = "pinCodeValueChanged")
fun getPinCodeValue(
    pinCodeView: PinCodeView
) = pinCodeView.getPinCode()

/**
 * Устанавливает слушатель на вызов двустороннего биндинга.
 * @param pinCodeView Виджет пин кода.
 * @param listener Слушатель на вызов двустороннего биндинга.
 */

@BindingAdapter("pinCodeValueChanged")
fun setPinCodeValueChangeListener(
    pinCodeView: PinCodeView,
    listener: InverseBindingListener?
) {
    if (listener != null) {
        pinCodeView.addOnInputListener { code, isComplete ->
            listener.onChange()
        }
    }
}
