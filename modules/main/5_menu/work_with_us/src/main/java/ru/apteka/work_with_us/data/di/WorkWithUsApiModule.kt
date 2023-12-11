package ru.apteka.work_with_us.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import ru.apteka.work_with_us.data.repository.new_repository.WorkWithUsIApi


@Module
@InstallIn(SingletonComponent::class)
class WorkWithUsApiModule {

    /**
     * Предоставляет экземпляр [WorkWithUsIApi].
     */
    @Provides
    fun provideWorkWithUsApi(retrofitClient: Retrofit): WorkWithUsIApi =
        retrofitClient.create(WorkWithUsIApi::class.java)

}