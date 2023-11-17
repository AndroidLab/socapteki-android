package ru.apteka.components.data.services.user


import android.content.Context
import com.google.gson.Gson
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import ru.apteka.components.data.models.DeliveryAddressModel
import ru.apteka.components.data.models.OrderStatus
import ru.apteka.components.data.models.PharmacyModel
import ru.apteka.components.data.utils.PreferencesDelegate
import ru.apteka.components.data.services.user.models.CityModel
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
        private const val CITY = "city"
        private const val ORDER_FILTER = "order_filter"
        private const val SELECTED_PHARMACY = "selected_pharmacy"
        private const val SELECTED_ADDRESS = "selected_address"
    }

    private val userPref =
        context.getSharedPreferences(UserPreferences::class.java.simpleName, Context.MODE_PRIVATE)

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
        null, {
            it?.let { Gson().toJson(it) } ?: ""
        }, {
            if (it == "") null else Gson().fromJson(it, CityModel::class.java)
        },
        prefFlow = _cityFlow
    )


    private val _orderFilterFlow = MutableSharedFlow<OrderStatus>(replay = 1)

    /**
     * Возвращает поток выбранного фильтра заказа.
     */
    val orderFilterFlow: SharedFlow<OrderStatus> = _orderFilterFlow

    /**
     * Возвращет или устанавливает выбранный фильтры заказов.
     */
    var orderFilter: OrderStatus by PreferencesDelegate(
        userPref,
        ORDER_FILTER,
        OrderStatus.ALL, {
            it.name
        }, {
            OrderStatus.valueOf(it)
        },
        _orderFilterFlow
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
        null, {
            it?.let { Gson().toJson(it) } ?: ""
        }, {
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
        null, {
            it?.let { Gson().toJson(it) } ?: ""
        }, {
            if (it == "") null else Gson().fromJson(it, DeliveryAddressModel::class.java)
        },
        _selectedDeliveryAddressFlow
    )

}