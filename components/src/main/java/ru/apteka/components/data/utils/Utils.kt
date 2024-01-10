package ru.apteka.components.data.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.style.ClickableSpan
import android.util.Log
import android.view.View
import kotlinx.coroutines.delay
import ru.apteka.components.R
import ru.apteka.components.data.models.DiscountModel
import ru.apteka.components.data.models.Label
import ru.apteka.components.data.models.ProductModel
import ru.apteka.components.data.models.ProductVariantModel
import java.util.UUID
import java.util.regex.Pattern


/**
 * Валидирует емайл.
 */
fun validateEmail(email: String): Boolean {
    val VALID_EMAIL_ADDRESS_REGEX =
        Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE)
    return if (email.isEmpty()) true else VALID_EMAIL_ADDRESS_REGEX.matcher(email).matches()
}

/**
 * Возвращает span персональных данных.
 */
fun getPersonalDataSpan(context: Context): SpannableString {
    val span = object : ClickableSpan() {
        override fun onClick(textView: View) {
            context.startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://social-apteka.ru/about/confidentiality/")
                )
            )
        }

        override fun updateDrawState(ds: TextPaint) {
            super.updateDrawState(ds)
            ds.isUnderlineText = true
        }
    }
    return SpannableString(context.getString(R.string.consent_personal_data)).apply {
        setSpan(span, length - 29, length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
    }
}


private val images = listOf(
    "https://social-apteka.ru/upload/ammina.optimizer/jpg-webp/q80/upload/resize_cache/iblock/26a/4t2rwziwqy0985ppytp7tlwl473rihud/150_150_0/f2848cc6f2c04f92cd3876228dbdf81f.webp",
    "https://social-apteka.ru/upload/ammina.optimizer/jpg-webp/q80/upload/resize_cache/iblock/26a/4t2rwziwqy0985ppytp7tlwl473rihud/150_150_0/f2848cc6f2c04f92cd3876228dbdf81f.webp",
    "https://social-apteka.ru/upload/ammina.optimizer/jpg-webp/q80/upload/resize_cache/iblock/26a/4t2rwziwqy0985ppytp7tlwl473rihud/150_150_0/f2848cc6f2c04f92cd3876228dbdf81f.webp"
)

private val variants = listOf(
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
    ) {

    },
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
    ) {

    },
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
    ) {
        Log.d("myL", "Количество " + it)
    }
)

/**
 * Получает продукты.
 */
suspend fun getProductsFake(): List<ProductModel> {
    delay(1000)
    return listOf(
        ProductModel(
            id = UUID.randomUUID(),
            image = "https://social-apteka.ru/upload/ammina.optimizer/jpg-webp/q80/upload/resize_cache/iblock/26a/4t2rwziwqy0985ppytp7tlwl473rihud/150_150_0/f2848cc6f2c04f92cd3876228dbdf81f.webp",
            images = images,
            variants = variants,
            isFavorite = false,
            price = "от 18 913 ₽",
            title = "Диклофенак-акос гель для наружного применения 5% 50 г адлфвоа фдлоа жофд аафвлождало фоа жофр ажшор жфщшаро щшгрофашщрофщжгаро фш а",
            rating = "4.7",
            comments = 123,
            discount = DiscountModel(
                "22 131 ₽",
                "30%"
            ),
            desc = "Шрея Лайф Саенсиз Пвт.Лтд, Индия",
            additionalDesc = "Имеются  противопоказания Необходимо консультация со специалистом",
            labels = listOf(
                (
                        Label.ADVERT
                        )
            )
        ),
        ProductModel(
            id = UUID.randomUUID(),
            image = "https://social-apteka.ru/upload/ammina.optimizer/jpg-webp/q80/upload/resize_cache/iblock/26a/4t2rwziwqy0985ppytp7tlwl473rihud/150_150_0/f2848cc6f2c04f92cd3876228dbdf81f.webp",
            images = images,
            variants = variants,
            isFavorite = false,
            price = "от 16 913 ₽",
            title = "Диклофенак-акос гель для наружного применения 5% 50 г",
            rating = "4.7",
            comments = 321,
            discount = DiscountModel(
                "22 131 ₽",
                "30%"
            ),
            additionalDesc = "Имеются  противопоказания Необходимо консультация со специалистом",
            labels = listOf(
                (
                        Label.ADVERT
                        ),
                (
                        Label.PRODUCT_DAY
                        )
            )
        ),
        ProductModel(
            id = UUID.randomUUID(),
            image = "https://social-apteka.ru/upload/ammina.optimizer/jpg-webp/q80/upload/resize_cache/iblock/26a/4t2rwziwqy0985ppytp7tlwl473rihud/150_150_0/f2848cc6f2c04f92cd3876228dbdf81f.webp",
            images = images,
            variants = variants,
            isFavorite = false,
            price = "от 16 913 ₽",
            title = "Диклофенак-акос гель для наружного применения 5% 50 г",
            rating = "4.7",
            comments = 321,
            labels = listOf(
                (
                        Label.ADVERT
                        ),
                (
                        Label.PRODUCT_DAY
                        )
            )
        ),
        ProductModel(
            id = UUID.randomUUID(),
            image = "https://social-apteka.ru/upload/ammina.optimizer/jpg-webp/q80/upload/resize_cache/iblock/26a/4t2rwziwqy0985ppytp7tlwl473rihud/150_150_0/f2848cc6f2c04f92cd3876228dbdf81f.webp",
            images = images,
            variants = variants,
            isFavorite = false,
            price = "от 16 913 ₽",
            title = "Диклофенак-акос гель для наружного применения 5% 50 г",
            rating = "4.7",
            comments = 321,
        ),
        ProductModel(
            id = UUID.randomUUID(),
            image = "https://social-apteka.ru/upload/ammina.optimizer/jpg-webp/q80/upload/resize_cache/iblock/26a/4t2rwziwqy0985ppytp7tlwl473rihud/150_150_0/f2848cc6f2c04f92cd3876228dbdf81f.webp",
            images = images,
            variants = variants,
            isFavorite = false,
            price = "от 18 913 ₽",
            title = "Диклофенак-акос гель для наружного применения 5% 50 г адлфвоа фдлоа жофд аафвлождало фоа жофр ажшор жфщшаро щшгрофашщрофщжгаро фш а",
            rating = "4.7",
            comments = 123,
            discount = DiscountModel(
                "22 131 ₽",
                "30%"
            ),
            desc = "Шрея Лайф Саенсиз Пвт.Лтд, Индия",
            additionalDesc = "Имеются  противопоказания Необходимо консультация со специалистом",
            labels = listOf(
                (
                        Label.ADVERT
                        )
            )
        ),
        ProductModel(
            id = UUID.randomUUID(),
            image = "https://social-apteka.ru/upload/ammina.optimizer/jpg-webp/q80/upload/resize_cache/iblock/26a/4t2rwziwqy0985ppytp7tlwl473rihud/150_150_0/f2848cc6f2c04f92cd3876228dbdf81f.webp",
            images = images,
            variants = variants,
            isFavorite = false,
            price = "от 18 913 ₽",
            title = "Диклофенак-акос гель для наружного применения 5% 50 г адлфвоа фдлоа жофд аафвлождало фоа жофр ажшор жфщшаро щшгрофашщрофщжгаро фш а",
            rating = "4.7",
            comments = 123,
            discount = DiscountModel(
                "22 131 ₽",
                "30%"
            ),
            desc = "Шрея Лайф Саенсиз Пвт.Лтд, Индия",
            additionalDesc = "Имеются  противопоказания Необходимо консультация со специалистом",
            labels = listOf(
                (
                        Label.ADVERT
                        )
            )
        ),
        ProductModel(
            id = UUID.randomUUID(),
            image = "https://social-apteka.ru/upload/ammina.optimizer/jpg-webp/q80/upload/resize_cache/iblock/26a/4t2rwziwqy0985ppytp7tlwl473rihud/150_150_0/f2848cc6f2c04f92cd3876228dbdf81f.webp",
            images = images,
            variants = variants,
            isFavorite = false,
            price = "от 18 913 ₽",
            title = "Диклофенак-акос гель для наружного применения 5% 50 г адлфвоа фдлоа жофд аафвлождало фоа жофр ажшор жфщшаро щшгрофашщрофщжгаро фш а",
            rating = "4.7",
            comments = 123,
            discount = DiscountModel(
                "22 131 ₽",
                "30%"
            ),
            desc = "Шрея Лайф Саенсиз Пвт.Лтд, Индия",
            additionalDesc = "Имеются  противопоказания Необходимо консультация со специалистом",
            labels = listOf(
                (
                        Label.ADVERT
                        )
            )
        ),
    )
}

/**
 * Получает продукты.
 */
suspend fun getProductsFake2(): List<ProductModel> {
    return listOf(
        ProductModel(
            id = UUID.randomUUID(),
            image = "https://social-apteka.ru/upload/ammina.optimizer/jpg-webp/q80/upload/resize_cache/iblock/26a/4t2rwziwqy0985ppytp7tlwl473rihud/150_150_0/f2848cc6f2c04f92cd3876228dbdf81f.webp",
            images = images,
            variants = variants,
            isFavorite = false,
            price = "от 18 913 ₽",
            title = "Диклофенак-акос гель для наружного применения 5% 50 г адлфвоа фдлоа жофд аафвлождало фоа жофр ажшор жфщшаро щшгрофашщрофщжгаро фш а",
            rating = "4.7",
            comments = 123,
            discount = DiscountModel(
                "22 131 ₽",
                "30%"
            ),
            desc = "Шрея Лайф Саенсиз Пвт.Лтд, Индия",
            additionalDesc = "Имеются  противопоказания Необходимо консультация со специалистом",
            labels = listOf(
                (
                        Label.ADVERT
                        )
            )
        ),
        ProductModel(
            id = UUID.randomUUID(),
            image = "https://social-apteka.ru/upload/ammina.optimizer/jpg-webp/q80/upload/resize_cache/iblock/26a/4t2rwziwqy0985ppytp7tlwl473rihud/150_150_0/f2848cc6f2c04f92cd3876228dbdf81f.webp",
            images = images,
            variants = variants,
            isFavorite = false,
            price = "от 16 913 ₽",
            title = "Диклофенак-акос гель для наружного применения 5% 50 г",
            rating = "4.7",
            comments = 321,
            discount = DiscountModel(
                "22 131 ₽",
                "30%"
            ),
            additionalDesc = "Имеются  противопоказания Необходимо консультация со специалистом",
            labels = listOf(
                (
                        Label.ADVERT
                        ),
                (
                        Label.PRODUCT_DAY
                        )
            )
        ),
        ProductModel(
            id = UUID.randomUUID(),
            image = "https://social-apteka.ru/upload/ammina.optimizer/jpg-webp/q80/upload/resize_cache/iblock/26a/4t2rwziwqy0985ppytp7tlwl473rihud/150_150_0/f2848cc6f2c04f92cd3876228dbdf81f.webp",
            images = images,
            variants = variants,
            isFavorite = false,
            price = "от 16 913 ₽",
            title = "Диклофенак-акос гель для наружного применения 5% 50 г",
            rating = "4.7",
            comments = 321,
            labels = listOf(
                (
                        Label.ADVERT
                        ),
                (
                        Label.PRODUCT_DAY
                        )
            )
        ),
    )
}