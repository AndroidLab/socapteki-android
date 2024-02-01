package ru.apteka.orders.data.repository

import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

/**
 * Представляет репозиторий заказоы.
 * @param ordersApi Advert api.
 */

@ViewModelScoped
class OrdersRepository @Inject constructor(
    private val ordersApi: IOrdersApi
)
