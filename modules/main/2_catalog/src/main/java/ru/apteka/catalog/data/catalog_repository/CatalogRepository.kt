package ru.apteka.catalog.data.catalog_repository

import kotlinx.coroutines.delay
import ru.apteka.components.data.models.DiscountModel
import ru.apteka.components.data.models.ProductModel
import ru.apteka.catalog.data.models.FilterType
import ru.apteka.catalog.data.models.IFilter
import ru.apteka.catalog.data.models.SearchHistoryHeaderModel
import ru.apteka.catalog.data.models.SearchResultModel
import ru.apteka.catalog.data.services.SearchProductPreferences
import ru.apteka.components.data.models.Label
import java.util.UUID
import javax.inject.Inject

/**
 * Представляет репозиторий каталога.
 * @param catalogApi Api.
 */
class CatalogRepository @Inject constructor(
    private val catalogApi: ICatalogApi,
) {
    fun getFilters(): List<IFilter> {
        //delay(1500)
        return listOf(
            IFilter.FilterPriceModel(
                type = FilterType.PRICE,
                title = "Цена",
                minPrice = 100,
                maxPrice = 200,
            ),
            IFilter.FilterReleaseFormModel(
                type = FilterType.RELEASE_FORM,
                title = "Форма выпуска",
                items = listOf(
                    IFilter.FilterReleaseFormModel.ReleaseFormModel(
                        title = "Жидкое",
                        desk = "Капли, настойка, настои, сироп, суспензия, эмульсия"
                    ),
                    IFilter.FilterReleaseFormModel.ReleaseFormModel(
                        title = "Твердые",
                        desk = "Капсула, таблетка, порошки, гранулы, драже, карамель, карандаш"
                    ),
                    IFilter.FilterReleaseFormModel.ReleaseFormModel(
                        title = "Мягкие",
                        desk = "Крем, мазь, гель, суппозитории, пастаsit amet, consectetur"
                    ),
                    IFilter.FilterReleaseFormModel.ReleaseFormModel(
                        title = "Аэрозоли",
                    ),
                )
            ),
            IFilter.FilterManufacturerModel(
                type = FilterType.MANUFACTURER,
                title = "Производитель",
                items = listOf(
                    IFilter.FilterManufacturerModel.ManufacturerModel(
                        title = "Johnson & Johnson",
                    ),
                    IFilter.FilterManufacturerModel.ManufacturerModel(
                        title = "Pfizer",
                    ),
                    IFilter.FilterManufacturerModel.ManufacturerModel(
                        title = "Sinopharm",
                    ),
                    IFilter.FilterManufacturerModel.ManufacturerModel(
                        title = "Roche Holding",
                    ),
                    IFilter.FilterManufacturerModel.ManufacturerModel(
                        title = "Johnson & Johnson",
                    ),
                )
            ),
            IFilter.FilterDiscountsModel(
                type = FilterType.DISCOUNTS,
                title = "Скидки",
                desc = "Только товары со скидкой",
            ),
            IFilter.FilterNosologyModel(
                type = FilterType.NOSOLOGY,
                title = "Нозология",
                items = listOf(
                    IFilter.FilterNosologyModel.NosologyModel(
                        title = "Воспаления и инфекции"
                    ),
                    IFilter.FilterNosologyModel.NosologyModel(
                        title = "Другие"
                    ),
                    IFilter.FilterNosologyModel.NosologyModel(
                        title = "Обезболивающие"
                    ),
                    IFilter.FilterNosologyModel.NosologyModel(
                        title = "Перхоть"
                    ),
                    IFilter.FilterNosologyModel.NosologyModel(
                        title = "Опрелость"
                    )
                ),
            ),
            IFilter.FilterBrandModel(
                type = FilterType.BRAND,
                title = "Бренды",
                items = listOf(
                    IFilter.FilterBrandModel.BrandItemModel(
                        title = "Ауробин",
                    ),
                    IFilter.FilterBrandModel.BrandItemModel(
                        title = "Декарис",
                    ),
                    IFilter.FilterBrandModel.BrandItemModel(
                        title = "Плавикс",
                    ),
                    IFilter.FilterBrandModel.BrandItemModel(
                        title = "Сеафор",
                    )
                )
            ),
            IFilter.FilterCountryModel(
                type = FilterType.COUNTRY,
                title = "Страны",
                items = listOf(
                    IFilter.FilterCountryModel.CountryItemModel(
                        title = "Венгрия",
                    ),
                    IFilter.FilterCountryModel.CountryItemModel(
                        title = "Германия",
                    ),
                    IFilter.FilterCountryModel.CountryItemModel(
                        title = "Россия",
                    ),
                    IFilter.FilterCountryModel.CountryItemModel(
                        title = "Франция",
                    )
                )
            ),
            IFilter.FilterActiveSubstanceModel(
                type = FilterType.ACTIVE_SUBSTANCE,
                title = "Действующее вещество",
                items = listOf(
                    IFilter.FilterActiveSubstanceModel.ActiveSubstanceItemModel(
                        title = "Декспантенол, преднизолон, лидокаин"
                    ),
                    IFilter.FilterActiveSubstanceModel.ActiveSubstanceItemModel(
                        title = "Доксазозин"
                    ),
                    IFilter.FilterActiveSubstanceModel.ActiveSubstanceItemModel(
                        title = "Итраконазол"
                    ),
                    IFilter.FilterActiveSubstanceModel.ActiveSubstanceItemModel(
                        title = "Клопидогрел"
                    ),
                    IFilter.FilterActiveSubstanceModel.ActiveSubstanceItemModel(
                        title = "Левамизол"
                    ),
                    IFilter.FilterActiveSubstanceModel.ActiveSubstanceItemModel(
                        title = "Метформин"
                    ),
                )
            )
        )
    }

    private val searchFake = listOf(
        SearchResultModel(
            type = SearchResultModel.SearchResultType.RESULT,
            text = "горло ангидак"
        ),
        SearchResultModel(
            type = SearchResultModel.SearchResultType.RESULT,
            text = "горло гелангин"
        ),
        SearchResultModel(
            type = SearchResultModel.SearchResultType.RESULT,
            text = "горло звездочка"
        ),
    )

    /**
     * Возвращает результаты поиска.
     */
    suspend fun searchResult(q: String): List<SearchResultModel> {
        delay(1500)
        return searchFake.filter { it.text.contains(q, true) }
    }
}