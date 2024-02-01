package ru.apteka.making_order.presentation.making_order

import android.util.Log
import androidx.core.os.bundleOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.asLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import ru.apteka.components.data.models.DiscountModel
import ru.apteka.components.data.models.Label
import ru.apteka.components.data.models.ProductModel
import ru.apteka.components.data.repository.kogin.LoginRepository
import ru.apteka.components.data.services.RequestHandler
import ru.apteka.components.data.services.message_notice_service.MessageService
import ru.apteka.components.data.services.navigation_manager.NavigationManager
import ru.apteka.components.data.services.user.UserPreferences
import ru.apteka.components.data.utils.getPhoneRaw
import ru.apteka.components.data.utils.launchIO
import ru.apteka.components.data.utils.navigateWithAnim
import ru.apteka.components.ui.BaseViewModel
import ru.apteka.making_order.R
import ru.apteka.making_order.data.model.DeliveryDateModel
import ru.apteka.making_order.data.model.DeliveryMethodsModel
import ru.apteka.making_order.data.model.DeliveryType
import ru.apteka.making_order.data.model.PaymentsMethodsModel
import ru.apteka.making_order.data.model.RecipientModel
import ru.apteka.making_order_api.api.MAKING_ORDER_ARGUMENT_PRODUCT
import ru.apteka.pharmacies_map_api.api.PHARMACIES_MAP_TYPE_INTERACTION
import ru.apteka.pharmacies_map_api.api.TypeInteraction
import java.util.UUID
import javax.inject.Inject
import ru.apteka.pharmacies_map_api.R as PharmaciesMapApiR

/**
 * Представляет модель представления "Оформление заказа".
 */
@HiltViewModel
class MakingOrderViewModel @Inject constructor(
    private val requestHandler: RequestHandler,
    private val userPreferences: UserPreferences,
    private val savedStateHandle: SavedStateHandle,
    private val loginRepository: LoginRepository,
    navigationManager: NavigationManager,
    messageService: MessageService
) : BaseViewModel(
    navigationManager,
    messageService
) {

    //
    val additionalProducts = listOf(
        ProductModel(
            id = UUID.randomUUID(),
            image = "https://social-apteka.ru/upload/ammina.optimizer/jpg-webp/q80/upload/resize_cache/iblock/26a/4t2rwziwqy0985ppytp7tlwl473rihud/150_150_0/f2848cc6f2c04f92cd3876228dbdf81f.webp",
            isFavorite = false,
            price = "от 18 913 ₽",
            title = "Диклофенак-акос гель для наружного применения 5% 50 г адлфвоа фдлоа жофд аафвлождало фоа жофр ажшор жфщшаро щшгрофашщрофщжгаро фш а",
            rating = "4.7",
            comments = 123,
            discount = DiscountModel(
                "22 131 ₽",
                "30%"
            ),
            desc = "Без рецепта",
            labels = listOf(
                Label.PRODUCT_DAY
            )
        ),
        ProductModel(
            id = UUID.randomUUID(),
            image = "https://social-apteka.ru/upload/ammina.optimizer/jpg-webp/q80/upload/resize_cache/iblock/26a/4t2rwziwqy0985ppytp7tlwl473rihud/150_150_0/f2848cc6f2c04f92cd3876228dbdf81f.webp",
            isFavorite = false,
            price = "от 16 913 ₽",
            title = "Диклофенак-акос гель для наружного применения 5% 50 г",
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
    )

    /**
     * Возвращает или устанавливает список продукции для заказа.
     */
    val productsForOrder = MutableLiveData(
        savedStateHandle.get<Array<ProductModel>>(MAKING_ORDER_ARGUMENT_PRODUCT)!!.toList()
    )

    /**
     * Возвращает медель выбора способа оплаты.
     */
    val paymentsMethods = PaymentsMethodsModel {
        Log.d("myL", "paymentsMethods " + it)
    }

    /**
     * Возвращает выбранную аптеку.
     */
    val selectedPharmacy = userPreferences.selectedPharmacyFlow.asLiveData()

    /**
     * Возвращает выбранный адрес.
     */
    val selectedDeliveryAddress = userPreferences.selectedDeliveryAddressFlow.asLiveData()

    /**
     * Возвращает выбранное время.
     */
    val selectedDeliveryDate = MutableLiveData<DeliveryDateModel?>(null)

    /**
     * Возвращает медель выбора способа доставки.
     */
    val deliveryMethods = DeliveryMethodsModel(
        pickPharmacy = DeliveryMethodsModel.Item.PickPharmacy(),
        yandexDelivery = DeliveryMethodsModel.Item.YandexDelivery(
            title = R.string.making_order_shipping_methods_yandex_delivery,
            price = "99 ₽",
            address = selectedDeliveryAddress,
            date = selectedDeliveryDate
        ),
        courierDelivery = DeliveryMethodsModel.Item.CourierDelivery(
            title = R.string.making_order_shipping_methods_сourier_delivery,
            price = "199 ₽",
            address = selectedDeliveryAddress,
            date = selectedDeliveryDate
        )
    ) { item ->
        fun navigateToAddressFragment(deliveryType: DeliveryType) {
            navigationManager.generalNavController.navigateWithAnim(
                MakingOrderFragmentDirections.toMakingOrderAddressFragment(deliveryType)
            )
        }

        when (item) {
            is DeliveryMethodsModel.Item.PickPharmacy -> {
                navigationManager.generalNavController.navigateWithAnim(
                    PharmaciesMapApiR.id.pharmacies_map_graph,
                    bundleOf(
                        PHARMACIES_MAP_TYPE_INTERACTION to TypeInteraction.SELECT_PHARMACY
                    )
                )
            }

            is DeliveryMethodsModel.Item.YandexDelivery -> {
                navigateToAddressFragment(item.deliveryType)
            }

            is DeliveryMethodsModel.Item.CourierDelivery -> {
                navigateToAddressFragment(item.deliveryType)
            }
        }
    }

    /**
     * Возвращает или устанавливает фио.
     */
    val fio = MutableLiveData(loginRepository.personalData.fio ?: "")

    /**
     * Возвращает или устанавливает номер.
     */
    val number = MutableLiveData(loginRepository.personalData.phone ?: "")

    /**
     * Возвращает значение номера.
     */
    val rawNumber = number.map {
        getPhoneRaw(it)
    }

    /**
     * Возвращает или устанавливает майл.
     */
    val mail = MutableLiveData(loginRepository.personalData.userMail?.mail ?: "")

    /**
     * Возвращает или устанавливает комментарий.
     */
    val comment = MutableLiveData("")

    /**
     * Возвращает флаг выбора самого себя в получатели.
     */
    val isRecipientSameBuyer = MutableLiveData(false)

    /**
     * Возвращает список получателей.
     */
    val recipients = MutableLiveData<List<RecipientModel>>(emptyList())

    /**
     * Устанавливает выбор самого себя в получатели заказа.
     */
    fun setRecipientSameBuyer(b: Boolean) {
        val r = RecipientModel(
            fio = fio.value!!,
            phone = number.value!!
        ).apply {
            onRemove = {
                removeRecipientOrder(this)
            }
        }
        if (b) {
            recipients.value = recipients.value!!.plus(r)
        } else {
            recipients.value = recipients.value!!.minus(r)
        }
    }

    /**
     * Добавляет получателя заказа.
     */
    fun addRecipientOrder(recipient: RecipientModel) {
        recipients.value = recipients.value!!.plus(
            recipient.apply {
                onRemove = {
                    removeRecipientOrder(this)
                }
            }
        )
    }

    /**
     * Удаляет получателя заказа.
     */
    fun removeRecipientOrder(recipient: RecipientModel) {
        recipients.value = recipients.value!!.minus(recipient)
    }

    /**
     * Возвращает или устанавливает промо код.
     */
    val promoCodeValue = MutableLiveData("")

    private val _isPromoCodeApplyLoading = MutableLiveData(false)

    /**
     * Возвращает флаг загрузки применения промокода.
     */
    val isPromoCodeApplyLoading: LiveData<Boolean> = _isPromoCodeApplyLoading

    /**
     * Применяет промокод.
     */
    fun applyPromoCode() {
        viewModelScope.launchIO {
            _isPromoCodeApplyLoading.postValue(true)
            delay(2000)
            _isPromoCodeApplyLoading.postValue(false)
        }
    }

    /**
     * Возвращает или устанавливает кол-во бонусов для списания.
     */
    val bonusesValue = MutableLiveData("")

    private val _isBonusesApplyLoading = MutableLiveData(false)

    /**
     * Возвращает флаг загрузки применения бонусов.
     */
    val isBonusesApplyLoading: LiveData<Boolean> = _isBonusesApplyLoading

    /**
     * Применяет бонусы.
     */
    fun applyBonuses() {
        viewModelScope.launchIO {
            _isBonusesApplyLoading.postValue(true)
            delay(2000)
            _isBonusesApplyLoading.postValue(false)
        }
    }

    /**
     * Возвращает флаг получения электронного чека.
     */
    val electronicReceipt = MutableLiveData(true)

    /**
     * Возвращает флаг доступности кнопки оформления заказа.
     */
    val isMakingOrderEnabled = MediatorLiveData<Boolean>().apply {
        fun check() {
            value = !isPromoCodeApplyLoading.value!! &&
                !isBonusesApplyLoading.value!! &&
                paymentsMethods.selectedItem.value != null &&
                (deliveryMethods.selectedItem.value?.deliveryType == DeliveryType.PICKUP || selectedDeliveryDate.value != null) &&
                recipients.value!!.isNotEmpty()
        }

        addSource(isPromoCodeApplyLoading) {
            check()
        }

        addSource(isBonusesApplyLoading) {
            check()
        }

        addSource(paymentsMethods.selectedItem) {
            check()
        }

        addSource(deliveryMethods.selectedItem) {
            check()
        }

        addSource(selectedDeliveryDate) {
            check()
        }

        addSource(recipients) {
            check()
        }
    }

    /**
     * Возвращает флаг заполнения обязательных полей.
     */
    val isFieldsFilled = MediatorLiveData<Boolean>().apply {
        fun checkFieldFilled() {
            value = selectedDeliveryDate.value != null && recipients.value!!.isNotEmpty()
        }
    }

    init {
        /*viewModelScope.launchIO {
            requestHandler.handleApiRequest(
                onRequest = {
                    loginRepository.getPersonalData()
                },
                onSuccess = {
                    personalData.postValue(it)
                },
                isLoading = _isPersonalDataLoading
            )
        }*/
    }
}
