package ru.apteka.profile.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import ru.apteka.profile.data.repository.bonus.IBonusApi


@Module
@InstallIn(SingletonComponent::class)
class ProfileApiModule {

    /**
     * Предоставляет экземпляр [IBonusApi].
     */
    @Provides
    fun provideBonusApi(retrofitClient: Retrofit): IBonusApi =
        retrofitClient.create(IBonusApi::class.java)

}