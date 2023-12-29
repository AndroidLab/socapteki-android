package ru.apteka.components.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import ru.apteka.components.data.repository.kogin.ILoginApi
import ru.apteka.components.data.repository.products.IProductsApi


@Module
@InstallIn(SingletonComponent::class)
class CommonApiModule {

    /**
     * Предоставляет экземпляр [IProductsApi].
     */
    @Provides
    fun provideProductsApi(retrofitClient: Retrofit): IProductsApi =
        retrofitClient.create(IProductsApi::class.java)

    /**
     * Предоставляет экземпляр [ILoginApi].
     */
    @Provides
    fun provideLoginApiApi(retrofitClient: Retrofit): ILoginApi =
        retrofitClient.create(ILoginApi::class.java)
}