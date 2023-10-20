package ru.apteka.choosing_city.data.repository.new_repository

import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.delay
import ru.apteka.components.data.services.user.models.CityModel
import java.util.UUID
import javax.inject.Inject

/**
 * Представляет репозиторий городов.
 * @param citiesApi Cities api.
 */
@ViewModelScoped
class CitiesRepository @Inject constructor(
    private val citiesApi: ICitiesApi
) {

    /**
     * Получает города.
     */
    suspend fun getCities(): List<CityModel> {
        delay(2000)
        return listOf(
            CityModel(
                id = UUID.randomUUID(),
                name = "Москва",
                subtitle = "Москва"
            ),
            CityModel(
                id = UUID.randomUUID(),
                name = "Санкт-Петербург",
                subtitle = "subtitle"
            ),
            CityModel(
                id = UUID.randomUUID(),
                name = "Екатеринбург",
                subtitle = "subtitle"
            ),
            CityModel(
                id = UUID.randomUUID(),
                name = "Нижний Новгород",
                subtitle = "subtitle"
            ),
            CityModel(
                id = UUID.randomUUID(),
                name = "Пермь",
                subtitle = "subtitle"
            ),
            CityModel(
                id = UUID.randomUUID(),
                name = "Новосибирск",
                subtitle = "subtitle"
            ),
            CityModel(
                id = UUID.randomUUID(),
                name = "Казань",
                subtitle = "subtitle"
            ),
            CityModel(
                id = UUID.randomUUID(),
                name = "Челябинск",
                subtitle = "subtitle"
            ),
            CityModel(
                id = UUID.randomUUID(),
                name = "Краснодар",
                subtitle = "subtitle"
            ),
            CityModel(
                id = UUID.randomUUID(),
                name = "Самара",
                subtitle = "subtitle"
            )
        )
    }


}