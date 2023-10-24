package ru.apteka.home.presentation.apteki

import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import ru.apteka.components.data.services.account.AccountRemoveService
import ru.apteka.components.data.services.navigation_manager.NavigationManager
import ru.apteka.components.data.utils.dp
import ru.apteka.components.ui.BaseFragment
import ru.apteka.components.ui.delegate_adapter.CompositeDelegateAdapter
import ru.apteka.components.ui.delegate_adapter.SkeletonAdapter
import ru.apteka.home.R
import ru.apteka.home.data.models.AptekaCardModel
import ru.apteka.home.data.models.AptekaModel
import ru.apteka.home.databinding.AptekiFragmentBinding
import ru.apteka.home.databinding.MySubscriptionsFragmentBinding
import ru.apteka.home.presentation.home.adapters.AdvertCardViewAdapter
import javax.inject.Inject
import ru.apteka.components.R as ComponentsR


/**
 * Представляет фрагмент "Аптеки".
 */
@AndroidEntryPoint
class AptekiFragment : BaseFragment<AptekiViewModel, AptekiFragmentBinding>() {
    override val viewModel: AptekiViewModel by viewModels()
    override val layoutId: Int = R.layout.apteki_fragment

    private val aptekiAdapter by lazy {
        CompositeDelegateAdapter(
            AptekiAdapter(::onAptekaItemClick)
        )
    }

    override fun onViewBindingInflated(binding: AptekiFragmentBinding) {
        binding.viewModel = viewModel

        binding.rvApteki.adapter = aptekiAdapter

        viewModel.apteki.observe(viewLifecycleOwner) {
            aptekiAdapter.swapData(it)
        }
    }

    override fun onResume() {
        super.onResume()
        binding.aptekiToolbar.apply {
            toolbar.setNavigationIcon(ComponentsR.drawable.ic_navigation_back)
            toolbar.title = getString(R.string.apteki_title)
            toolbar.setNavigationOnClickListener {
                viewModel.navigationManager.currentBottomNavControllerLiveData.value!!.popBackStack()
            }
        }
    }


    private fun onAptekaItemClick(item: AptekaCardModel) {

    }


}