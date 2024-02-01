package ru.apteka.catalog.data.services

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.qualifiers.ApplicationContext
import ru.apteka.components.data.utils.PreferencesDelegate
import ru.apteka.components.data.utils.subListOrNull
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Представляет сервис предпочтений поиска продукции.
 * @param context Контекст приложения.
 */
@Singleton
class SearchProductPreferences @Inject constructor(
    @ApplicationContext context: Context
) {

    companion object {
        private const val REQUESTS = "requests"
    }

    private val requestsPref =
        context.getSharedPreferences(
            SearchProductPreferences::class.java.simpleName,
            Context.MODE_PRIVATE
        )

    /**
     * Возвращет список предпочтений поиска.
     */
    private var requests: List<String> by PreferencesDelegate(
        requestsPref,
        REQUESTS,
        emptyList(),
        {
            Gson().toJson(it)
        },
        {
            Gson().fromJson(it, object : TypeToken<List<String>>() {}.type)
        }
    )

    /**
     * Устанавливает значения истории поиска.
     */
    fun setHistoryRequest(request: String) {
        if (!requests.contains(request)) {
            requests = (listOf(request) + requests).subListOrNull(0, 100)!!
        }
    }

    /**
     * Возвращает значения истории поиска.
     */
    fun getHistoryRequest(q: String): List<String> {
        return requests.filter {
            it.contains(q, true)
        }.subListOrNull(0, 5) ?: emptyList()
    }

    /**
     * Очищает значения истории поиска.
     */
    fun clear() {
        requests = emptyList()
    }
}
