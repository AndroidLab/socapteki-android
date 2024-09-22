package ru.apteka.making_order.data.services

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import ru.apteka.components.data.utils.PreferencesDelegate
import ru.apteka.making_order.data.model.RecipientModel
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Представляет предпочтения заказа.
 * @param context Контекст приложения.
 */
@Singleton
class MakingOrderPreferences @Inject constructor(
    @ApplicationContext context: Context
) {

    companion object {
        private const val ORDER_RECIPIENTS = "order_recipients"
    }

    private val orderRecipientsPref =
        context.getSharedPreferences(MakingOrderPreferences::class.java.simpleName, Context.MODE_PRIVATE)

    private val _orderRecipientsFlow = MutableSharedFlow<List<RecipientModel>>(replay = 1)

    /**
     * Возвращает поток выбранного фильтра заказа.
     */
    val orderRecipientsFlow: SharedFlow<List<RecipientModel>> = _orderRecipientsFlow

    /**
     * Возвращет или устанавливает выбранный фильтры заказов.
     */
    var orderRecipients: List<RecipientModel> by PreferencesDelegate(
        preferences = orderRecipientsPref,
        name = ORDER_RECIPIENTS,
        defValue = emptyList(),
        onDifficultTypeTransform = {
            Json.encodeToString(it)
        },
        onPrimitiveTypeTransform = {
            Log.d("myL", "Rec " + it)
            Json.decodeFromString(it)
        },
        _orderRecipientsFlow
    )
}
