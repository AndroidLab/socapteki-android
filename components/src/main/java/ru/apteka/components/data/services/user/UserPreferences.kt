package ru.apteka.components.data.services.user

import android.content.Context
import com.google.gson.Gson
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import ru.apteka.components.data.models.DeliveryAddressModel
import ru.apteka.components.data.models.PharmacyModel
import ru.apteka.components.data.services.user.models.CityModel
import ru.apteka.components.data.utils.PreferencesDelegate
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Представляет предпочтения выбранного города.
 * @param context Контекст приложения.
 */
@Singleton
class UserPreferences @Inject constructor(
    @ApplicationContext context: Context
) {

    companion object {
        private const val LAST_VERSION_CHECKED = "LAST_VERSION_CHECKED"
        private const val CITY = "city"
        private const val JOB_OPENINGS_CITY_FILTER = "job_openings_cCity_filter"
        private const val SELECTED_PHARMACY = "selected_pharmacy"
        private const val SELECTED_ADDRESS = "selected_address"
    }

    private val userPref =
        context.getSharedPreferences(UserPreferences::class.java.simpleName, Context.MODE_PRIVATE)

    /**
     * Возвращает последнюю версию приложения готовую для обновления.
     */
    var lastVersionChecked: Float by PreferencesDelegate<Float, Float>(
        userPref,
        LAST_VERSION_CHECKED,
        -1f
    )

    private val _cityFlow = MutableSharedFlow<CityModel?>(replay = 1)

    /**
     * Возвращает поток последнего времени проверки.
     */
    val cityFlow: SharedFlow<CityModel?> = _cityFlow

    /**
     * Возвращет или устанавливает текущий город.
     */
    var city: CityModel? by PreferencesDelegate(
        userPref,
        CITY,
        null,
        {
            it?.let { Gson().toJson(it) } ?: ""
        },
        {
            if (it == "") null else Gson().fromJson(it, CityModel::class.java)
        },
        prefFlow = _cityFlow
    )

    private val _jobOpeningsCityFilterFlow = MutableSharedFlow<String>(replay = 1)

    /**
     * Возвращает поток выбранного фильтра заказа.
     */
    val jobOpeningsCityFilterFlow: SharedFlow<String> = _jobOpeningsCityFilterFlow

    /**
     * Возвращет или устанавливает выбранный фильтры заказов.
     */
    var jobOpeningsCityFilter: String by PreferencesDelegate<String, String>(
        userPref,
        JOB_OPENINGS_CITY_FILTER,
        "Все города",
        prefFlow = _jobOpeningsCityFilterFlow
    )

    private val _selectedPharmacyFlow = MutableSharedFlow<PharmacyModel?>(replay = 1)

    /**
     * Возвращает поток выбоанной аптеки.
     */
    val selectedPharmacyFlow: SharedFlow<PharmacyModel?> = _selectedPharmacyFlow

    /**
     * Возвращет или устанавливает выбранную аптеку.
     */
    var selectedPharmacy: PharmacyModel? by PreferencesDelegate(
        userPref,
        SELECTED_PHARMACY,
        null,
        {
            it?.let { Gson().toJson(it) } ?: ""
        },
        {
            if (it == "") null else Gson().fromJson(it, PharmacyModel::class.java)
        },
        _selectedPharmacyFlow
    )

    private val _selectedDeliveryAddressFlow = MutableSharedFlow<DeliveryAddressModel?>(replay = 1)

    /**
     * Возвращает поток выбоанного адреса.
     */
    val selectedDeliveryAddressFlow: SharedFlow<DeliveryAddressModel?> =
        _selectedDeliveryAddressFlow

    /**
     * Возвращет или устанавливает выбранный адрес.
     */
    var selectedDeliveryAddress: DeliveryAddressModel? by PreferencesDelegate(
        userPref,
        SELECTED_ADDRESS,
        null,
        {
            it?.let { Gson().toJson(it) } ?: ""
        },
        {
            if (it == "") null else Gson().fromJson(it, DeliveryAddressModel::class.java)
        },
        _selectedDeliveryAddressFlow
    )
}
