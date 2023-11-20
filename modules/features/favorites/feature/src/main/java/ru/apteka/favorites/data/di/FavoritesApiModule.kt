package ru.apteka.favorites.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import ru.apteka.favorites.data.repository.favorites.IFavoritesApi


@Module
@InstallIn(SingletonComponent::class)
class FavoritesApiModule {

    /**
     * Предоставляет экземпляр [IFavoritesApi].
     */
    @Provides
    fun provideFavoritesApi(retrofitClient: Retrofit): IFavoritesApi =
        retrofitClient.create(IFavoritesApi::class.java)

}