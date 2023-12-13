package ru.apteka.referral_program.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import ru.apteka.referral_program.data.repository.referral_program.IApi


@Module
@InstallIn(SingletonComponent::class)
class ReferralProgramApiModule {

    /**
     * Предоставляет экземпляр [IApi].
     */
    @Provides
    fun provideReferralProgramApi(retrofitClient: Retrofit): IApi =
        retrofitClient.create(IApi::class.java)

}