package ru.apteka.listing.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import ru.apteka.listing.data.repository.listing.IListingApi


@Module
@InstallIn(SingletonComponent::class)
class ListingApiModule {

    /**
     * Предоставляет экземпляр [IListingApi].
     */
    @Provides
    fun provideListingApi(retrofitClient: Retrofit): IListingApi =
        retrofitClient.create(IListingApi::class.java)

}