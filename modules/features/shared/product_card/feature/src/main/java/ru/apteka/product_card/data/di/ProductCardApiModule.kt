package ru.apteka.product_card.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import ru.apteka.product_card.data.repository.product_card.IProductCardApi


@Module
@InstallIn(SingletonComponent::class)
class ProductCardApiModule {

    /**
     * Предоставляет экземпляр [IProductCardApi].
     */
    @Provides
    fun provideProductCardApi(retrofitClient: Retrofit): IProductCardApi =
        retrofitClient.create(IProductCardApi::class.java)

}