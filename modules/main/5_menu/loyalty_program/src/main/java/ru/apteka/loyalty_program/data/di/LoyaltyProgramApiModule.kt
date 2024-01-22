package ru.apteka.loyalty_program.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import ru.apteka.loyalty_program.data.repository.loyalty_program.IApi


@Module
@InstallIn(SingletonComponent::class)
class LoyaltyProgramApiModule {

    /**
     * Предоставляет экземпляр [IApi].
     */
    @Provides
    fun provideReferralProgramApi(retrofitClient: Retrofit): IApi =
        retrofitClient.create(IApi::class.java)

}