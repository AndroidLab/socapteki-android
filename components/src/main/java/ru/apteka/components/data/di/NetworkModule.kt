package ru.apteka.components.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.apteka.components.data.services.account.AccountsPreferences
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
internal class NetworkModule {

    /**
     * Представляет экземпляр [OkHttpClient].
     */
    @Provides
    fun provideOkHttpClient(
        accountsService: AccountsPreferences
    ): OkHttpClient = OkHttpClient.Builder().apply {
        hostnameVerifier { _, _ -> true }
        readTimeout(30, TimeUnit.SECONDS)
        connectTimeout(30, TimeUnit.SECONDS)
        addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        addInterceptor(Interceptor { chain ->
            return@Interceptor chain.proceed(
                chain.request().newBuilder().apply {
                    addHeader(
                        "Authorization",
                        "Bearer ${accountsService.account!!.token}"
                    )
                    addHeader("Content-Type", "application/json")
                }.build()
            )
        })
    }.build()

    /**
     * Представляет экземпляр [Retrofit].
     */
    @Provides
    @Singleton
    fun provideRetrofitClient(
        okHttpClient: OkHttpClient
    ): Retrofit = Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl("https://social-apteka.ru/method/")
        //.addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}