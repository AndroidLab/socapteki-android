package ru.apteka.components.data.services.city


import android.content.Context
import com.google.gson.Gson
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import ru.apteka.common.data.utils.PreferencesDelegate
import ru.apteka.components.data.services.account.models.CityModel
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Представляет предпочтения выбранного города.
 * @param context Контекст приложения.
 */
@Singleton
class CityPreferences @Inject constructor(
    @ApplicationContext context: Context
) {

    companion object {
        private const val CITY = "city"
    }

    private val cityPref =
        context.getSharedPreferences(CityPreferences::class.java.simpleName, Context.MODE_PRIVATE)

    private val _cityFlow = MutableSharedFlow<CityModel?>(replay = 1)

    /**
     * Возвращает поток последнего времени проверки.
     */
    val cityFlow: SharedFlow<CityModel?> = _cityFlow

    /**
     * Возвращет текущий город.
     */
    var city: CityModel? by PreferencesDelegate(
        cityPref,
        CITY,
        null, {
            it?.let { Gson().toJson(it) } ?: ""
        }, {
            if (it == "") null else Gson().fromJson(it, CityModel::class.java)
        },
        prefFlow = _cityFlow
    )

}