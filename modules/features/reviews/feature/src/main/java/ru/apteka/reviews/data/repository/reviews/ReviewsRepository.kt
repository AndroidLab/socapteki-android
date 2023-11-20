package ru.apteka.reviews.data.repository.reviews

import kotlinx.coroutines.delay
import javax.inject.Inject

/**
 * Представляет репозиторий .
 * @param Api  api.
 */
class ReviewsRepository @Inject constructor(
    private val newModuleApi: IReviewsApi
) {

    /**
     * Получает .
     */
    suspend fun get(): List<Unit> {
        delay(1500)
        return listOf()
    }


}