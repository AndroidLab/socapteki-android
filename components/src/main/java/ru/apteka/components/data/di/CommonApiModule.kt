package ru.apteka.components.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import ru.apteka.components.data.repository.IOrdersApi


@Module
@InstallIn(SingletonComponent::class)
class CommonApiModule {

    /**
     * Предоставляет экземпляр [IOrdersApi].
     */
    @Provides
    fun provideOrdersApi(retrofitClient: Retrofit): IOrdersApi =
        retrofitClient.create(IOrdersApi::class.java)

}