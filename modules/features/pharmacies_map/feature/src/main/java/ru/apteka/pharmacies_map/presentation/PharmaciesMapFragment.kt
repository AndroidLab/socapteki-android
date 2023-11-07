package ru.apteka.pharmacies_map.presentation

import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.viewModels
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.MapObjectTapListener
import com.yandex.mapkit.mapview.MapView
import com.yandex.runtime.image.ImageProvider
import dagger.hilt.android.AndroidEntryPoint
import ru.apteka.components.data.utils.dp
import ru.apteka.components.data.utils.equalsWithDeviation
import ru.apteka.components.data.utils.playAnimation
import ru.apteka.components.ui.BaseFragment
import ru.apteka.components.ui.delegate_adapter.CompositeDelegateAdapter
import ru.apteka.pharmacies_map.R
import ru.apteka.pharmacies_map.data.model.PharmacyModel
import ru.apteka.pharmacies_map.databinding.PharmaciesMapFragmentBinding


/**
 * Представляет фрагмент "Аптеки на карте".
 */
@AndroidEntryPoint
class PharmaciesMapFragment : BaseFragment<PharmaciesMapViewModel, PharmaciesMapFragmentBinding>() {

    override val viewModel: PharmaciesMapViewModel by viewModels()
    override val layoutId: Int = R.layout.pharmacies_map_fragment

    private val mapView: MapView by lazy {
        binding.mapview
    }

    private val map by lazy {
        mapView.mapWindow.map
    }

    private val pharmacyAdapter by lazy {
        CompositeDelegateAdapter(
            PharmacyAdapter(::onPharmacyClick)
        )
    }

    override fun onViewBindingInflated(binding: PharmaciesMapFragmentBinding) {
        MapKitFactory.initialize(requireContext())

        binding.viewModel = viewModel
        map.move(POSITION)

        binding.pharmaciesMapExpand.setOnClickListener {
            keyBoardClose()
            val appBarHeight = binding.pharmaciesMapAppbar.layoutParams.height
            if (appBarHeight == CoordinatorLayout.LayoutParams.MATCH_PARENT) {
                expandMap()
            } else {
                binding.lavPharmaciesMapExpand.playAnimation(0f, .5f)
                binding.pharmaciesMapAppbar.layoutParams.height =
                    CoordinatorLayout.LayoutParams.MATCH_PARENT
                binding.pharmaciesMapAppbar.setExpanded(true)
            }
        }


        binding.etPharmaciesMap.setOnClickListener {
            binding.pharmaciesMapAppbar.setExpanded(false)
        }
        binding.tilPharmaciesMap.addEditTextFocusChangeListener { view, b ->
            if (b) {
                binding.pharmaciesMapAppbar.setExpanded(false)
            }
        }

        binding.pharmaciesMapClear.setOnClickListener {
            viewModel.searchQuery.value = ""
        }

        val imageProvider = ImageProvider.fromResource(requireContext(), R.drawable.ic_apteca)
        binding.rvPharmaciesMap.adapter = pharmacyAdapter

        viewModel.pharmaciesFiltered.observe(viewLifecycleOwner) { pharmacies ->
            pharmacyAdapter.swapData(pharmacies)
            pharmacies.forEach { pharmacy ->
                map.mapObjects.addPlacemark(
                    Point(
                        pharmacy.coordinates.first,
                        pharmacy.coordinates.second
                    ), imageProvider
                ).apply {
                    addTapListener(placemarkTapListener)
                    userData = pharmacy
                }
            }
        }

        val deviation = 0.01f
        val startProgress = 0.1f
        val middleProgress = 0.25f
        val endProgress = 0.48f
        viewModel.searchQuery.observe(viewLifecycleOwner) {
            val progress = binding.pharmaciesMapClear.progress
            if (it.isNotEmpty()) {
                if (progress.equalsWithDeviation(
                        startProgress,
                        deviation
                    ) || progress.equalsWithDeviation(endProgress, deviation)
                ) {
                    binding.pharmaciesMapClear.playAnimation(
                        startProgress,
                        middleProgress
                    )
                }
            } else {
                if (progress.equalsWithDeviation(middleProgress, deviation)) {
                    binding.pharmaciesMapClear.playAnimation(0.4f, endProgress)
                }
            }
        }
    }

    private fun onPharmacyClick(pharmacy: PharmacyModel) {
        moveMap(
            pharmacy.coordinates.first,
            pharmacy.coordinates.second
        )
    }

    private val placemarkTapListener = MapObjectTapListener { mapObject, point ->
        val pharmacy = mapObject.userData as PharmacyModel
        viewModel.searchQuery.value = pharmacy.title
        moveMap(
            pharmacy.coordinates.first,
            pharmacy.coordinates.second
        )
        true
    }

    private fun moveMap(latitude: Double, longitude: Double) {
        keyBoardClose()
        expandMap()
        binding.pharmaciesMapAppbar.setExpanded(true)
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

    private fun expandMap() {
        binding.lavPharmaciesMapExpand.playAnimation(.5f, 1f)
        binding.pharmaciesMapAppbar.layoutParams.height = EXPAND_MAP_HEIGHT
        binding.pharmaciesMapAppbar.setExpanded(true)
    }

    override fun onStart() {
        super.onStart()
        MapKitFactory.getInstance().onStart()
        mapView.onStart()
    }

    override fun onResume() {
        super.onResume()
        binding.ivPharmaciesMapBack.setOnClickListener {
            viewModel.navigationManager.generalNavController.popBackStack()
        }
    }

    override fun onStop() {
        super.onStop()
        mapView.onStop()
        MapKitFactory.getInstance().onStop()
    }

    companion object {
        @JvmStatic
        private val EXPAND_MAP_HEIGHT = 400.dp
        private val POSITION = CameraPosition(Point(55.753216, 37.619299), 12.0f, 0f, 0f)
    }
}
