package ru.apteka.feedback.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import ru.apteka.charity.data.repository.charity.ICharityApi


@Module
@InstallIn(SingletonComponent::class)
class СharityApiModule {

    /**
     * Предоставляет экземпляр [ICharityApi].
     */
    @Provides
    fun provideApi(retrofitClient: Retrofit): ICharityApi =
        retrofitClient.create(ICharityApi::class.java)

}