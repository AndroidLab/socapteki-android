package ru.apteka.components.data.services.error_notice_service.models

import androidx.annotation.StringRes
import ru.apteka.components.R

/**
 * Представляет модель ошибки зарпоса.
 */
sealed interface IRequestError {
    /**
     * Заголовок ошибки.
     */
    val title: Int

    data class RequestErrorStringMsg(
        val msg: String,
        @StringRes override val title: Int = R.string.error
    ): IRequestError

    data class RequestErrorResMsg(
        @StringRes val msg: Int,
        @StringRes override val title: Int = R.string.error
    ): IRequestError
}
