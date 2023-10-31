package ru.apteka.components.ui.delegate_adapter


import android.widget.FrameLayout
import ru.apteka.components.data.utils.Skeleton
import ru.apteka.components.data.utils.dp
import ru.apteka.components.databinding.SceletonBinding


/**
 * Представляет адаптер для скелетона.
 */
class SkeletonAdapter(
    private val width: Int,
    private val height: Int,
    private val _marginStart: Int = 0,
    private val _marginTop: Int = 0,
    private val _marginEnd: Int = 0,
    private val _marginBottom: Int = 0
) :
    ViewBindingDelegateAdapter<Skeleton, SceletonBinding>(
        SceletonBinding::inflate
    ) {

    override fun SceletonBinding.onBind(item: Skeleton) {
        skeleton.layoutParams = FrameLayout.LayoutParams(width, height).apply {
            setMargins(
                _marginStart,
                _marginTop,
                _marginEnd,
                _marginBottom
            )
        }
    }

    override fun isForViewType(item: Any) = item is Skeleton

    override fun Skeleton.getItemId() = Unit
}