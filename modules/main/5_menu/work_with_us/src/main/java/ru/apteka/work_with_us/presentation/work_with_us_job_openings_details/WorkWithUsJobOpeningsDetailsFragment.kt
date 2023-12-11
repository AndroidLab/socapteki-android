package ru.apteka.work_with_us.presentation.work_with_us_job_openings_details

import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import ru.apteka.components.data.utils.navigateWithAnim
import ru.apteka.components.ui.BaseFragment
import ru.apteka.work_with_us.R
import ru.apteka.work_with_us.databinding.WorkWithUsJobOpeningsDetailsFragmentBinding
import ru.apteka.work_with_us.presentation.work_with_us.WorkWithUsFragmentDirections


/**
 * Представляет фрагмент "Работа у нас, Вакансии".
 */
@AndroidEntryPoint
class WorkWithUsJobOpeningsDetailsFragment :
    BaseFragment<WorkWithUsJobOpeningsDetailsViewModel, WorkWithUsJobOpeningsDetailsFragmentBinding>() {

    override val viewModel: WorkWithUsJobOpeningsDetailsViewModel by viewModels()
    override val layoutId: Int = R.layout.work_with_us_job_openings_details_fragment

    override fun onViewBindingInflated(binding: WorkWithUsJobOpeningsDetailsFragmentBinding) {
        binding.viewModel = viewModel

        binding.mbWorkWithUsJobOpeningsDetails.setOnClickListener {
            viewModel.navigationManager.currentBottomNavControllerLiveData.value!!.navigateWithAnim(
                WorkWithUsFragmentDirections.toWorkWithUsQuestionnaireFragment()
            )
        }
    }

    override fun onResume() {
        super.onResume()
        binding.workWithUsJobOpeningsDetailsToolbar.apply {
            toolbar.setNavigationIcon(ru.apteka.components.R.drawable.ic_navigation_back)
            tvToolbarTitle.text = getString(R.string.work_with_us_job_openings_details_title)
            toolbar.setNavigationOnClickListener {
                viewModel.navigationManager.currentBottomNavControllerLiveData.value!!.popBackStack()
            }
        }
    }

}