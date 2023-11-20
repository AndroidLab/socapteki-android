package ru.apteka.licenses.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import ru.apteka.licenses.data.repository.licenses_repository.ILicensesApi


@Module
@InstallIn(SingletonComponent::class)
class LicensesApiModule {

    /**
     * Предоставляет экземпляр [ILicensesApi].
     */
    @Provides
    fun provideLicensesApi(retrofitClient: Retrofit): ILicensesApi =
        retrofitClient.create(ILicensesApi::class.java)

}