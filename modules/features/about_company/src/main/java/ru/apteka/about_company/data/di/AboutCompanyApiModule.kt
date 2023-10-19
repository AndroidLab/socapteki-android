package ru.apteka.about_company.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import ru.apteka.about_company.data.repository.favorites.IApi


@Module
@InstallIn(SingletonComponent::class)
class AboutCompanyApiModule {

    /**
     * Предоставляет экземпляр [IApi].
     */
    @Provides
    fun provideApi(retrofitClient: Retrofit): IApi =
        retrofitClient.create(IApi::class.java)

}