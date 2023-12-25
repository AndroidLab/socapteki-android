package ru.apteka.customers.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import ru.apteka.customers.data.repository.customers_repository.ICustomersApi


@Module
@InstallIn(SingletonComponent::class)
class LicensesApiModule {

    /**
     * Предоставляет экземпляр [ICustomersApi].
     */
    @Provides
    fun provideCustomersApi(retrofitClient: Retrofit): ICustomersApi =
        retrofitClient.create(ICustomersApi::class.java)

}