import retrofit2.http.POST


/**
 * Описывает методы .
 */
interface IAdvantageApi {

    /**
     * Получает список .
     */
    @POST("/get")
    suspend fun get(): List<Unit>

}