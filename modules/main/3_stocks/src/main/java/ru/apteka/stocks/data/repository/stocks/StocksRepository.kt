package ru.apteka.stocks.data.repository.stocks

import ru.apteka.stocks.data.repository.stocks.IStocksApi
import javax.inject.Inject

/**
 * Представляет репозиторий избранных.
 * @param favoritesApi Favorites api.
 */
class StocksRepository @Inject constructor(
    private val favoritesApi: IStocksApi
) {




}