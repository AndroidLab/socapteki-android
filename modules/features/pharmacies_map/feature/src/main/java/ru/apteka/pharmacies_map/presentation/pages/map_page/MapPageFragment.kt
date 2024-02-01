package ru.apteka.pharmacies_map.presentation.pages.map_page

import androidx.fragment.app.activityViewModels
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.MapObjectTapListener
import com.yandex.mapkit.mapview.MapView
import com.yandex.runtime.ui_view.ViewProvider
import dagger.hilt.android.AndroidEntryPoint
import ru.apteka.components.data.models.PharmacyCardModel
import ru.apteka.components.data.services.message_notice_service.models.BottomSheetModel
import ru.apteka.components.data.services.message_notice_service.models.CommonBottomSheetModel
import ru.apteka.components.data.services.message_notice_service.showBottomSheet
import ru.apteka.components.data.utils.setExtraMargin
import ru.apteka.components.ui.BaseFragment
import ru.apteka.pharmacies_map.R
import ru.apteka.pharmacies_map.databinding.MapPageFragmentBinding
import ru.apteka.pharmacies_map.databinding.PharmacyItemHolderBinding
import ru.apteka.pharmacies_map.databinding.PharmacyMapItem1Binding
import ru.apteka.pharmacies_map.databinding.PharmacyMapItem2Binding
import ru.apteka.pharmacies_map.presentation.PharmaciesMapViewModel
import ru.apteka.pharmacies_map_api.api.TypeInteraction

/**
 * Представляет фрагмент "Страница с картой".
 */
@AndroidEntryPoint
class MapPageFragment : BaseFragment<PharmaciesMapViewModel, MapPageFragmentBinding>() {

    override val viewModel: PharmaciesMapViewModel by activityViewModels()
    override val layoutId: Int = R.layout.map_page_fragment

    private val mapView: MapView by lazy {
        binding.mapview
    }

    private val map by lazy {
        mapView.mapWindow.map
    }

    override fun onViewBindingInflated(binding: MapPageFragmentBinding) {
        MapKitFactory.initialize(requireContext())

        binding.viewModel = viewModel

        map.move(POSITION)

        viewModel.pharmacies.observe(viewLifecycleOwner) { pharmacies ->
            pharmacies.forEach { pharmacyCard ->
                val viewProvider =
                    if (viewModel.typeInteraction.value == TypeInteraction.NAVIGATION) {
                        ViewProvider(PharmacyMapItem1Binding.inflate(layoutInflater).root)
                    } else {
                        ViewProvider(
                            PharmacyMapItem2Binding.inflate(layoutInflater).apply {
                                tvPharmacyMapItem.text = "1 из 2"
                            }.root
                        )
                    }
                map.mapObjects.addPlacemark(
                    Point(
                        pharmacyCard.pharmacy.coordinates.first,
                        pharmacyCard.pharmacy.coordinates.second
                    ),
                    viewProvider
                ).apply {
                    addTapListener(placemarkTapListener)
                    userData = pharmacyCard
                }
            }
        }
    }

    private val placemarkTapListener = MapObjectTapListener { mapObject, point ->
        val pharmacyCard = mapObject.userData as PharmacyCardModel
        //viewModel.searchQuery.value = pharmacy.name
        moveMap(
            pharmacyCard.pharmacy.coordinates.first,
            pharmacyCard.pharmacy.coordinates.second
        )

        showBottomSheet(
            commonBottomSheet = CommonBottomSheetModel(
                fragmentManager = parentFragmentManager,
                bottomSheetModel = BottomSheetModel(
                    layoutId = R.layout.pharmacy_item_holder,
                    onLayoutInflate = { binding, dialog, behavior ->
                        binding as PharmacyItemHolderBinding
                        binding.model = pharmacyCard
                        binding.typeInteraction = viewModel.typeInteraction.value
                        binding.root.setExtraMargin(16)
                        binding.pharmacyItemPickupHere.setOnClickListener {
                            dialog.dismiss()
                            viewModel.savePharmacy(pharmacyCard.pharmacy)
                        }
                    }
                )
            )
        )
        true
    }

    private fun moveMap(latitude: Double, longitude: Double) {
        keyBoardClose()
        map.move(
            CameraPosition(
                Point(latitude, longitude),
                15.0f,
                0f,
                0f
            ),
            Animation(Animation.Type.LINEAR, 1f),
            null
        )
    }

    override fun onStart() {
        super.onStart()
        MapKitFactory.getInstance().onStart()
        mapView.onStart()
    }

    override fun onStop() {
        super.onStop()
        mapView.onStop()
        MapKitFactory.getInstance().onStop()
    }

    companion object {
        @JvmStatic
        private val POSITION = CameraPosition(Point(55.753216, 37.619299), 12.0f, 0f, 0f)
    }
}
