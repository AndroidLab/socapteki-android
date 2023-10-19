package ru.apteka.components.data.utils

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.res.Resources
import android.graphics.Color
import android.os.Build
import android.util.TypedValue
import android.view.View
import androidx.lifecycle.LifecycleOwner
import kotlinx.coroutines.*
import ru.apteka.components.R
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Matcher
import java.util.regex.Pattern


/**
 * Возвращает календарь с указзаной датой.
 * @param unixTime Время в секундах.
 */
fun Calendar(unixTime: Int) = Calendar.getInstance().apply { timeInMillis = unixTime * 1000L }

/**
 * Конвертирует дату в удобный для чтения вид.
 */
fun Calendar.formatDate(context: Context): String {
    var publishTime =
        if (get(Calendar.DAY_OF_MONTH) == Calendar.getInstance().get(Calendar.DAY_OF_MONTH)) {
            context.getString(R.string.common_today)
        } else {
            if (get(Calendar.DAY_OF_MONTH) == Calendar.getInstance()
                    .get(Calendar.DAY_OF_MONTH) - 1
            ) {
                context.getString(R.string.common_yesterday)
            } else {
                if (get(Calendar.YEAR) == Calendar.getInstance().get(Calendar.YEAR)) {
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
 * Возвращает владельца жизненого цикла вью.
 */
fun View.getLifeCycleOwner(): LifecycleOwner {
    var localContext = context
    while (localContext is ContextWrapper) {
        if (localContext is LifecycleOwner) {
            return localContext
        }
        localContext = localContext.baseContext
    }

    throw Exception("Владелец ЖЦ не найден.")
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
    return this.launch(Dispatchers.IO) {
        block()
    }
}

/**
 * Запускает корутину в контексте Main.
 * @param block Прерываемая функция в вызываемой области корутины.
 */
fun CoroutineScope.launchMain(block: suspend CoroutineScope.() -> Unit): Job {
    return this.launch(Dispatchers.Main) {
        block()
    }
}

/**
 * Запускает корутину в контексте Default.
 * @param block Прерываемая функция в вызываемой области корутины.
 */
fun CoroutineScope.launchDefault(block: suspend CoroutineScope.() -> Unit): Job {
    return this.launch(Dispatchers.Default) {
        block()
    }
}

/**
 * Запускает корутину в контексте IO.
 * @param block Прерываемая функция в вызываемой области корутины.
 */
fun <T> CoroutineScope.asyncIO(block: suspend CoroutineScope.() -> T): Deferred<T> {
    return this.async(Dispatchers.IO) {
        block()
    }
}

/**
 * Запускает корутину в контексте Main.
 * @param block Прерываемая функция в вызываемой области корутины.
 */
fun <T> CoroutineScope.asyncMain(block: suspend CoroutineScope.() -> T): Deferred<T> {
    return this.async(Dispatchers.Main) {
        block()
    }
}

/**
 * Запускает корутину в контексте Default.
 * @param block Прерываемая функция в вызываемой области корутины.
 */
fun <T> CoroutineScope.asyncDefault(block: suspend CoroutineScope.() -> T): Deferred<T> {
    return this.async(Dispatchers.Default) {
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
        subList(fromIndex, if (toIndex > lastIndex) lastIndex+1 else toIndex)
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
 * @param item Регулярное выражение.
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
        if ((this.length) > length)
            this.substring(0, length) + "..."
        else
            this
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