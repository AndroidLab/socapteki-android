package ru.apteka.our_partners.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import ru.apteka.our_partners.data.repository.our_partners_repository.IOurPartnersApi


@Module
@InstallIn(SingletonComponent::class)
class OurPartnersApiModule {

    /**
     * Предоставляет экземпляр [IOurPartnersApi].
     */
    @Provides
    fun provideOurPartnersApi(retrofitClient: Retrofit): IOurPartnersApi =
        retrofitClient.create(IOurPartnersApi::class.java)

}