package ru.apteka.notifications.presentation

import androidx.lifecycle.LifecycleOwner
import ru.apteka.components.ui.delegate_adapter.ViewBindingDelegateAdapter
import ru.apteka.notifications.data.model.NotificationModel
import ru.apteka.notifications.databinding.NotificationHolderBinding


/**
 * Представляет адаптер для заказов.
 */
class NotificationAdapter(
    private val lifeOwner: LifecycleOwner,
    private val onItemClick: (NotificationModel) -> Unit) :
    ViewBindingDelegateAdapter<NotificationModel, NotificationHolderBinding>(NotificationHolderBinding::inflate) {

    override fun NotificationHolderBinding.onBind(
        item: NotificationModel, position: Int,
        isFirst: Boolean,
        isLast: Boolean
    ) {
        lifecycleOwner = lifeOwner
        model = item
        executePendingBindings()
        llNotificationItem.setOnClickListener {
            onItemClick(item)
        }
    }

    override fun isForViewType(item: Any) = item is NotificationModel

    override fun NotificationModel.getItemId() = id
}