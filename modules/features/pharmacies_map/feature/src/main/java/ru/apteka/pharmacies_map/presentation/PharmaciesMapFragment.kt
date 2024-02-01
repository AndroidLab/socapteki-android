package ru.apteka.pharmacies_map.presentation

import androidx.core.content.ContextCompat
import androidx.core.widget.doAfterTextChanged
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.google.android.material.tabs.TabLayoutMediator
import com.yandex.mapkit.MapKitFactory
import dagger.hilt.android.AndroidEntryPoint
import ru.apteka.components.data.utils.equalsWithDeviation
import ru.apteka.components.data.utils.navigateWithAnim
import ru.apteka.components.data.utils.playAnimation
import ru.apteka.components.databinding.SearchToolbarViewBinding
import ru.apteka.components.ui.BaseFragment
import ru.apteka.components.ui.adapters.PagerAdapter
import ru.apteka.pharmacies_map.R
import ru.apteka.pharmacies_map.databinding.PharmaciesMapFragmentBinding
import ru.apteka.pharmacies_map.presentation.pages.list_page.ListPageFragment
import ru.apteka.pharmacies_map.presentation.pages.map_page.MapPageFragment

/**
 * Представляет фрагмент "Аптеки на карте".
 */
@AndroidEntryPoint
class PharmaciesMapFragment : BaseFragment<PharmaciesMapViewModel, PharmaciesMapFragmentBinding>() {

    override val viewModel: PharmaciesMapViewModel by activityViewModels()
    override val layoutId: Int = R.layout.pharmacies_map_fragment

    private val _args: PharmaciesMapFragmentArgs by navArgs()

    override fun onViewBindingInflated(binding: PharmaciesMapFragmentBinding) {
        MapKitFactory.initialize(requireContext())
        viewModel.typeInteraction.value = _args.typeInteraction
        binding.viewModel = viewModel

        binding.pharmaciesMapCityChange.setOnClickListener {
            viewModel.navigationManager.generalNavController.navigateWithAnim(
                ru.apteka.choosing_city_api.R.id.choosing_city_graph,
            )
        }

        binding.pharmaciesMapPager.isUserInputEnabled = false
        binding.pharmaciesMapPager.adapter = PagerAdapter(
            requireActivity(),
            arrayListOf(
                MapPageFragment() as Fragment,
                ListPageFragment() as Fragment,
            )
        )
        TabLayoutMediator(binding.pharmaciesMapTabLayout, binding.pharmaciesMapPager) { tab, pos ->
            tab.text = listOf(
                getString(R.string.pharmacies_map_page),
                getString(R.string.pharmacies_list_page),
            )[pos]
        }.attach()
    }

    override fun onResume() {
        super.onResume()
        viewModel.navigationManager.onBottomAppBarShowed(false)
        binding.choosingCityToolbar.apply {
            toolbar.setNavigationIcon(ru.apteka.components.R.drawable.ic_navigation_back)
            toolbar.setNavigationOnClickListener {
                viewModel.navigationManager.generalNavController.popBackStack()
            }
            toolbarCustomViewContainer.removeAllViews()
            toolbarCustomViewContainer.apply {
                //layoutParams = Toolbar.LayoutParams(Toolbar.LayoutParams.MATCH_PARENT, Toolbar.LayoutParams.MATCH_PARENT)
                addView(
                    DataBindingUtil.inflate<SearchToolbarViewBinding>(
                        layoutInflater,
                        ru.apteka.components.R.layout.search_toolbar_view,
                        null,
                        false
                    ).apply {
                        lifecycleOwner = viewLifecycleOwner
                        hint = getString(ru.apteka.components.R.string.address)
                        isMicIconVisible = false
                        isBarCodeIconVisible = false

                        searchToolbarSearch.setOnClickListener {
                            etToolBarSearch.setText("")
                        }
                        viewModel.addressQueryColor.observe(viewLifecycleOwner) {
                            etToolBarSearch.setTextColor(
                                ContextCompat.getColor(
                                    requireContext(),
                                    it
                                )
                            )
                        }
                        val deviation = 0.01f
                        val startProgress = 0.1f
                        val middleProgress = 0.25f
                        val endProgress = 0.48f
                        etToolBarSearch.doAfterTextChanged {
                            viewModel.addressQuery.value = it.toString()
                            val progress = searchToolbarSearch.progress
                            if (it.isNullOrEmpty()) {
                                if (progress.equalsWithDeviation(middleProgress, deviation)) {
                                    searchToolbarSearch.playAnimation(0.4f, endProgress)
                                }
                            } else {
                                if (progress.equalsWithDeviation(
                                        startProgress,
                                        deviation
                                    ) || progress.equalsWithDeviation(endProgress, deviation)
                                ) {
                                    searchToolbarSearch.playAnimation(
                                        startProgress,
                                        middleProgress
                                    )
                                }
                            }
                        }
                    }.root
                )
            }
        }
    }
}
