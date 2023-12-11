package ru.apteka.profile.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import ru.apteka.profile.data.repository.apteki.IAptekiApi
import ru.apteka.profile.data.repository.bonus.IBonusApi


@Module
@InstallIn(SingletonComponent::class)
class ProfileApiModule {



    /**
     * Предоставляет экземпляр [IAptekiApi].
     */
    @Provides
    fun provideAptekiApi(retrofitClient: Retrofit): IAptekiApi =
        retrofitClient.create(IAptekiApi::class.java)

    /**
     * Предоставляет экземпляр [IBonusApi].
     */
    @Provides
    fun provideBonusApi(retrofitClient: Retrofit): IBonusApi =
        retrofitClient.create(IBonusApi::class.java)

}