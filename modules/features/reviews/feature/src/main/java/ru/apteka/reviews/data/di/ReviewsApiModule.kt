package ru.apteka.reviews.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import ru.apteka.reviews.data.repository.reviews.IReviewsApi


@Module
@InstallIn(SingletonComponent::class)
class ReviewsApiModule {

    /**
     * Предоставляет экземпляр [IReviewsApi].
     */
    @Provides
    fun provideReviewsApi(retrofitClient: Retrofit): IReviewsApi =
        retrofitClient.create(IReviewsApi::class.java)

}