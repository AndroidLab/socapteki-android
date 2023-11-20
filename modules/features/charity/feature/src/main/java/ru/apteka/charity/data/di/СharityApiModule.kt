package ru.apteka.feedback.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import ru.apteka.feedback.data.repository.feedback.IСharityApi


@Module
@InstallIn(SingletonComponent::class)
class СharityApiModule {

    /**
     * Предоставляет экземпляр [IСharityApi].
     */
    @Provides
    fun provideApi(retrofitClient: Retrofit): IСharityApi =
        retrofitClient.create(IСharityApi::class.java)

}