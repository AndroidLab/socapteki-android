package ru.apteka.favorites.presentation.stocks.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import ru.apteka.favorites.presentation.stocks.data.repository.stocks.IStocksApi


@Module
@InstallIn(SingletonComponent::class)
class StocksApiModule {

    /**
     * Предоставляет экземпляр [IStocksApi].
     */
    @Provides
    fun provideFavoritesApi(retrofitClient: Retrofit): IStocksApi =
        retrofitClient.create(IStocksApi::class.java)

}