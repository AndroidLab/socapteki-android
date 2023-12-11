package ru.apteka.contacts.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import ru.apteka.contacts.data.repository.contacts.IContactsApi


@Module
@InstallIn(SingletonComponent::class)
class ContactsApiModule {

    /**
     * Предоставляет экземпляр [IContactsApi].
     */
    @Provides
    fun provideContactsApi(retrofitClient: Retrofit): IContactsApi =
        retrofitClient.create(IContactsApi::class.java)

}