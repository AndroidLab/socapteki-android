package ru.apteka.barcode_scanner.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import ru.apteka.new_module.data.repository.new_repository.IApi


@Module
@InstallIn(SingletonComponent::class)
class NewModuleApiModule {

    /**
     * Предоставляет экземпляр [IApi].
     */
    @Provides
    fun provideBarcodeApi(retrofitClient: Retrofit): IApi =
        retrofitClient.create(IApi::class.java)

}