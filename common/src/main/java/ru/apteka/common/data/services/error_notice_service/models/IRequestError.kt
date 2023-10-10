package ru.apteka.common.data.services.error_notice_service.models

import androidx.annotation.StringRes
import ru.apteka.resources.R as ResourcesR

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
        @StringRes override val title: Int = ResourcesR.string.error
    ): IRequestError

    data class RequestErrorResMsg(
        @StringRes val msg: Int,
        @StringRes override val title: Int = ResourcesR.string.error
    ): IRequestError
}
