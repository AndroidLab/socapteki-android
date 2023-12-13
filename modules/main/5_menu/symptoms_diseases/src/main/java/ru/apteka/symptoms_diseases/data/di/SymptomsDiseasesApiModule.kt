package ru.apteka.symptoms_diseases.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import ru.apteka.symptoms_diseases.data.repository.symptoms_diseases.IApi


@Module
@InstallIn(SingletonComponent::class)
class SymptomsDiseasesApiModule {

    /**
     * Предоставляет экземпляр [IApi].
     */
    @Provides
    fun provideSymptomsDiseasesApi(retrofitClient: Retrofit): IApi =
        retrofitClient.create(IApi::class.java)

}