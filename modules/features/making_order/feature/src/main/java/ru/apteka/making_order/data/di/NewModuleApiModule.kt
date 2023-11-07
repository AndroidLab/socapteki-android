package ru.apteka.making_order.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import ru.apteka.making_order.data.repository.new_repository.IMakingOrderApi


@Module
@InstallIn(SingletonComponent::class)
class NewModuleApiModule {

    /**
     * Предоставляет экземпляр [IMakingOrderApi].
     */
    @Provides
    fun provideMakingOrderApi(retrofitClient: Retrofit): IMakingOrderApi =
        retrofitClient.create(IMakingOrderApi::class.java)

}