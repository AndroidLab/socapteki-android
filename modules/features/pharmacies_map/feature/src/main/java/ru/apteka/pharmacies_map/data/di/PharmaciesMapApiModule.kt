package ru.apteka.pharmacies_map.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import ru.apteka.pharmacies_map.data.repository.pharmacies_map_repository.IPharmaciesMapApi


@Module
@InstallIn(SingletonComponent::class)
class PharmaciesMapApiModule {

    /**
     * Предоставляет экземпляр [IPharmaciesMapApi].
     */
    @Provides
    fun provideApi(retrofitClient: Retrofit): IPharmaciesMapApi =
        retrofitClient.create(IPharmaciesMapApi::class.java)

}