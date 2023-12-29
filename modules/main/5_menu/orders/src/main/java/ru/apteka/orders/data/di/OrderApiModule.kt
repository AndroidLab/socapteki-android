package ru.apteka.orders.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import ru.apteka.components.data.repository.kogin.ILoginApi
import ru.apteka.components.data.repository.products.IProductsApi
import ru.apteka.orders.data.repository.IOrdersApi


@Module
@InstallIn(SingletonComponent::class)
class OrderApiModule {

    /**
     * Предоставляет экземпляр [IOrdersApi].
     */
    @Provides
    fun provideOrdersApi(retrofitClient: Retrofit): IOrdersApi =
        retrofitClient.create(IOrdersApi::class.java)

}