package ru.apteka.home.data.repository.apteki

import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.delay
import ru.apteka.home.data.models.AptekaModel
import ru.apteka.home.data.models.OtherModel
import java.util.UUID
import javax.inject.Inject

/**
 * Представляет репозиторий аптек.
 * @param aptekiApi Apteki api.
 */
@ViewModelScoped
class AptekiRepository @Inject constructor(
    private val aptekiApi: IAptekiApi
) {

    /**
     * Получает аптеки.
     */
    suspend fun getApteki(): List<AptekaModel> {
        delay(1500)
        return listOf(
            AptekaModel(
                uuid = UUID.randomUUID(),
                image = "https://avatars.mds.yandex.net/i?id=4a3b191facbad44a986b95afede6fc6e5e44a656-10415038-images-thumbs&n=13",
                title = "Аптека «Социальная аптека»",
                desc = "ул. Богдана Хмельницкого, 154 Ежедневно с 09:00 до 20:00",
                number = "+7 (987) 654 32-10",
                isFavorite = true
            ),
            AptekaModel(
                uuid = UUID.randomUUID(),
                image = "https://avatars.mds.yandex.net/i?id=4a3b191facbad44a986b95afede6fc6e5e44a656-10415038-images-thumbs&n=13",
                title = "Аптека «Социальная аптека»",
                desc = "ул. Богдана Хмельницкого, 154 Ежедневно с 09:00 до 20:00",
                number = "+7 (987) 654 32-10",
                isFavorite = false
            ),
            AptekaModel(
                uuid = UUID.randomUUID(),
                image = "https://avatars.mds.yandex.net/i?id=4a3b191facbad44a986b95afede6fc6e5e44a656-10415038-images-thumbs&n=13",
                title = "Аптека «Социальная аптека»",
                desc = "ул. Богдана Хмельницкого, 154 Ежедневно с 09:00 до 20:00",
                number = "+7 (987) 654 32-10",
                isFavorite = false
            ),
            AptekaModel(
                uuid = UUID.randomUUID(),
                image = "https://avatars.mds.yandex.net/i?id=4a3b191facbad44a986b95afede6fc6e5e44a656-10415038-images-thumbs&n=13",
                title = "Аптека «Социальная аптека»",
                desc = "ул. Богдана Хмельницкого, 154 Ежедневно с 09:00 до 20:00",
                number = "+7 (987) 654 32-10",
                isFavorite = false
            ),
            AptekaModel(
                uuid = UUID.randomUUID(),
                image = "https://avatars.mds.yandex.net/i?id=4a3b191facbad44a986b95afede6fc6e5e44a656-10415038-images-thumbs&n=13",
                title = "Аптека «Социальная аптека»",
                desc = "ул. Богдана Хмельницкого, 154 Ежедневно с 09:00 до 20:00",
                number = "+7 (987) 654 32-10",
                isFavorite = false
            ),
        )
    }


}