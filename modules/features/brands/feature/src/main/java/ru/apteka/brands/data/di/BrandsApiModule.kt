package ru.apteka.brands.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import ru.apteka.brands.data.repository.brands.IBrandsApi


@Module
@InstallIn(SingletonComponent::class)
class BrandsApiModule {

    /**
     * Предоставляет экземпляр [IBrandsApi].
     */
    @Provides
    fun provideBrandsApi(retrofitClient: Retrofit): IBrandsApi =
        retrofitClient.create(IBrandsApi::class.java)

}