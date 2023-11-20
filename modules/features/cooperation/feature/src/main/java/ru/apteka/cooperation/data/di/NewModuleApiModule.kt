package ru.apteka.cooperation.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import ru.apteka.feedback.data.repository.feedback.ICooperationApi


@Module
@InstallIn(SingletonComponent::class)
class NewModuleApiModule {

    /**
     * Предоставляет экземпляр [ICooperationApi].
     */
    @Provides
    fun provideCooperationApi(retrofitClient: Retrofit): ICooperationApi =
        retrofitClient.create(ICooperationApi::class.java)

}