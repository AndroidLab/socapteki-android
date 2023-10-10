package ru.apteka.main.presentation.bottomnavigation

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.ScaleAnimation
import android.widget.FrameLayout
import android.widget.TextView
import androidx.annotation.ColorInt
import ru.apteka.common.data.utils.dp
import ru.apteka.main.R
import ru.apteka.resources.R as ResourcesR
import ru.apteka.main.databinding.NotificationBadgeViewBinding


/**
 * Представляет значек уведомления.
 */
class NotificationBadge @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {
    private val binding =
        NotificationBadgeViewBinding.inflate(LayoutInflater.from(context), this, true)

    @Volatile
    private var _number: UInt? = null
    private var _textSize = 12.dp
    var animationDuration: Int = 250
    var countLimit: UInt = 99u
    var badgeVisible = false
        set(value) {
            field = value
            setNumber(_number)
        }

    private var ellipsizeText: String = "$countLimit+"
    private val iconBigSize =
        LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, Gravity.CENTER)
    private val iconSmallSize = LayoutParams(6.dp, 6.dp, Gravity.CENTER)

    /**
     * Возвращает или устанавливает цвет текста.
     */
    @ColorInt
    var textColor: Int = context.getColor(ResourcesR.color.white)

    /**
     * Возвращает или устанавливает фон значка.
     */
    var badgeBackgroundDrawable: Drawable?
        get() = binding.ivBadge.drawable
        set(drawable) = binding.ivBadge.setImageDrawable(drawable)


    private val update: Animation by lazy {
        ScaleAnimation(
            1f, 1.2f, 1f, 1.2f,
            Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f
        ).apply {
            duration = animationDuration.toLong()
            repeatMode = Animation.REVERSE
            repeatCount = 1
            setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationRepeat(animation: Animation?) {}
                override fun onAnimationEnd(animation: Animation?) {}
                override fun onAnimationStart(animation: Animation?) {
                    visibility = VISIBLE
                }
            })
        }
    }

    private val show: Animation by lazy {
        ScaleAnimation(
            0f, 1f, 0f, 1f,
            Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f
        ).apply {
            duration = animationDuration.toLong()
            setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationRepeat(animation: Animation?) {}
                override fun onAnimationEnd(animation: Animation?) {}
                override fun onAnimationStart(animation: Animation?) {
                    visibility = VISIBLE
                }
            })
        }
    }

    private val hide: Animation by lazy {
        ScaleAnimation(
            1f, 0f, 1f, 0f,
            Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f
        ).apply {
            duration = animationDuration.toLong()
            setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationRepeat(animation: Animation?) {}
                override fun onAnimationEnd(animation: Animation?) {
                    visibility = INVISIBLE
                }

                override fun onAnimationStart(animation: Animation?) {}
            })
        }
    }

    private val slideInLeftAnimation: Animation by lazy {
        AnimationUtils.loadAnimation(
            context,
            android.R.anim.slide_in_left
        )
    }

    private val slideOutRightAnimation: Animation by lazy {
        AnimationUtils.loadAnimation(
            context,
            android.R.anim.slide_out_right
        )
    }

    private val slideOutLeftAnimation: Animation by lazy {
        AnimationUtils.loadAnimation(
            context,
            R.anim.slide_out_left
        )
    }

    private val slideInRightAnimation: Animation by lazy {
        AnimationUtils.loadAnimation(
            context,
            R.anim.slide_in_right
        )
    }


    init {
        layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        visibility = if (badgeVisible) {
            binding.ivBadge.layoutParams = iconSmallSize
            VISIBLE
        } else {
            binding.ivBadge.layoutParams = iconBigSize
            INVISIBLE
        }
        minimumWidth = 22.dp
        minimumHeight = 20.dp
        setPadding(3.dp, 3.dp, 3.dp, 3.dp)

        val typedArray =
            context.theme.obtainStyledAttributes(attrs, R.styleable.NotificationBadge, 0, 0)
        try {
            textColor = typedArray.getColor(
                R.styleable.NotificationBadge_android_textColor,
                textColor
            )

            _textSize = typedArray.getDimensionPixelSize(
                R.styleable.NotificationBadge_android_textSize,
                _textSize
            )

            animationDuration = typedArray.getInt(
                R.styleable.NotificationBadge_nbAnimationDuration,
                animationDuration
            )

            typedArray.getDrawable(R.styleable.NotificationBadge_nbBackground)?.let {
                binding.ivBadge.setImageDrawable(it)
            }

            countLimit = typedArray.getInt(
                R.styleable.NotificationBadge_nbCountLimit,
                countLimit.toInt()
            ).toUInt()
            typedArray.getString(R.styleable.NotificationBadge_nbEllipsizeText)?.let {
                ellipsizeText = it
            }
        } finally {
            typedArray.recycle()
        }


        binding.tsBadger.setFactory {
            TextView(context).apply {
                setTextSize(TypedValue.COMPLEX_UNIT_PX, _textSize.toFloat())
                gravity = Gravity.CENTER
                maxLines = 1
                setTextColor(textColor)
                layoutParams = LayoutParams(
                    LayoutParams.MATCH_PARENT,
                    LayoutParams.MATCH_PARENT,
                    Gravity.CENTER
                ).apply {
                    setMargins(2.dp, 0, 2.dp, 0)
                }
            }
        }
    }


    /**
     * Устанавливает значение.
     * @param number Значение.
     */
    fun setNumber(number: UInt?) {
        val badgeText = if (number == null) {
            ""
        } else {
            val oldNumber = _number
            if (oldNumber == null || number > oldNumber) {
                binding.tsBadger.apply {
                    inAnimation = slideInLeftAnimation
                    outAnimation = slideOutRightAnimation
                }
            } else {
                binding.tsBadger.apply {
                    inAnimation = slideInRightAnimation
                    outAnimation = slideOutLeftAnimation
                }
            }
            if (number > countLimit) {
                ellipsizeText
            } else {
                number.toString()
            }
        }

        binding.tsBadger.setText(badgeText)
        _number = number

        if (badgeVisible) {
            if (badgeText.isEmpty()) {
                binding.ivBadge.layoutParams = iconSmallSize
                if (visibility == INVISIBLE) {
                    startAnimation(show)
                }
            } else {
                binding.ivBadge.layoutParams = iconBigSize
                startAnimation(update)
            }
        } else {
            if (badgeText.isEmpty()) {
                startAnimation(hide)
            } else {
                binding.ivBadge.layoutParams = iconBigSize
                startAnimation(update)
            }
        }
    }

}