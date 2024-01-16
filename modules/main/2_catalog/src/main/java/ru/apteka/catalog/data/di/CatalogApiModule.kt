package ru.apteka.catalog.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit


@Module
@InstallIn(SingletonComponent::class)
class CatalogApiModule {

    /**
     * Предоставляет экземпляр [ICatalogApi].
     */
    /*@Provides
    fun provideCatalogApi(retrofitClient: Retrofit): ru.apteka.listing.data.repository.listing.ICatalogApi =
        retrofitClient.create(ru.apteka.listing.data.repository.listing.ICatalogApi::class.java)*/

}