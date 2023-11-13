package ru.apteka.pharmacies_map.data.repository.pharmacies_map_repository

import kotlinx.coroutines.delay
import ru.apteka.components.data.models.PharmacyModel
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
                id = UUID.fromString("a602d47a-13f0-4925-9eeb-5741206f59a2"),
                name = "Аптека 1 «Социальная Аптека»",
                address = "Москва, Варшавское шоссе, 143а\nМетро «Аннино»\nЕжедневно с 08:00 до 21:00",
                pickup = "13 сентября",
                isFavorite = true,
                coordinates = Pair(55.751280, 37.629720)
            ),
            PharmacyModel(
                id = UUID.fromString("c2f79f5a-437a-405e-be25-4dda44d40d3c"),
                name = "Аптека 2 «Социальная Аптека»",
                address = "Москва, Варшавское шоссе, 143а\nМетро «Аннино»\nЕжедневно с 08:00 до 21:00",
                pickup = "14 сентября",
                isFavorite = true,
                coordinates = Pair(55.778055, 37.608712)
            ),
            PharmacyModel(
                id = UUID.fromString("6a51072d-5738-4d5a-8813-625799206450"),
                name = "Аптека 3 «Социальная Аптека»",
                address = "Москва, Варшавское шоссе, 143а\nМетро «Аннино»\nЕжедневно с 08:00 до 21:00",
                pickup = "15 сентября",
                isFavorite = false,
                coordinates = Pair(55.770504, 37.639697)
            ),
            PharmacyModel(
                id = UUID.fromString("58d775ec-8ad9-41fd-860a-892da2a065b7"),
                name = "Аптека 4 «Социальная Аптека»",
                address = "Москва, Варшавское шоссе, 143а\nМетро «Аннино»\nЕжедневно с 08:00 до 21:00",
                pickup = "16 сентября",
                isFavorite = true,
                coordinates = Pair(55.758592, 37.625496)
            ),
            PharmacyModel(
                id = UUID.fromString("daed3ca8-1c9b-4048-a615-f56d2a066dea"),
                name = "Аптека 5 «Социальная Аптека»",
                address = "Москва, Варшавское шоссе, 143а\nМетро «Аннино»\nЕжедневно с 08:00 до 21:00",
                pickup = "17 сентября",
                isFavorite = true,
                coordinates = Pair(55.756994, 37.607163)
            ),
            PharmacyModel(
                id = UUID.fromString("96182354-c269-45dd-874f-eb6731e4bf01"),
                name = "Аптека 6 «Социальная Аптека»",
                address = "Москва, Варшавское шоссе, 143а\nМетро «Аннино»\nЕжедневно с 08:00 до 21:00",
                pickup = "17 сентября",
                isFavorite = true,
                coordinates = Pair(55.750019, 37.640730)
            ),
            PharmacyModel(
                id = UUID.fromString("45ab7bc0-deda-4167-906d-e9e4e501a3fc"),
                name = "Аптека 7 «Социальная Аптека»",
                address = "Москва, Варшавское шоссе, 143а\nМетро «Аннино»\nЕжедневно с 08:00 до 21:00",
                pickup = "17 сентября",
                isFavorite = true,
                coordinates = Pair(55.743189, 37.618782)
            ),

        )
    }


}