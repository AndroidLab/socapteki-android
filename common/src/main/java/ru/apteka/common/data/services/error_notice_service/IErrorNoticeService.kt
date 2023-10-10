package ru.apteka.common.data.services.error_notice_service

import ru.apteka.common.data.services.error_notice_service.models.IRequestError

/**
 * Описывает свойства и методы вызова сообщений.
 */
interface IErrorNoticeService {

    /**
     * Показывает ошибку.
     */
    fun showError(
        errorRequest: IRequestError
    )

}