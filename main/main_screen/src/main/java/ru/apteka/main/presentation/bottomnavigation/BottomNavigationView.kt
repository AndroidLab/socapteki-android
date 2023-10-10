package ru.apteka.main.presentation.bottomnavigation

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import ru.apteka.main.databinding.BottomNavigationViewBinding


/**
 * Представляет BottomNavigationView с анимированными значками.
 */
class BottomNavigationView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {
    private val binding = BottomNavigationViewBinding.inflate(LayoutInflater.from(context), this, true)

    /**
     * Возвращает [BottomNavigationView].
     */
    val component = binding.bottomNavigationView

    val nbTab5 = binding.nbTab5


}
