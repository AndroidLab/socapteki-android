package ru.apteka.home.presentation.profile

import dagger.hilt.android.AndroidEntryPoint
import ru.apteka.components.data.services.account.AccountRemoveService
import ru.apteka.components.data.services.navigation_manager.NavigationManager
import ru.apteka.components.data.utils.navigateWithAnim
import ru.apteka.components.ui.BaseFragment
import ru.apteka.home.R
import ru.apteka.home.databinding.UserProfileFragmentBinding
import javax.inject.Inject
import ru.apteka.main_common.R as MainCommonR
import ru.apteka.personal_data_api.R as UserProfileApiR


/**
 * Представляет фрагмент "Профиль пользователя".
 */
@AndroidEntryPoint
class UserProfileFragment : BaseFragment<Nothing, UserProfileFragmentBinding>() {
    override val layoutId: Int = R.layout.user_profile_fragment

    @Inject
    lateinit var navigationManager: NavigationManager

    @Inject
    lateinit var accountRemoveService: AccountRemoveService

    override fun onViewBindingInflated(binding: UserProfileFragmentBinding) {
        accountRemoveService.isAccountRemove.observe(viewLifecycleOwner) {
            navigationManager.currentBottomNavControllerLiveData.value!!.popBackStack()
        }

        binding.llProfilePersonalData.setOnClickListener {
            navigationManager.generalNavController.navigateWithAnim(UserProfileApiR.id.personal_data_graph)
        }

        binding.userProfileFavorite.profileCard.setOnClickListener {
            navigationManager.bottomNavBar.selectedItemId = MainCommonR.id.favorites_graph
        }

        binding.userProfileOrders.profileCard.setOnClickListener {
            navigationManager.bottomNavBar.selectedItemId = MainCommonR.id.orders_graph
        }

        binding.userProfileApteki.profileCard.setOnClickListener {
            navigationManager.currentBottomNavControllerLiveData.value!!.navigateWithAnim(
                UserProfileFragmentDirections.toAptekiFragment()
            )
        }


        binding.userProfileBonusProgram.llProfileMenuItem.setOnClickListener {
            navigationManager.currentBottomNavControllerLiveData.value!!.navigateWithAnim(
                UserProfileFragmentDirections.toBonusProgramFragment()
            )
        }

        binding.userProfileReferralProgram.llProfileMenuItem.setOnClickListener {

        }

        binding.userProfileCommentsReviews.llProfileMenuItem.setOnClickListener {
            navigationManager.currentBottomNavControllerLiveData.value!!.navigateWithAnim(
                UserProfileFragmentDirections.toCommentsReviewsFragment()
            )
        }

        binding.userProfileMySubscriptions.llProfileMenuItem.setOnClickListener {
            navigationManager.currentBottomNavControllerLiveData.value!!.navigateWithAnim(
                UserProfileFragmentDirections.toSubscriptionsFragment()
            )
        }
    }

    override fun onResume() {
        super.onResume()
        binding.profileToolbar.apply {
            toolbar.setNavigationIcon(ru.apteka.components.R.drawable.ic_navigation_back)
            toolbar.title = getString(R.string.profile_title)
            toolbar.setNavigationOnClickListener {
                navigationManager.currentBottomNavControllerLiveData.value!!.popBackStack()
            }
        }
    }

}