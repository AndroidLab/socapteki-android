package ru.apteka.home.presentation.home

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.apteka.components.data.services.BasketService
import java.util.UUID
import javax.inject.Inject



@HiltViewModel
class HomeViewModel @Inject constructor(
    private val basketService: BasketService
): ViewModel() {


    fun plusProduct() {
        basketService.addProduct(UUID.randomUUID())
    }

    fun minusProduct() {
        basketService.products.value!!.firstOrNull()?.let {
            basketService.removeProduct(it)
        }

    }

}