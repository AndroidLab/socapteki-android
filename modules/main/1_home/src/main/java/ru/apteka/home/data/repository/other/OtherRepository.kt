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
        delay(1500)
        return listOf(
            OtherModel(
                id = UUID.randomUUID(),
                title = "Для детей",
                description = "Подгузники, детское питание, гигиена для малышей",
                image = "https://buroperevodov.su/800/600/https/kpoxainfo.ru/wp-content/uploads/2019/06/5-kubiki.jpg",

            ),
            OtherModel(
                id = UUID.randomUUID(),
                title = "Для детей",
                description = "Подгузники, детское питание, гигиена для малышей",
                image = "https://tacon.ru/wp-content/uploads/5/1/6/51631949d7d3448e4afd987cbbbdc851.jpeg"
            ),
            OtherModel(
                id = UUID.randomUUID(),
                title = "Красота",
                description = "Лицо, косметика",
                image = "https://img.goodfon.ru/original/1440x900/4/28/devushka-vesna-priroda-cvety.jpg"
            )
        )
    }


}