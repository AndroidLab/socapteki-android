package ru.apteka.home.data.repository.other

import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.delay
import ru.apteka.home.data.models.OtherModel
import java.util.UUID
import javax.inject.Inject

/**
 * Представляет репозиторий остальное.
 * @param promotionApi Promotion api.
 */
@ViewModelScoped
class OtherRepository @Inject constructor(
    private val promotionApi: IOtherApi
) {

    /**
     * Получает.
     */
    suspend fun getOther(): List<OtherModel> {
        delay(3000)
        return listOf(
            OtherModel(
                id = UUID.randomUUID(),
            ),
            OtherModel(
                id = UUID.randomUUID(),
            ),
            OtherModel(
                id = UUID.randomUUID(),
            )
        )
    }


}