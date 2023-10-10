package ru.apteka.main.presentation.main

import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import ru.apteka.common.ui.BaseFragment
import ru.apteka.main.R
import ru.apteka.main.data.setupWithNavController
import ru.apteka.main.databinding.MainFragmentBinding
import ru.apteka.screen_1.R as Screen1R
import ru.apteka.screen_2.R as Screen2R
import ru.apteka.screen_3.R as Screen3R
import ru.apteka.screen_4.R as Screen4R
import ru.apteka.screen_5.R as Screen5R


/**
 * Представляет фрагмент "Основной экран".
 */
@AndroidEntryPoint
class MainFragment : BaseFragment<MainViewModel, MainFragmentBinding>() {
    override val viewModel: MainViewModel by viewModels()
    override val layoutId: Int = R.layout.main_fragment

    override fun onViewBindingInflated(binding: MainFragmentBinding) {
        viewModel.navigationManager.isBottomNavigationBarNeedUpdateSingleEvent.observe(
            viewLifecycleOwner
        ) {
            setupBottomNavigationBar()
        }
        //TODO Для сэмпла
        viewModel.basketService.products.observe(viewLifecycleOwner) { products ->
            products.size.toUInt().also { productCount ->
                binding.bottomNav.nbTab5.setNumber(
                    if (productCount > 0u) {
                        productCount
                    } else {
                        null
                    }
                )
            }
        }


    }

    private fun setupBottomNavigationBar() {
        viewModel.navigationManager.currentBottomNavControllerLiveData =
            binding.bottomNav.component.setupWithNavController(
                navGraphIds = listOf(
                    Screen1R.navigation.screen_1_graph,
                    Screen2R.navigation.screen_2_graph,
                    Screen3R.navigation.screen_3_graph,
                    Screen4R.navigation.screen_4_graph,
                    Screen5R.navigation.screen_5_graph
                ),
                fragmentManager = childFragmentManager,
                containerId = R.id.nav_host_container,
                intent = requireActivity().intent
            ).apply {
                observe(viewLifecycleOwner) { navController ->
                    //(requireActivity() as AppCompatActivity).setupActionBarWithNavController(navController)
                }
            }
    }
}