package ru.apteka.pharmacies_map.presentation

import android.widget.Toast
import androidx.fragment.app.viewModels
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.MapObjectTapListener
import com.yandex.mapkit.map.PlacemarkCreatedCallback
import com.yandex.mapkit.map.PlacemarkMapObject
import com.yandex.mapkit.mapview.MapView
import com.yandex.runtime.image.ImageProvider
import dagger.hilt.android.AndroidEntryPoint
import ru.apteka.components.ui.BaseFragment
import ru.apteka.pharmacies_map.R
import ru.apteka.pharmacies_map.databinding.PharmaciesMapFragmentBinding


/**
 * Представляет фрагмент "Аптеки на карте".
 */
@AndroidEntryPoint
class PharmaciesMapFragment : BaseFragment<PharmaciesMapViewModel, PharmaciesMapFragmentBinding>() {

    override val viewModel: PharmaciesMapViewModel by viewModels()
    override val layoutId: Int = R.layout.pharmacies_map_fragment

    private lateinit var mapView: MapView

    override fun onViewBindingInflated(binding: PharmaciesMapFragmentBinding) {
        MapKitFactory.initialize(requireContext())
        mapView = binding.mapview

        val map = mapView.mapWindow.map
        map.move(POSITION)
        val imageProvider = ImageProvider.fromResource(requireContext(), R.drawable.ic_apteca)
        POINTS.forEach { point ->
            val placemarkObject = map.mapObjects.addPlacemark(point, imageProvider)
            placemarkObject.addTapListener(placemarkTapListener)
        }

    }

    private val placemarkTapListener = MapObjectTapListener { _, point ->
        Toast.makeText(requireContext(), "Адресс аптеки (${point.longitude}, ${point.latitude})", Toast.LENGTH_LONG).show()
        true
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
        private val POINTS = listOf(
            Point(55.751280, 37.629720),
            Point(55.778055, 37.608712),
            Point(55.770504, 37.639697),
            Point(55.758592, 37.625496),
            Point(55.756994, 37.607163),
            Point(55.750019, 37.640730),
            Point(55.743189, 37.618782)
        )
        private val POSITION = CameraPosition(Point(55.753216, 37.619299), 12.0f, 0f, 0f)
    }
}