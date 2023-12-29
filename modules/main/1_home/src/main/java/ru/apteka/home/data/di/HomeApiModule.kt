package ru.apteka.home.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import ru.apteka.home.data.repository.advert.IAdvertApi
import ru.apteka.home.data.repository.other.IOtherApi
import ru.apteka.home.data.repository.promotion.IPromotionApi


@Module
@InstallIn(SingletonComponent::class)
class HomeApiModule {

    /**
     * Предоставляет экземпляр [IAdvertApi].
     */
    @Provides
    fun provideAdvertApi(retrofitClient: Retrofit): IAdvertApi =
        retrofitClient.create(IAdvertApi::class.java)

    /**
     * Предоставляет экземпляр [IPromotionApi].
     */
    @Provides
    fun providePromotionApi(retrofitClient: Retrofit): IPromotionApi =
        retrofitClient.create(IPromotionApi::class.java)

    /**
     * Предоставляет экземпляр [IOtherApi].
     */
    @Provides
    fun provideOtherApi(retrofitClient: Retrofit): IOtherApi =
        retrofitClient.create(IOtherApi::class.java)

}