package ru.apteka.profile.presentation.profile

import dagger.hilt.android.AndroidEntryPoint
import ru.apteka.components.data.services.account.AccountRemoveService
import ru.apteka.components.data.services.account.AccountsPreferences
import ru.apteka.components.data.services.navigation_manager.NavigationManager
import ru.apteka.components.data.utils.navigateWithAnim
import ru.apteka.components.ui.BaseFragment
import ru.apteka.profile.R
import ru.apteka.profile.databinding.ProfileFragmentBinding
import javax.inject.Inject
import ru.apteka.personal_data_api.R as UserProfileApiR


/**
 * Представляет фрагмент "Профиль пользователя".
 */
@AndroidEntryPoint
class ProfileFragment : BaseFragment<Nothing, ProfileFragmentBinding>() {
    override val layoutId: Int = R.layout.profile_fragment

    @Inject
    lateinit var navigationManager: NavigationManager

    @Inject
    lateinit var accountRemoveService: AccountRemoveService


    override fun onViewBindingInflated(binding: ProfileFragmentBinding) {
        accountRemoveService.isAccountRemove.observe(viewLifecycleOwner) {
            navigationManager.currentBottomNavControllerLiveData.value!!.popBackStack()
        }

        binding.llProfilePersonalData.setOnClickListener {
            navigationManager.currentBottomNavControllerLiveData.value!!.navigateWithAnim(
                ProfileFragmentDirections.toPersonalDataFragment()
            )
        }

        binding.userProfileFavorite.profileCard.setOnClickListener {
            navigationManager.generalNavController.navigateWithAnim(
                ru.apteka.favorites_api.R.id.favorites_graph,
            )
        }

        binding.userProfileOrders.profileCard.setOnClickListener {
            navigationManager.generalNavController.navigateWithAnim(
                ru.apteka.orders_api.R.id.orders_graph,
            )
        }

        binding.userProfilePharmacy.profileCard.setOnClickListener {
            navigationManager.currentBottomNavControllerLiveData.value!!.navigateWithAnim(
                ProfileFragmentDirections.toPharmaciesFragment()
            )
        }

        binding.userProfileCommentsReviews.llProfileMenuItem.setOnClickListener {
            navigationManager.currentBottomNavControllerLiveData.value!!.navigateWithAnim(
                ProfileFragmentDirections.toCommentsReviewsFragment()
            )
        }

        binding.userProfileSettingNotifications.llProfileMenuItem.setOnClickListener {
            navigationManager.currentBottomNavControllerLiveData.value!!.navigateWithAnim(
                ProfileFragmentDirections.toSettingNotificationsFragment()
            )
        }

        binding.userProfileReferralProgram.llProfileMenuItem.setOnClickListener {

        }

    }

    override fun onResume() {
        super.onResume()
        navigationManager.onBottomAppBarShowed(true)
        binding.profileToolbar.apply {
            tvToolbarTitle.text = getString(R.string.profile_title)
        }
    }

}