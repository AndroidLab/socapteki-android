package ru.apteka.notifications.data.model

import androidx.lifecycle.MutableLiveData
import java.util.UUID


/**
 * Возвращает модель уведомления.
 */
data class NotificationModel(
    val title: String,
    val desc: String,
    val type: NotificationType,
    val orderOrStock: Any,
    val id: UUID = UUID.randomUUID()
) {
    enum class NotificationType {
        STOCKS,
        ORDERS
    }

    val isRead = MutableLiveData(false)
}
