package ru.apteka.feedback.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import ru.apteka.feedback.data.repository.feedback.IFeedbackApi


@Module
@InstallIn(SingletonComponent::class)
class FeedbackApiModule {

    /**
     * Предоставляет экземпляр [IFeedbackApi].
     */
    @Provides
    fun provideFeedbackApi(retrofitClient: Retrofit): IFeedbackApi =
        retrofitClient.create(IFeedbackApi::class.java)

}