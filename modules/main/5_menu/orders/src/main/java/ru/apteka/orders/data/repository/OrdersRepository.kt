package ru.apteka.orders.data.repository

import dagger.hilt.android.scopes.ViewModelScoped
import ru.apteka.components.data.models.OrderDeliveryMethod
import ru.apteka.components.data.utils.getProductsFake
import ru.apteka.components.data.models.OrderModel
import ru.apteka.components.data.models.OrderPayStatus
import ru.apteka.components.data.models.OrderStatus
import javax.inject.Inject

/**
 * Представляет репозиторий заказоы.
 * @param ordersApi Advert api.
 */
@ViewModelScoped
class OrdersRepository @Inject constructor(
    private val ordersApi: IOrdersApi
) {



}