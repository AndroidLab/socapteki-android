package ru.apteka.components.data.utils

import android.animation.Animator
import android.animation.Animator.AnimatorListener
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.text.Spanned
import android.util.TypedValue
import android.view.View
import android.view.WindowManager
import android.widget.TextView
import androidx.annotation.IdRes
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.os.bundleOf
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions
import com.airbnb.lottie.LottieAnimationView
import com.alab.extra_bouncy.BouncyRecyclerView
import com.alab.extra_bouncy.util.OnOverScrollOffsetListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.apteka.components.R
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Matcher
import java.util.regex.Pattern
import kotlin.math.ceil

/**
 * Возвращает календарь с указзаной датой.
 * @param unixTime Время в секундах.
 */
fun Calendar(unixTime: Int) = Calendar.getInstance().apply {
    timeInMillis = unixTime * 1000L
}

fun Calendar(timeInMils: Long) = Calendar.getInstance().apply {
    timeInMillis = timeInMils
}

/**
 * Конвертирует дату в удобный для чтения вид.
 */
fun Calendar.formatDate(context: Context): String {
    var publishTime =
        if (get(Calendar.DAY_OF_MONTH) == Calendar.getInstance()[Calendar.DAY_OF_MONTH]) {
            context.getString(R.string.common_today)
        } else {
            if (get(Calendar.DAY_OF_MONTH) == Calendar.getInstance()[Calendar.DAY_OF_MONTH] - 1
            ) {
                context.getString(R.string.common_yesterday)
            } else {
                if (get(Calendar.YEAR) == Calendar.getInstance()[Calendar.YEAR]) {
                    SimpleDateFormat("dd MMMM", Locale.getDefault()).format(time)
                } else {
                    DateFormat.getDateInstance(DateFormat.MEDIUM).format(time)
                }
            }
        }
    publishTime += " в " + get(Calendar.HOUR_OF_DAY) + ":" + SimpleDateFormat(
        "mm",
        Locale.getDefault()
    ).format(time)
    return publishTime
}

/**
 * Конвертирует дату в удобный для чтения вид.
 */
fun Calendar.formatDate(format: String): String {
    return SimpleDateFormat(format, Locale.getDefault()).format(time)
}


/**
 * Проверяет значение на null и возвращает значение по умолчанию.
 * Если значение null вызывает обработчик для получения значение по умолчанию.
 * Если не null возвращает значение.
 * @param defaultValue Обработчик возвращающий значение по умолчанию.
 */
fun String?.valueOrDefault(defaultValue: () -> String) = this ?: defaultValue()

/**
 * Возвращает значение в dp единицах.
 */
val Number.dp
    get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this.toFloat(),
        Resources.getSystem().displayMetrics
    ).toInt()

/**
 * Возвращает значение в sp единицах.
 */
val Number.sp
    get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_SP,
        this.toFloat(),
        Resources.getSystem().displayMetrics
    )

/**
 * Запускает корутину в контексте IO.
 * @param block Прерываемая функция в вызываемой области корутины.
 */
fun CoroutineScope.launchIO(block: suspend CoroutineScope.() -> Unit): Job {
    return launch(Dispatchers.IO) {
        block()
    }
}

/**
 * Запускает корутину в контексте Main.
 * @param block Прерываемая функция в вызываемой области корутины.
 */
fun CoroutineScope.launchMain(block: suspend CoroutineScope.() -> Unit): Job {
    return launch(Dispatchers.Main) {
        block()
    }
}

/**
 * Запускает корутину в контексте Default.
 * @param block Прерываемая функция в вызываемой области корутины.
 */
fun CoroutineScope.launchDefault(block: suspend CoroutineScope.() -> Unit): Job {
    return launch(Dispatchers.Default) {
        block()
    }
}

/**
 * Запускает корутину в контексте IO.
 * @param block Прерываемая функция в вызываемой области корутины.
 */
fun <T> CoroutineScope.asyncIO(block: suspend CoroutineScope.() -> T): Deferred<T> {
    return async(Dispatchers.IO) {
        block()
    }
}

/**
 * Запускает корутину в контексте Main.
 * @param block Прерываемая функция в вызываемой области корутины.
 */
fun <T> CoroutineScope.asyncMain(block: suspend CoroutineScope.() -> T): Deferred<T> {
    return async(Dispatchers.Main) {
        block()
    }
}

/**
 * Запускает корутину в контексте Default.
 * @param block Прерываемая функция в вызываемой области корутины.
 */
fun <T> CoroutineScope.asyncDefault(block: suspend CoroutineScope.() -> T): Deferred<T> {
    return async(Dispatchers.Default) {
        block()
    }
}

/**
 * Переключает контекст потока на Main(UI).
 * @param block Прерываемая функция в вызываемой области корутины.
 */
suspend fun <T> mainThread(block: suspend CoroutineScope.() -> T): T {
    return withContext(Dispatchers.Main) {
        block()
    }
}

/**
 * Переключает контекст потока на IO.
 * @param block Прерываемая функция в вызываемой области корутины.
 */
suspend fun <T> ioThread(block: suspend CoroutineScope.() -> T): T {
    return withContext(Dispatchers.IO) {
        block()
    }
}

/**
 * Переключает контекст потока на вычислительный.
 * @param block Прерываемая функция в вызываемой области корутины.
 */
suspend fun <T> defaultThread(block: suspend CoroutineScope.() -> T): T {
    return withContext(Dispatchers.Default) {
        block()
    }
}

/**
 * Запускает таймер на отложенное выполнение функции в скоупе корутины.
 * @param waitMs Время по истечении которого будет запущена функция.
 * @param onAction Действие выполняемое по истечении таймера.
 */
fun <T> CoroutineScope.launchAfter(
    waitMs: Long,
    onAction: suspend CoroutineScope.() -> T
) = launch {
    delay(waitMs)
    if (isActive) {
        onAction(this@launchAfter)
    }
}


/**
 * Обрабатывает данные после завершения ввода.
 * @param waitMs Время по истечении которого будет запущена функция.
 * @param onAction Действие выполняемое по истечении таймера.
 */
fun <T> CoroutineScope.debounce(
    waitMs: Long,
    onAction: suspend (T) -> Unit
): (T) -> Unit {
    var debounceJob: Job? = null
    return { param: T ->
        debounceJob?.cancel()
        debounceJob = launchIO {
            delay(waitMs)
            onAction(param)
        }
    }
}

/**
 * Находит активити в контексте приложения.
 */
tailrec fun Context.activity(): Activity? = when {
    this is Activity -> this
    else -> (this as? ContextWrapper)?.baseContext?.activity()
}

/**
 * Возвращает представление FragmentContainerView.
 */
/*
val DrawerLayout.fragmentContainerView: FragmentContainerView
    get() = findViewById(R.id.fragment_container_view)
        ?: throw NullPointerException("Не удалось найти 'FragmentContainerView' в контейнере 'DrawerLayout'")
*/

/**
 * Возвращает подсписок или null.
 * @param fromIndex От индекса.
 * @param toIndex До индекса.
 */
fun <T> List<T>.subListOrNull(fromIndex: Int, toIndex: Int): List<T>? {
    return if (fromIndex > lastIndex) {
        null
    } else {
        subList(fromIndex, if (toIndex > lastIndex) lastIndex + 1 else toIndex)
    }
}


/**
 * Устанавливает прозрачный статус бар.
 */
/*fun Activity.transparentStatusBar() {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
        window.decorView.systemUiVisibility =
            (View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
    } else {
        window.setDecorFitsSystemWindows(false)
    }
    window.statusBarColor = Color.TRANSPARENT
}*/

/**
 * Возвращает подстроку по regex.
 * @param regex Регулярное выражение.
 */
fun String.subStringByRegex(regex: String): String? {
    val matcher: Matcher = Pattern.compile(regex).matcher(this)
    return if (matcher.find()) {
        matcher.group()
    } else {
        null
    }
}

/**
 * Возвращает подстроки по regex.
 * @param regex Регулярное выражение.
 */
fun String.subStringsByRegex(regex: String): List<String> {
    return Regex(regex).findAll(this).map { it.value }.toList()
}

/**
 * Возвращает флаг содержится ли объект в списке.
 * @param predicate Регулярное выражение.
 */
fun <T> List<T>.contains(predicate: (item: T) -> Boolean): Boolean {
    return this.firstOrNull { predicate(it) } != null
}

/**
 * Возвращает обрезаннуюю строку с точками.
 */
fun String?.crop(length: Int): String? {
    return if (this == null) {
        null
    } else {
        if ((this.length) > length) {
            this.substring(0, length) + "..."
        } else {
            this
        }
    }
}

/**
 * Устанавливает прозрачный статус бар.
 */
fun Activity.transparentStatusBar() {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
        window.decorView.systemUiVisibility =
            (View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
    } else {
        window.setDecorFitsSystemWindows(false)
    }
    window.statusBarColor = Color.TRANSPARENT
}

/**
 * Возвращает bitmap представления.
 */
val View.bitmap
    get() = Bitmap.createBitmap(
        width,
        height,
        Bitmap.Config.ARGB_8888
    ).also {
        val canvas = Canvas(it)
        this.draw(canvas)
    }

private val navOptions: NavOptions = NavOptions.Builder()
    .setEnterAnim(R.anim.move_in_left)
    .setExitAnim(R.anim.move_out_left)
    .setPopExitAnim(R.anim.move_out_down)
    .build()

/**
 * Навигирует с анимацией перехода.
 */
fun NavController.navigateWithAnim(@IdRes resId: Int, args: Bundle = bundleOf()) {
    navigate(
        resId,
        args,
        navOptions
    )
}

/**
 * Навигирует с анимацией перехода.
 */
fun NavController.navigateWithAnim(directions: NavDirections) {
    navigate(
        directions,
        navOptions
    )
}


/**
 * Возвращает ширину экрана.
 */
val screenWidth
    get() = Resources.getSystem().displayMetrics.widthPixels

/**
 * Возвращает высоту экрана.
 */
val screenHeight
    get() = Resources.getSystem().displayMetrics.heightPixels

/**
 * Возвращает высоту нижней навигационной панели.
 */
val Context.bottomSystemNavigationBarHeight: Int
    @SuppressLint("DiscouragedApi", "InternalInsetResource")
    get() = resources.getIdentifier("navigation_bar_height", "dimen", "android").let {
        if (it > 0)
            resources.getDimensionPixelSize(it)
        else
            0
    }

/**
 * Шарит ссылку другим приложениям.
 */
fun sharedLink(
    ctx: Context,
    link: String?
) {
    if (link != null) {
        val i = Intent(Intent.ACTION_SEND)
        i.type = "text/plain"
        i.putExtra(Intent.EXTRA_SUBJECT, "Sharing URL")
        i.putExtra(Intent.EXTRA_TEXT, link)
        ContextCompat.startActivity(ctx, Intent.createChooser(i, "Share URL"), bundleOf())
    }
}

/**
 * Проверяет равенство с возможным отклонением.
 */
fun Float.equalsWithDeviation(value: Float, deviation: Float): Boolean {
    return ((this - deviation)..(this + deviation)).contains(value)
}

/**
 * Запускает анимацию в указанном интервале.
 * @param minProgress Минимальный прогресс анимации.
 * @param maxProgress Максимальный прогресс анимации.
 */
fun LottieAnimationView.playAnimation(minProgress: Float, maxProgress: Float) {
    setMinAndMaxProgress(minProgress, maxProgress)
    playAnimation()
}

/**
 * Получает текст от любого объекта.
 * @param value Значение.
 */
fun Context.getStringFrom(value: Any): String {
    return if (value is Int && value != 0) {
        getString(value.toInt())
    } else {
        value.toString()
    }
}

/**
 * Преобразует в [LiveData].
 */
fun <T> MutableLiveData<T>.asLiveData() = this as LiveData<T>


/**
 * Возвращает html текст.
 * @param text Текст.
 */
fun getSpannedFromHtml(text: String): Spanned {
    return HtmlCompat.fromHtml(text, HtmlCompat.FROM_HTML_MODE_LEGACY)
}

/**
 * Устанавливает паддинг слева.
 * @param paddingLeft Отступ слева.
 */
fun View.setPaddingLeft(paddingLeft: Int) {
    setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom)
}

/**
 * Устанавливает паддинг сверху.
 * @param paddingTop Отступ сверху.
 */
fun View.setPaddingTop(paddingTop: Int) {
    setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom)
}

/**
 * Устанавливает паддинг справа.
 * @param paddingRight Отступ справа.
 */
fun View.setPaddingRight(paddingRight: Int) {
    setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom)
}

/**
 * Устанавливает паддинг снизу.
 * @param paddingBottom Отступ снизу.
 */
fun View.setPaddingBottom(paddingBottom: Int) {
    setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom)
}

/**
 * Устанавливает слушатель обновления через оверскролл.
 */
fun BouncyRecyclerView.setPullForward(
    view: LottieAnimationView,
    release: () -> Unit
) {
    this.overscrollEndAnimationSize = 1f
    this.onOverScrollOffsetListener = object : OnOverScrollOffsetListener {
        private var isAnimating = false

        override fun onOverScrollStart(overscrollMode: Int) {}

        override fun onOverScrollOffset(overscrollMode: Int, offset: Int) {
            if (offset < view.width) {
                view.translationX = offset * -1f
                val delta = (offset * 100 / view.width) / 100f
                view.alpha = delta
                view.scale(delta)
                view.cancelAnimation()
                isAnimating = false
            } else {
                view.translationX = -view.width.toFloat()
                if (!isAnimating) {
                    fun playAnim() {
                        view.playAnimation(.38f, 0.73f)
                    }
                    isAnimating = true
                    view.addAnimatorListener(object : Animator.AnimatorListener {
                        override fun onAnimationStart(p0: Animator) {}
                        override fun onAnimationEnd(p0: Animator) {
                            playAnim()
                        }

                        override fun onAnimationCancel(p0: Animator) {}
                        override fun onAnimationRepeat(p0: Animator) {}
                    })
                    playAnim()
                    val vibrator = getSystemService(context, Vibrator::class.java)!!
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        vibrator.vibrate(
                            VibrationEffect.createOneShot(
                                50,
                                1
                            )
                        )
                    } else {
                        vibrator.vibrate(50)
                    }
                }
            }
        }

        override fun onOverScrollRelease(overscrollMode: Int) {
            release()
        }

        override fun onOverScrollEnd() {}
    }
}

/**
 * Устанавливает режим открытия клавиатуры со сдвигом текстового поля.
 */
fun Fragment.setSoftInputModeAdjustPan() {
    requireActivity().window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
}

/**
 * Устанавливает режим открытия клавиатуры со сдвигом контента.
 */
fun Fragment.setSoftInputModeResize() {
    requireActivity().window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
}

/**
 * Устанавливает режим открытия клавиатуры без здвига контента.
 */
fun Fragment.setSoftInputModeNothing() {
    requireActivity().window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING)
}

/**
 * Устанавливает режим открытия клавиатуры без здвига контента.
 */
fun Fragment.setSoftInputModeHidden() {
    requireActivity().window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
}

/**
 * Возвращает кол-во строк в тексте.
 */
fun TextView.countLines() =
    ceil(paint.measureText(text.toString()) / measuredWidth.toDouble()).toInt()

/**
 * Возвращает обработчик старта анимации.
 */
fun ValueAnimator.addOnAnimationStartListener(onAnimationStart: () -> Unit) =
    onAnimationStartListener(onAnimationStart).also {
        addListener(it)
    }

/**
 * Возвращает обработчик конца анимации.
 */
fun ValueAnimator.addOnAnimationEndListener(onAnimationEnd: () -> Unit) =
    onAnimationEndListener(onAnimationEnd).also {
        addListener(it)
    }

/**
 * Возвращает обработчик отмены анимации.
 */
fun ValueAnimator.addOnAnimationCancel(onAnimationCancel: () -> Unit) =
    onAnimationCancel(onAnimationCancel).also {
        addListener(it)
    }

/**
 * Возвращает обработчик повтора анимации.
 */
fun ValueAnimator.addOnAnimationRepeat(onAnimationRepeat: () -> Unit) =
    onAnimationRepeat(onAnimationRepeat).also {
        addListener(it)
    }

/**
 * Возвращает обработчик старта анимации.
 */
fun onAnimationStartListener(onAnimationStart: () -> Unit) =
    object : AnimatorListener {
        override fun onAnimationStart(animation: Animator) {
            onAnimationStart()
        }

        override fun onAnimationEnd(animation: Animator) {}
        override fun onAnimationCancel(animation: Animator) {}
        override fun onAnimationRepeat(animation: Animator) {}
    }

/**
 * Возвращает обработчик конца анимации.
 */
fun onAnimationEndListener(onAnimationEnd: () -> Unit) =
    object : AnimatorListener {
        override fun onAnimationStart(animation: Animator) {}
        override fun onAnimationEnd(animation: Animator) {
            onAnimationEnd()
        }

        override fun onAnimationCancel(animation: Animator) {}
        override fun onAnimationRepeat(animation: Animator) {}
    }

/**
 * Возвращает обработчик отмены анимации.
 */
fun onAnimationCancel(onAnimationCancel: () -> Unit) =
    object : AnimatorListener {
        override fun onAnimationStart(animation: Animator) {}
        override fun onAnimationEnd(animation: Animator) {}
        override fun onAnimationCancel(animation: Animator) {
            onAnimationCancel()
        }

        override fun onAnimationRepeat(animation: Animator) {}
    }

/**
 * Возвращает обработчик повтора анимации.
 */
fun onAnimationRepeat(onAnimationRepeat: () -> Unit) =
    object : AnimatorListener {
        override fun onAnimationStart(animation: Animator) {}
        override fun onAnimationEnd(animation: Animator) {}
        override fun onAnimationCancel(animation: Animator) {}

        override fun onAnimationRepeat(animation: Animator) {
            onAnimationRepeat()
        }
    }