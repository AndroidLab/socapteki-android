package ru.apteka.catalog.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import ru.apteka.catalog.data.catalog_repository.ICatalogApi


@Module
@InstallIn(SingletonComponent::class)
class CatalogApiModule {

    /**
     * Предоставляет экземпляр [ICatalogApi].
     */
    @Provides
    fun provideCatalogApi(retrofitClient: Retrofit): ICatalogApi =
        retrofitClient.create(ICatalogApi::class.java)

}