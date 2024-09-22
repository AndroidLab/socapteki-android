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
    val validMailRegex =
        Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE)
    return if (email.isEmpty()) true else validMailRegex.matcher(email).matches()
}

/**
 * Возвращает значение номера.
 */
fun getPhoneRaw(phone: String) =
    phone.replace("+7", "")
        .replace("(", "")
        .replace(")", "")
        .replace("-", "")
        .replace(" ", "")

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
 * Возвращает продукты.
 */
suspend fun getProductsFake(): List<ProductModel> {
    delay(1000)
    return listOf(
        ProductModel(
            id = UUID.randomUUID(),
            image = "https://social-apteka.ru/upload/ammina.optimizer/jpg-webp/q80/upload/resize_cache/iblock/7e0/gjdf42sxmtg8gl5e5b3cii97u7n5ucje/160_160_0/alrg10mg.webp",
            isFavorite = false,
            price = "от 169 ₽",
            title = "Аллергостин таб п/пл/о 10мг N10 (Полисан)",
            rating = "4.7",
            comments = 12,
            needRecipe = false,
            images = listOf(
                "https://social-apteka.ru/upload/ammina.optimizer/jpg-webp/q80/upload/resize_cache/iblock/7e0/gjdf42sxmtg8gl5e5b3cii97u7n5ucje/160_160_0/alrg10mg.webp",
                "https://social-apteka.ru/upload/ammina.optimizer/jpg-webp/q80/upload/resize_cache/iblock/7e0/gjdf42sxmtg8gl5e5b3cii97u7n5ucje/160_160_0/alrg10mg.webp",
                "https://social-apteka.ru/upload/ammina.optimizer/jpg-webp/q80/upload/resize_cache/iblock/7e0/gjdf42sxmtg8gl5e5b3cii97u7n5ucje/160_160_0/alrg10mg.webp",
            ),
            discount = DiscountModel(
                "179 ₽",
                "20%"
            ),
            desc = null,
            additionalDesc = "Имеются  противопоказания Необходимо консультация со специалистом",
            releaseForm = "Таблетки, покрытые плёночной оболочкой, 10 мг",
            manufacturer = "Поличан",
            manufacturerCountry = "Россия",
            activeSubstance = "Эбластин",
            dosage = null,
            expirationDate = null,
            pickupDate = null,
            homeDeliveryDate = null,
            variants = variants,
            labels = listOf(
                Label.ADVERT
            ),
            disclaimer = "БАД НЕ ЯВЛЯЕТСЯ ЛЕКАРСТВЕННЫМ ПРЕПАРАТОМ"
        ),
        ProductModel(
            id = UUID.randomUUID(),
            image = "https://social-apteka.ru/upload/ammina.optimizer/jpeg-webp/q80/upload/resize_cache/iblock/own/320_320_0/PREV_16921-1.webp",
            isFavorite = false,
            price = "от 158 ₽",
            title = "Цетиризин таб 10мг N30 (Вертекс)",
            rating = "5.0",
            comments = 2,
            needRecipe = false,
            images = listOf(
                "https://social-apteka.ru/upload/ammina.optimizer/jpeg-webp/q80/upload/resize_cache/iblock/own/320_320_0/PREV_16921-1.webp",
                "https://social-apteka.ru/upload/ammina.optimizer/jpeg-webp/q80/upload/resize_cache/iblock/own/320_320_0/PREV_16921-1.webp",
                "https://social-apteka.ru/upload/ammina.optimizer/jpeg-webp/q80/upload/resize_cache/iblock/own/320_320_0/PREV_16921-1.webp",
            ),
            discount = null,
            desc = null,
            additionalDesc = "Имеются  противопоказания Необходимо консультация со специалистом",
            releaseForm = "Таблетки",
            manufacturer = "Вертекс",
            manufacturerCountry = "Россия",
            activeSubstance = "Цетиризин",
            dosage = null,
            expirationDate = null,
            pickupDate = null,
            homeDeliveryDate = null,
            variants = variants,
            labels = listOf(
                Label.ADVERT
            ),
            disclaimer = "ИМЕЮТСЯ ПРОТИВОПОКАЗАНИЯ НЕОБХОДИМА КОНСУЛЬТАЦИЯ С СПЕЦИАЛИСТОМ"
        ),
        ProductModel(
            id = UUID.randomUUID(),
            image = "https://social-apteka.ru/upload/ammina.optimizer/jpg-webp/q80/upload/resize_cache/iblock/own/320_320_0/PREV_17186-1.webp",
            isFavorite = false,
            price = "от 92 ₽",
            title = "Лоратадин таб 10мг N30 (Вертекс)",
            rating = "4.7",
            comments = 0,
            needRecipe = false,
            images = listOf(
                "https://social-apteka.ru/upload/ammina.optimizer/jpg-webp/q80/upload/resize_cache/iblock/own/320_320_0/PREV_17186-1.webp",
                "https://social-apteka.ru/upload/ammina.optimizer/jpg-webp/q80/upload/resize_cache/iblock/own/320_320_0/PREV_17186-1.webp",
                "https://social-apteka.ru/upload/ammina.optimizer/jpg-webp/q80/upload/resize_cache/iblock/own/320_320_0/PREV_17186-1.webp",
            ),
            discount = DiscountModel(
                "98 ₽",
                "9%"
            ),
            desc = null,
            additionalDesc = "Имеются  противопоказания Необходимо консультация со специалистом",
            releaseForm = "Таблетки, плоскоцилиндрические с фаской, 10 мг ",
            manufacturer = "Вертекс",
            manufacturerCountry = "Россия",
            activeSubstance = "Лоратадин",
            dosage = null,
            expirationDate = null,
            pickupDate = null,
            homeDeliveryDate = null,
            variants = variants,
            labels = listOf(
                Label.ADVERT
            ),
            disclaimer = "ИМЕЮТСЯ ПРОТИВОПОКАЗАНИЯ НЕОБХОДИМА КОНСУЛЬТАЦИЯ С СПЕЦИАЛИСТОМ"
        ),
        ProductModel(
            id = UUID.randomUUID(),
            image = "https://social-apteka.ru/upload/ammina.optimizer/jpg-webp/q80/upload/resize_cache/iblock/150/320_320_0/1502eaf7297014c4e8614da9c1a63cef.webp",
            isFavorite = false,
            price = "от 345 ₽",
            title = "Витамир Ледишарм витамины д/волос таб N30 (Квадрат-С)",
            rating = "4.2",
            comments = 22,
            needRecipe = false,
            images = listOf(
                "https://social-apteka.ru/upload/ammina.optimizer/jpg-webp/q80/upload/resize_cache/iblock/150/320_320_0/1502eaf7297014c4e8614da9c1a63cef.webp",
                "https://social-apteka.ru/upload/ammina.optimizer/jpg-webp/q80/upload/resize_cache/iblock/150/320_320_0/1502eaf7297014c4e8614da9c1a63cef.webp",
                "https://social-apteka.ru/upload/ammina.optimizer/jpg-webp/q80/upload/resize_cache/iblock/150/320_320_0/1502eaf7297014c4e8614da9c1a63cef.webp",
            ),
            discount = DiscountModel(
                "399 ₽",
                "20%"
            ),
            desc = null,
            additionalDesc = "Имеются  противопоказания Необходимо консультация со специалистом",
            releaseForm = null,
            manufacturer = "Квадрат-С",
            manufacturerCountry = "Россия",
            activeSubstance = null,
            dosage = null,
            expirationDate = null,
            pickupDate = null,
            homeDeliveryDate = null,
            variants = variants,
            labels = listOf(
                Label.ADVERT
            ),
            disclaimer = "БАД НЕ ЯВЛЯЕТСЯ ЛЕКАРСТВЕННЫМ ПРЕПАРАТОМ"
        ),
        ProductModel(
            id = UUID.randomUUID(),
            image = "https://social-apteka.ru/upload/ammina.optimizer/jpg-webp/q80/upload/resize_cache/iblock/eb6/320_320_0/eb6fd745730f175591a7a411bb127cb3.webp",
            isFavorite = false,
            price = "от 219 ₽",
            title = "Аллергостин таб п/пл/о 10мг N10 (Полисан)",
            rating = "0.0",
            comments = 111,
            needRecipe = false,
            images = listOf(
                "https://social-apteka.ru/upload/ammina.optimizer/jpg-webp/q80/upload/resize_cache/iblock/eb6/320_320_0/eb6fd745730f175591a7a411bb127cb3.webp",
                "https://social-apteka.ru/upload/ammina.optimizer/jpg-webp/q80/upload/resize_cache/iblock/eb6/320_320_0/eb6fd745730f175591a7a411bb127cb3.webp",
                "https://social-apteka.ru/upload/ammina.optimizer/jpg-webp/q80/upload/resize_cache/iblock/eb6/320_320_0/eb6fd745730f175591a7a411bb127cb3.webp",
            ),
            discount = DiscountModel(
                "279 ₽",
                "20%"
            ),
            desc = "Количество в упаковке: 100",
            additionalDesc = "Имеются  противопоказания Необходимо консультация со специалистом",
            releaseForm = "Капсулы",
            manufacturer = "Мирролла",
            manufacturerCountry = "Россия",
            activeSubstance = "Эбластин",
            dosage = null,
            expirationDate = null,
            pickupDate = null,
            homeDeliveryDate = null,
            variants = variants,
            labels = listOf(
                //Label.ADVERT
            ),
            disclaimer = "ИМЕЮТСЯ ПРОТИВОПОКАЗАНИЯ НЕОБХОДИМА КОНСУЛЬТАЦИЯ С СПЕЦИАЛИСТОМ"
        ),
        ProductModel(
            id = UUID.randomUUID(),
            image = "https://social-apteka.ru/upload/ammina.optimizer/jpeg-webp/q80/upload/resize_cache/iblock/489/320_320_0/6f7d71bb56414ce7bb2262fc139033b4.webp",
            isFavorite = false,
            price = "от 499 ₽",
            title = "Аллергостин таб п/пл/о 10мг N10 (Полисан)",
            rating = "4.3",
            comments = 999,
            needRecipe = false,
            images = listOf(
                "https://social-apteka.ru/upload/ammina.optimizer/jpeg-webp/q80/upload/resize_cache/iblock/489/320_320_0/6f7d71bb56414ce7bb2262fc139033b4.webp",
                "https://social-apteka.ru/upload/ammina.optimizer/jpeg-webp/q80/upload/resize_cache/iblock/489/320_320_0/6f7d71bb56414ce7bb2262fc139033b4.webp",
                "https://social-apteka.ru/upload/ammina.optimizer/jpeg-webp/q80/upload/resize_cache/iblock/489/320_320_0/6f7d71bb56414ce7bb2262fc139033b4.webp",
            ),
            discount = null,
            desc = null,
            additionalDesc = "Имеются  противопоказания Необходимо консультация со специалистом",
            releaseForm = "Спрей",
            manufacturer = "Фамар Нидерланды Б.В",
            manufacturerCountry = "Нидерланды",
            activeSubstance = null,
            dosage = null,
            expirationDate = null,
            pickupDate = null,
            homeDeliveryDate = null,
            variants = variants,
            labels = listOf(
                //Label.ADVERT
            ),
            disclaimer = "БАД НЕ ЯВЛЯЕТСЯ ЛЕКАРСТВЕННЫМ ПРЕПАРАТОМ"
        ),
        ProductModel(
            id = UUID.randomUUID(),
            image = "https://social-apteka.ru/upload/ammina.optimizer/jpg-webp/q80/upload/resize_cache/iblock/1ae/320_320_0/1ae90adc6f7f54039980a2fb55f92960.webp",
            isFavorite = false,
            price = "от 899 ₽",
            title = "Гентос капли 50мл (Лагофарм)",
            rating = "3.8",
            comments = 2,
            needRecipe = false,
            images = listOf(
                "https://social-apteka.ru/upload/ammina.optimizer/jpg-webp/q80/upload/resize_cache/iblock/1ae/320_320_0/1ae90adc6f7f54039980a2fb55f92960.webp",
                "https://social-apteka.ru/upload/ammina.optimizer/jpg-webp/q80/upload/resize_cache/iblock/1ae/320_320_0/1ae90adc6f7f54039980a2fb55f92960.webp",
                "https://social-apteka.ru/upload/ammina.optimizer/jpg-webp/q80/upload/resize_cache/iblock/1ae/320_320_0/1ae90adc6f7f54039980a2fb55f92960.webp",
            ),
            discount = DiscountModel(
                "1299 ₽",
                "35%"
            ),
            desc = null,
            additionalDesc = "Имеются  противопоказания Необходимо консультация со специалистом",
            releaseForm = "капли гомеопатические",
            manufacturer = "Рихард Биттнер АГ",
            manufacturerCountry = "Австрия",
            activeSubstance = "Эбластин",
            dosage = null,
            expirationDate = null,
            pickupDate = null,
            homeDeliveryDate = null,
            variants = variants,
            labels = listOf(
                Label.HIT_SALES
            ),
            disclaimer = "ИМЕЮТСЯ ПРОТИВОПОКАЗАНИЯ НЕОБХОДИМА КОНСУЛЬТАЦИЯ С СПЕЦИАЛИСТОМ"
        ),
        ProductModel(
            id = UUID.randomUUID(),
            image = "https://social-apteka.ru/upload/ammina.optimizer/jpg-webp/q80/upload/resize_cache/iblock/7f1/m1jgt7xej3j64mwb8pia0037i8rqlvsu/320_320_0/gevkamenmaz25gsamaramedprom.webp",
            isFavorite = false,
            price = "от 89 ₽",
            title = "Гэвкамен мазь 25г (Самарамедпром)",
            rating = "3.8",
            comments = 2,
            needRecipe = false,
            images = listOf(
                "https://social-apteka.ru/upload/ammina.optimizer/jpg-webp/q80/upload/resize_cache/iblock/7f1/m1jgt7xej3j64mwb8pia0037i8rqlvsu/320_320_0/gevkamenmaz25gsamaramedprom.webp",
                "https://social-apteka.ru/upload/ammina.optimizer/jpg-webp/q80/upload/resize_cache/iblock/7f1/m1jgt7xej3j64mwb8pia0037i8rqlvsu/320_320_0/gevkamenmaz25gsamaramedprom.webp",
                "https://social-apteka.ru/upload/ammina.optimizer/jpg-webp/q80/upload/resize_cache/iblock/7f1/m1jgt7xej3j64mwb8pia0037i8rqlvsu/320_320_0/gevkamenmaz25gsamaramedprom.webp",
            ),
            discount = null,
            desc = null,
            additionalDesc = "Имеются  противопоказания Необходимо консультация со специалистом",
            releaseForm = "Мазь для наружного применения, 25г",
            manufacturer = "Самарамедпром",
            manufacturerCountry = "Россия",
            activeSubstance = "Эвкалипта шарикового листьев + масло Камфоры + Левоментол",
            dosage = null,
            expirationDate = null,
            pickupDate = null,
            homeDeliveryDate = null,
            variants = variants,
            labels = listOf(
                Label.PRODUCT_DAY
            ),
            disclaimer = "ИМЕЮТСЯ ПРОТИВОПОКАЗАНИЯ НЕОБХОДИМА КОНСУЛЬТАЦИЯ С СПЕЦИАЛИСТОМ"
        ),
        ProductModel(
            id = UUID.randomUUID(),
            image = "https://social-apteka.ru/upload/ammina.optimizer/jpg-webp/q80/upload/resize_cache/iblock/own/320_320_0/PREV_37899-1.webp",
            isFavorite = false,
            price = "от 463 ₽",
            title = "Гомеострес таб д/расс N40 (Буарон)",
            rating = "3.8",
            comments = 2,
            needRecipe = false,
            images = listOf(
                "https://social-apteka.ru/upload/ammina.optimizer/jpg-webp/q80/upload/resize_cache/iblock/own/320_320_0/PREV_37899-1.webp",
                "https://social-apteka.ru/upload/ammina.optimizer/jpg-webp/q80/upload/resize_cache/iblock/own/320_320_0/PREV_37899-1.webp",
                "https://social-apteka.ru/upload/ammina.optimizer/jpg-webp/q80/upload/resize_cache/iblock/own/320_320_0/PREV_37899-1.webp",
            ),
            discount = null,
            desc = null,
            additionalDesc = "Имеются  противопоказания Необходимо консультация со специалистом",
            releaseForm = "таблетки для рассасывания",
            manufacturer = "Лаборатория Буарон",
            manufacturerCountry = "Франция",
            activeSubstance = null,
            dosage = null,
            expirationDate = null,
            pickupDate = null,
            homeDeliveryDate = null,
            variants = variants,
            labels = listOf(
                Label.PRODUCT_DAY,
                Label.ADVERT
            ),
            disclaimer = "ИМЕЮТСЯ ПРОТИВОПОКАЗАНИЯ НЕОБХОДИМА КОНСУЛЬТАЦИЯ С СПЕЦИАЛИСТОМ"
        ),
    )
}

/**
 * Возвращает продукты.
 */
fun getProductsFake2(): List<ProductModel> {
    return listOf(
        ProductModel(
            id = UUID.randomUUID(),
            image = "https://social-apteka.ru/upload/ammina.optimizer/jpeg-webp/q80/upload/resize_cache/iblock/489/320_320_0/6f7d71bb56414ce7bb2262fc139033b4.webp",
            isFavorite = false,
            price = "от 499 ₽",
            title = "Аллергостин таб п/пл/о 10мг N10 (Полисан)",
            rating = "4.3",
            comments = 999,
            needRecipe = false,
            images = listOf(
                "https://social-apteka.ru/upload/ammina.optimizer/jpeg-webp/q80/upload/resize_cache/iblock/489/320_320_0/6f7d71bb56414ce7bb2262fc139033b4.webp",
                "https://social-apteka.ru/upload/ammina.optimizer/jpeg-webp/q80/upload/resize_cache/iblock/489/320_320_0/6f7d71bb56414ce7bb2262fc139033b4.webp",
                "https://social-apteka.ru/upload/ammina.optimizer/jpeg-webp/q80/upload/resize_cache/iblock/489/320_320_0/6f7d71bb56414ce7bb2262fc139033b4.webp",
            ),
            discount = null,
            desc = null,
            additionalDesc = "Имеются  противопоказания Необходимо консультация со специалистом",
            releaseForm = "Спрей",
            manufacturer = "Фамар Нидерланды Б.В",
            manufacturerCountry = "Нидерланды",
            activeSubstance = null,
            dosage = null,
            expirationDate = null,
            pickupDate = null,
            homeDeliveryDate = null,
            variants = variants,
            labels = listOf(
                //Label.ADVERT
            ),
        ),
        ProductModel(
            id = UUID.randomUUID(),
            image = "https://social-apteka.ru/upload/ammina.optimizer/jpg-webp/q80/upload/resize_cache/iblock/1ae/320_320_0/1ae90adc6f7f54039980a2fb55f92960.webp",
            isFavorite = false,
            price = "от 899 ₽",
            title = "Гентос капли 50мл (Лагофарм)",
            rating = "3.8",
            comments = 2,
            needRecipe = false,
            images = listOf(
                "https://social-apteka.ru/upload/ammina.optimizer/jpg-webp/q80/upload/resize_cache/iblock/1ae/320_320_0/1ae90adc6f7f54039980a2fb55f92960.webp",
                "https://social-apteka.ru/upload/ammina.optimizer/jpg-webp/q80/upload/resize_cache/iblock/1ae/320_320_0/1ae90adc6f7f54039980a2fb55f92960.webp",
                "https://social-apteka.ru/upload/ammina.optimizer/jpg-webp/q80/upload/resize_cache/iblock/1ae/320_320_0/1ae90adc6f7f54039980a2fb55f92960.webp",
            ),
            discount = DiscountModel(
                "1299 ₽",
                "35%"
            ),
            desc = null,
            additionalDesc = "Имеются  противопоказания Необходимо консультация со специалистом",
            releaseForm = "капли гомеопатические",
            manufacturer = "Рихард Биттнер АГ",
            manufacturerCountry = "Австрия",
            activeSubstance = "Эбластин",
            dosage = null,
            expirationDate = null,
            pickupDate = null,
            homeDeliveryDate = null,
            variants = variants,
            labels = listOf(
                Label.HIT_SALES
            ),
        ),
        ProductModel(
            id = UUID.randomUUID(),
            image = "https://social-apteka.ru/upload/ammina.optimizer/jpg-webp/q80/upload/resize_cache/iblock/7f1/m1jgt7xej3j64mwb8pia0037i8rqlvsu/320_320_0/gevkamenmaz25gsamaramedprom.webp",
            isFavorite = false,
            price = "от 89 ₽",
            title = "Гэвкамен мазь 25г (Самарамедпром)",
            rating = "3.8",
            comments = 2,
            needRecipe = false,
            images = listOf(
                "https://social-apteka.ru/upload/ammina.optimizer/jpg-webp/q80/upload/resize_cache/iblock/7f1/m1jgt7xej3j64mwb8pia0037i8rqlvsu/320_320_0/gevkamenmaz25gsamaramedprom.webp",
                "https://social-apteka.ru/upload/ammina.optimizer/jpg-webp/q80/upload/resize_cache/iblock/7f1/m1jgt7xej3j64mwb8pia0037i8rqlvsu/320_320_0/gevkamenmaz25gsamaramedprom.webp",
                "https://social-apteka.ru/upload/ammina.optimizer/jpg-webp/q80/upload/resize_cache/iblock/7f1/m1jgt7xej3j64mwb8pia0037i8rqlvsu/320_320_0/gevkamenmaz25gsamaramedprom.webp",
            ),
            discount = null,
            desc = null,
            additionalDesc = "Имеются  противопоказания Необходимо консультация со специалистом",
            releaseForm = "Мазь для наружного применения, 25г",
            manufacturer = "Самарамедпром",
            manufacturerCountry = "Россия",
            activeSubstance = "Эвкалипта шарикового листьев + масло Камфоры + Левоментол",
            dosage = null,
            expirationDate = null,
            pickupDate = null,
            homeDeliveryDate = null,
            variants = variants,
            labels = listOf(
                Label.PRODUCT_DAY
            ),
        ),
    )
}
