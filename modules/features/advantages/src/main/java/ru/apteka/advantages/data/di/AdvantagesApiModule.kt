package ru.apteka.advantages.data.di

import IAdvantageApi
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
class AdvantagesApiModule {

    /**
     * Предоставляет экземпляр [IAdvantageApi].
     */
    /*@Provides
    fun provideAdvantagesApi(retrofitClient: Retrofit): IAdvantagesApi =
        retrofitClient.create(IAdvantagesApi::class.java)*/

}