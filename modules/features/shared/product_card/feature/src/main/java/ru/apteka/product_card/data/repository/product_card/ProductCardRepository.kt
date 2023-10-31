package ru.apteka.product_card.data.repository.product_card

import kotlinx.coroutines.delay
import ru.apteka.components.data.models.DiscountModel
import ru.apteka.components.data.models.Label
import ru.apteka.components.data.models.ProductModel
import ru.apteka.components.data.models.ProductVariantModel
import ru.apteka.product_card.data.model.CommentModel
import ru.apteka.product_card.data.model.InstructionModel
import java.util.UUID
import javax.inject.Inject

/**
 * Представляет репозиторий деталей продукции.
 * @param Api  api.
 */
class ProductCardRepository @Inject constructor(
    private val productCardApi: IProductCardApi
) {

    /**
     * Получает детали карточки.
     */
    suspend fun getProductDetails(): ProductModel {
        //delay(1500)
        return ProductModel(
            id = UUID.randomUUID(),
            image = "https://social-apteka.ru/upload/ammina.optimizer/jpg-webp/q80/upload/resize_cache/iblock/26a/4t2rwziwqy0985ppytp7tlwl473rihud/150_150_0/f2848cc6f2c04f92cd3876228dbdf81f.webp",
            images = listOf(
                "https://social-apteka.ru/upload/ammina.optimizer/jpg-webp/q80/upload/resize_cache/iblock/26a/4t2rwziwqy0985ppytp7tlwl473rihud/150_150_0/f2848cc6f2c04f92cd3876228dbdf81f.webp",
                "https://social-apteka.ru/upload/ammina.optimizer/jpg-webp/q80/upload/resize_cache/iblock/26a/4t2rwziwqy0985ppytp7tlwl473rihud/150_150_0/f2848cc6f2c04f92cd3876228dbdf81f.webp",
                "https://social-apteka.ru/upload/ammina.optimizer/jpg-webp/q80/upload/resize_cache/iblock/26a/4t2rwziwqy0985ppytp7tlwl473rihud/150_150_0/f2848cc6f2c04f92cd3876228dbdf81f.webp"
            ),
            isFavorite = false,
            price = "от 2 200 ₽",
            desc = "Диклофенак-акос гель для наружного применения 5% 50 г",
            rating = "4.7",
            comments = 123,
            discount = DiscountModel(
                oldPrice = "22 131 ₽",
                percent = "30%"
            ),
            additionalDesc = "Без рецепта",
            releaseForm = "Какая то форма выпуска",
            manufacturer = "Индия, Патеон",
            activeSubstance = "Ибупрофен",
            dosage = "400 мг",
            expirationDate = "36 месяцев",
            pickupDate = "13 сентября",
            homeDeliveryDate = "13 сентября",
            variants = listOf(
                ProductVariantModel(
                    title = "Форма выпуска",
                    variants = listOf(
                        ProductVariantModel.VariantItem(
                            name = "Капсулы"
                        ),
                        ProductVariantModel.VariantItem(
                            name = "Гранулы"
                        ),
                    )
                ),
                ProductVariantModel(
                    title = "Дозировка",
                    variants = listOf(
                        ProductVariantModel.VariantItem(
                            name = "10 тыч ед"
                        ),
                        ProductVariantModel.VariantItem(
                            name = "50 тыч ед"
                        ),
                    )
                ),
                ProductVariantModel(
                    title = "Количество",
                    variants = listOf(
                        ProductVariantModel.VariantItem(
                            name = "20 шт"
                        ),
                        ProductVariantModel.VariantItem(
                            name = "50 шт"
                        ),
                    )
                )
            ),
            labels = listOf(
                Label.CHECKED_SPECIALIST,
                Label.PRODUCT_DAY,
                Label.WITHOUT_PRESCRIPTION,
                Label.HIT_SALES,
                Label.ADVERT,
            ),
            countInBasket = 0,
        )
    }


    /**
     * Возвращает инструкцию по товару.
     */
    fun getInstruction(): InstructionModel {
        return InstructionModel(
            instructions = listOf(
                InstructionModel.InstructionItem(
                    title = "Фармакотерапевтическая группа",
                    desc = "Антиаритмическое средство класса IB, местный анестетик, производное ацетанилида."
                ),
                InstructionModel.InstructionItem(
                    title = "Способ применения и дозы",
                    desc = "Антиаритмическое средство класса IB, местный анестетик, производное ацетанилида."
                ),
                InstructionModel.InstructionItem(
                    title = "Применение при беременности и кормлении грудью",
                    desc = "Антиаритмическое средство класса IB, местный анестетик, производное ацетанилида."
                ),
                InstructionModel.InstructionItem(
                    title = "Противопоказания",
                    desc = "Антиаритмическое средство класса IB, местный анестетик, производное ацетанилида."
                ),
                InstructionModel.InstructionItem(
                    title = "Взаимодействие",
                    desc = "Антиаритмическое средство класса IB, местный анестетик, производное ацетанилида."
                ),
                InstructionModel.InstructionItem(
                    title = "Влияние на способность управлять транспортными средствами, механизмами",
                    desc = "Антиаритмическое средство класса IB, местный анестетик, производное ацетанилида."
                ),
                InstructionModel.InstructionItem(
                    title = "Организация, принимающая претензии потребителей",
                    desc = "Антиаритмическое средство класса IB, местный анестетик, производное ацетанилида."
                )
            )
        )
    }

    /**
     * Возвращает комментарии к продукту.
     */
    fun getProductComments(): CommentModel {
        return CommentModel(
            rating = 4.7f,
            commentCount = 123,
            comments = listOf(
                CommentModel.CommentItem(
                    name = "Пескова Наталья",
                    rating = 3.3f,
                    date = 1698920656,
                    text = "Супер пробиотик, действительно помогает. Лучший из подобных препаратов"
                ),
                CommentModel.CommentItem(
                    name = "Пескова Наталья",
                    rating = 2.3f,
                    date = 1698920656,
                    text = "Супер пробиотик, действительно помогает. Лучший из подобных препаратов"
                ),
                CommentModel.CommentItem(
                    name = "Пескова Наталья",
                    rating = 1.3f,
                    date = 1698920656,
                    text = "Супер пробиотик, действительно помогает. Лучший из подобных препаратов"
                ),
            )
        )
    }


}