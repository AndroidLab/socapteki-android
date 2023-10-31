package ru.apteka.pharmacies_map.data.repository.pharmacies_map_repository

import kotlinx.coroutines.delay
import ru.apteka.pharmacies_map.data.model.PharmacyModel
import java.util.UUID
import javax.inject.Inject

/**
 * Представляет репозиторий аптек.
 * @param ApiPharmacies Api pharmacies.
 */
class PharmaciesMapRepository @Inject constructor(
    private val pharmaciesMapApi: IPharmaciesMapApi
) {

    /**
     * Получает аптеки.
     */
    suspend fun getPharmacies(): List<PharmacyModel> {
        delay(2500)
        return listOf(
            PharmacyModel(
                id = UUID.randomUUID(),
                title = "Аптека 1 «Социальная Аптека»",
                desc = "Москва, Варшавское шоссе, 143а\nМетро «Аннино»\nЕжедневно с 08:00 до 21:00",
                pickup = "13 сентября",
                isFavorite = true,
                coordinates = Pair(55.751280, 37.629720)
            ),
            PharmacyModel(
                id = UUID.randomUUID(),
                title = "Аптека 2 «Социальная Аптека»",
                desc = "Москва, Варшавское шоссе, 143а\nМетро «Аннино»\nЕжедневно с 08:00 до 21:00",
                pickup = "14 сентября",
                isFavorite = true,
                coordinates = Pair(55.778055, 37.608712)
            ),
            PharmacyModel(
                id = UUID.randomUUID(),
                title = "Аптека 3 «Социальная Аптека»",
                desc = "Москва, Варшавское шоссе, 143а\nМетро «Аннино»\nЕжедневно с 08:00 до 21:00",
                pickup = "15 сентября",
                isFavorite = false,
                coordinates = Pair(55.770504, 37.639697)
            ),
            PharmacyModel(
                id = UUID.randomUUID(),
                title = "Аптека 4 «Социальная Аптека»",
                desc = "Москва, Варшавское шоссе, 143а\nМетро «Аннино»\nЕжедневно с 08:00 до 21:00",
                pickup = "16 сентября",
                isFavorite = true,
                coordinates = Pair(55.758592, 37.625496)
            ),
            PharmacyModel(
                id = UUID.randomUUID(),
                title = "Аптека 5 «Социальная Аптека»",
                desc = "Москва, Варшавское шоссе, 143а\nМетро «Аннино»\nЕжедневно с 08:00 до 21:00",
                pickup = "17 сентября",
                isFavorite = true,
                coordinates = Pair(55.756994, 37.607163)
            ),
            PharmacyModel(
                id = UUID.randomUUID(),
                title = "Аптека 6 «Социальная Аптека»",
                desc = "Москва, Варшавское шоссе, 143а\nМетро «Аннино»\nЕжедневно с 08:00 до 21:00",
                pickup = "17 сентября",
                isFavorite = true,
                coordinates = Pair(55.750019, 37.640730)
            ),
            PharmacyModel(
                id = UUID.randomUUID(),
                title = "Аптека 7 «Социальная Аптека»",
                desc = "Москва, Варшавское шоссе, 143а\nМетро «Аннино»\nЕжедневно с 08:00 до 21:00",
                pickup = "17 сентября",
                isFavorite = true,
                coordinates = Pair(55.743189, 37.618782)
            ),

        )
    }


}