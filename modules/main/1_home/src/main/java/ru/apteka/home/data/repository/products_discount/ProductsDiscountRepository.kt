package ru.apteka.home.data.repository.products_discount

import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.delay
import ru.apteka.components.R
import ru.apteka.components.data.models.DiscountModel
import ru.apteka.components.data.models.Label
import ru.apteka.components.data.models.ProductModel
import java.util.UUID
import javax.inject.Inject

/**
 * Представляет репозиторий проуктов со скидкой.
 * @param productionsDiscountApi Productions discount api.
 */
@ViewModelScoped
class ProductsDiscountRepository @Inject constructor(
    private val productionsDiscountApi: IProductsDiscountApi
) {

    /**
     * Получает акции.
     */
    suspend fun getProductionsDiscount(): List<ProductModel> {
        delay(1300)
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
                    (
                        Label.ADVERT
                    )
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
                    (
                        Label.ADVERT
                    ),
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
                    (
                        Label.ADVERT
                    ),
                )
            ),
        )
    }

}