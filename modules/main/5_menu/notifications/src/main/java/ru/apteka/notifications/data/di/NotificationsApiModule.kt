package ru.apteka.notifications.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import ru.apteka.notifications.data.repository.notifications.INotificationsApi


@Module
@InstallIn(SingletonComponent::class)
class NotificationsApiModule {

    /**
     * Предоставляет экземпляр [INotificationsApi].
     */
    @Provides
    fun provideNotificationsApi(retrofitClient: Retrofit): INotificationsApi =
        retrofitClient.create(INotificationsApi::class.java)

}