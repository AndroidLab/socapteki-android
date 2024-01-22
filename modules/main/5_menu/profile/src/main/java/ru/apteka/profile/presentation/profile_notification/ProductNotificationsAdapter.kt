package ru.apteka.profile.presentation.profile_notification


import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import ru.apteka.components.data.utils.dp
import ru.apteka.components.ui.delegate_adapter.ViewBindingDelegateAdapter
import ru.apteka.profile.data.models.ProductNotificationModel
import ru.apteka.profile.databinding.ProductNotificationHolderBinding


/**
 * Представляет адаптер для уведомления продукта.
 */
class ProductNotificationsAdapter(
    private val lifeOwner: LifecycleOwner,
) :
    ViewBindingDelegateAdapter<ProductNotificationModel, ProductNotificationHolderBinding>(
        ProductNotificationHolderBinding::inflate
    ) {

    override fun ProductNotificationHolderBinding.onBind(
        item: ProductNotificationModel,
        position: Int,
        isFirst: Boolean,
        isLast: Boolean
    ) {
        lifecycleOwner = lifeOwner
        val lp = root.layoutParams as RecyclerView.LayoutParams

        lp.width = RecyclerView.LayoutParams.MATCH_PARENT
        lp.topMargin = 6.dp
        lp.marginStart = 6.dp

        root.layoutParams = lp
        model = item

        executePendingBindings()
        /*productCardItem.setOnClickListener {
            onItemClick(item.product)
        }*/
    }

    override fun isForViewType(item: Any) = item is ProductNotificationModel

    override fun ProductNotificationModel.getItemId() = product.id
}