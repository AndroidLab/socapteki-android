package ru.apteka.work_with_us.presentation.work_with_us

import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import ru.apteka.components.data.services.navigation_manager.NavigationManager
import ru.apteka.components.data.utils.navigateWithAnim
import ru.apteka.components.ui.BaseFragment
import ru.apteka.work_with_us.R
import ru.apteka.work_with_us.databinding.WorkWithUsFragmentBinding
import javax.inject.Inject


/**
 * Представляет фрагмент "Работа у нас".
 */
@AndroidEntryPoint
class WorkWithUsFragment : BaseFragment<WorkWithUsViewModel, WorkWithUsFragmentBinding>() {

    override val viewModel: WorkWithUsViewModel by viewModels()
    override val layoutId: Int = R.layout.work_with_us_fragment

    @Inject
    lateinit var navigationManager: NavigationManager

    override fun onViewBindingInflated(binding: WorkWithUsFragmentBinding) {
        binding.viewModel = viewModel

        binding.workWithUsJobOpenings.setOnClickListener {
            navigationManager.currentBottomNavControllerLiveData.value!!.navigateWithAnim(
                WorkWithUsFragmentDirections.toWorkWithUsJobOpeningsFragment()
            )
        }

        binding.workWithUsQuestionnaire.setOnClickListener {
            navigationManager.currentBottomNavControllerLiveData.value!!.navigateWithAnim(
                WorkWithUsFragmentDirections.toWorkWithUsQuestionnaireFragment()
            )
        }
    }

}