package ru.apteka.faq.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import ru.apteka.feedback.data.repository.feedback.IFaqApi

@Module
@InstallIn(SingletonComponent::class)
class FaqApiModule {

    /**
     * Предоставляет экземпляр [IFaqApi].
     */
    @Provides
    fun provideApi(retrofitClient: Retrofit): IFaqApi =
        retrofitClient.create(IFaqApi::class.java)

}