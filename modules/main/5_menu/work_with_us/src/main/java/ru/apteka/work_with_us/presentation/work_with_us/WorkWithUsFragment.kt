package ru.apteka.work_with_us.presentation.work_with_us

import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import ru.apteka.components.data.services.navigation_manager.NavigationManager
import ru.apteka.components.data.utils.navigateWithAnim
import ru.apteka.components.data.utils.setSoftInputModeResize
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

        fun navigateToQuestionnaire() {
            navigationManager.currentBottomNavControllerLiveData.value!!.navigateWithAnim(
                WorkWithUsFragmentDirections.toWorkWithUsQuestionnaireFragment()
            )
        }

        binding.mbWorkWithUsRespond.setOnClickListener {
            navigateToQuestionnaire()
        }

        binding.workWithUsQuestionnaire1.setOnClickListener {
            navigateToQuestionnaire()
        }

        binding.workWithUsQuestionnaire2.setOnClickListener {
            navigateToQuestionnaire()
        }

        binding.workWithUsQuestionnaire3.setOnClickListener {
            navigateToQuestionnaire()
        }

        binding.workWithUsQuestionnaire4.setOnClickListener {
            navigateToQuestionnaire()
        }

        binding.workWithUsQuestionnaire5.setOnClickListener {
            navigateToQuestionnaire()
        }

        binding.jobOpeningsShowMore.setOnClickListener {

        }

        binding.workWithUsSort.setOnClickListener {

        }

    }


    override fun onResume() {
        super.onResume()
        binding.workWithUsToolbar.apply {
            tvToolbarTitle.text = getString(R.string.work_with_us_title)
        }
    }
}