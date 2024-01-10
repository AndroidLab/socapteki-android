package ru.apteka.social.data.repository

import androidx.annotation.Keep
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import ru.apteka.social.data.models.Docs

/**
 * Описывает методы обновления приложения.
 */
@Keep
interface IDocsApi {

    /**
     * Возвращает список файлов.
     */
    @GET("docs.get")
    suspend fun docsGet(
        @Query("count") count: UInt? = null,
        @Query("offset") offset: UInt? = null,
        @Query("type") type: UInt? = null,
        @Query("owner_id") ownerId: Int? = null,
    ): Response<Docs>

}