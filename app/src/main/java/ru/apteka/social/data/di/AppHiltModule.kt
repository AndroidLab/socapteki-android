package ru.apteka.social.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.apteka.social.data.repository.IDocsApi
import java.util.concurrent.TimeUnit
import javax.inject.Named


@Module
@InstallIn(SingletonComponent::class)
internal class AppHiltModule {

    /**
     * Представляет экземпляр [OkHttpClient].
     */
    @Named("vk")
    @Provides
    fun provideVKOkHttpClient(): OkHttpClient = OkHttpClient.Builder().apply {
        hostnameVerifier { _, _ -> true }
        readTimeout(30, TimeUnit.SECONDS)
        connectTimeout(30, TimeUnit.SECONDS)
        addInterceptor(Interceptor { chain ->
            return@Interceptor chain.proceed(
                chain.request().newBuilder().apply {
                    addHeader("Content-Type", "application/json")
                }.build()
            )
        })
    }.build()

    /**
     * Представляет экземпляр [Retrofit].
     */
    @Named("vk")
    @Provides
    fun provideVKRetrofitClient(
        @Named("vk") okHttpClient: OkHttpClient
    ): Retrofit = Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl("https://api.vk.com/method/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()


    /**
     * Предоставляет экземпляр [IDocsApi].
     */
    @Provides
    fun provideDocsApi(@Named("vk") retrofitClient: Retrofit): IDocsApi =
        retrofitClient.create(IDocsApi::class.java)

}