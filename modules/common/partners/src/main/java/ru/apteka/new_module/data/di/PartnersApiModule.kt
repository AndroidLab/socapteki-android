package ru.apteka.new_module.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import ru.apteka.new_module.data.repository.partners_repository.IPartnersApi


@Module
@InstallIn(SingletonComponent::class)
class PartnersApiModule {

    /**
     * Предоставляет экземпляр [IPartnersApi].
     */
    @Provides
    fun provideApi(retrofitClient: Retrofit): IPartnersApi =
        retrofitClient.create(IPartnersApi::class.java)

}