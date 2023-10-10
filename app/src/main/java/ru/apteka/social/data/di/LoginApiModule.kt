package ru.apteka.social.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import ru.apteka.social.data.repository.remote.api.ILoginApi


@Module
@InstallIn(SingletonComponent::class)
class LoginApiModule {

    /**
     * Предоставляет экземпляр [LoginApi].
     */
    @Provides
    fun provideExecuteApi(retrofitClient: Retrofit): ILoginApi =
        retrofitClient.create(ILoginApi::class.java)

}