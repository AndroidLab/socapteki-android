package ru.apteka.home.presentation.my_subscriptions

import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import ru.apteka.components.ui.BaseFragment
import ru.apteka.home.R
import ru.apteka.home.databinding.MySubscriptionsFragmentBinding
import ru.apteka.components.R as ComponentsR


/**
 * Представляет фрагмент "Мои подписки".
 */
@AndroidEntryPoint
class MySubscriptionsFragment :
    BaseFragment<MySubscriptionsViewModel, MySubscriptionsFragmentBinding>() {
    override val viewModel: MySubscriptionsViewModel by viewModels()
    override val layoutId: Int = R.layout.my_subscriptions_fragment


    override fun onViewBindingInflated(binding: MySubscriptionsFragmentBinding) {
        binding.viewModel = viewModel

    }

    override fun onResume() {
        super.onResume()
        binding.mySubscriptionsToolbar.apply {
            toolbar.setNavigationIcon(ComponentsR.drawable.ic_navigation_back)
            toolbar.title = getString(R.string.my_subscriptions_title)
            toolbar.setNavigationOnClickListener {
                viewModel.navigationManager.currentBottomNavControllerLiveData.value!!.popBackStack()
            }
        }
    }


}