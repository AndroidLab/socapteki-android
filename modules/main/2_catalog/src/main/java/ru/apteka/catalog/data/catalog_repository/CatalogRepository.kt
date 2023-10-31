package ru.apteka.catalog.data.catalog_repository

import kotlinx.coroutines.delay
import ru.apteka.components.data.models.DiscountModel
import ru.apteka.components.data.models.ProductModel
import ru.apteka.catalog.data.models.FilterType
import ru.apteka.catalog.data.models.IFilter
import ru.apteka.components.data.models.Label
import java.util.UUID
import javax.inject.Inject

/**
 * Представляет репозиторий каталога.
 * @param catalogApi Api.
 */
class CatalogRepository @Inject constructor(
    private val catalogApi: ICatalogApi
) {

    /**
     * Получает список продуктов.
     */
    suspend fun getProducts(): List<ProductModel> {
        delay(1500)
        return listOf(
            ProductModel(
                id = UUID.randomUUID(),
                image = "https://social-apteka.ru/upload/ammina.optimizer/jpg-webp/q80/upload/resize_cache/iblock/26a/4t2rwziwqy0985ppytp7tlwl473rihud/150_150_0/f2848cc6f2c04f92cd3876228dbdf81f.webp",
                isFavorite = false,
                price = "от 18 913 ₽",
                desc = "Диклофенак-акос гель для наружного применения 5% 50 г",
                rating = "4.7",
                comments = 123,
                discount = DiscountModel(
                    "22 131 ₽",
                    "30%"
                ),
                additionalDesc = "Без рецепта",
                labels = listOf(
                    Label.PRODUCT_DAY
                )
            ),
            ProductModel(
                id = UUID.randomUUID(),
                image = "https://social-apteka.ru/upload/ammina.optimizer/jpg-webp/q80/upload/resize_cache/iblock/26a/4t2rwziwqy0985ppytp7tlwl473rihud/150_150_0/f2848cc6f2c04f92cd3876228dbdf81f.webp",
                isFavorite = false,
                price = "от 16 913 ₽",
                desc = "Диклофенак-акос гель для наружного применения 5% 50 г",
                rating = "4.7",
                comments = 321,
                discount = DiscountModel(
                    "22 131 ₽",
                    "30%"
                ),
                labels = listOf(
                    Label.ADVERT,
                    Label.ADVERT
                )
            ),
            ProductModel(
                id = UUID.randomUUID(),
                image = "https://social-apteka.ru/upload/ammina.optimizer/jpg-webp/q80/upload/resize_cache/iblock/26a/4t2rwziwqy0985ppytp7tlwl473rihud/150_150_0/f2848cc6f2c04f92cd3876228dbdf81f.webp",
                isFavorite = false,
                price = "от 16 913 ₽",
                desc = "Диклофенак-акос гель для наружного применения 5% 50 г",
                rating = "4.7",
                comments = 321,
                labels = listOf(
                    Label.ADVERT
                )
            ),
            ProductModel(
                id = UUID.randomUUID(),
                image = "https://social-apteka.ru/upload/ammina.optimizer/jpg-webp/q80/upload/resize_cache/iblock/26a/4t2rwziwqy0985ppytp7tlwl473rihud/150_150_0/f2848cc6f2c04f92cd3876228dbdf81f.webp",
                isFavorite = false,
                price = "от 16 913 ₽",
                desc = "Диклофенак-акос гель для наружного применения 5% 50 г",
                rating = "4.7",
                comments = 321,
                discount = DiscountModel(
                    "22 131 ₽",
                    "30%"
                ),
                labels = listOf(
                    Label.PRODUCT_DAY,
                    Label.ADVERT
                )
            ),
            ProductModel(
                id = UUID.randomUUID(),
                image = "https://social-apteka.ru/upload/ammina.optimizer/jpg-webp/q80/upload/resize_cache/iblock/26a/4t2rwziwqy0985ppytp7tlwl473rihud/150_150_0/f2848cc6f2c04f92cd3876228dbdf81f.webp",
                isFavorite = false,
                price = "от 16 913 ₽",
                desc = "Диклофенак-акос гель для наружного применения 5% 50 г",
                rating = "4.7",
                comments = 321,
                labels = listOf(
                    Label.ADVERT
                )
            ),
        )
    }

    suspend fun getFilters(): List<IFilter> {
        delay(1500)
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

}