package ru.apteka.work_with_us.presentation.work_with_us_job_openings

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import ru.apteka.components.data.utils.launchIO
import ru.apteka.components.data.utils.navigateWithAnim
import ru.apteka.components.data.utils.recyclerAutoScroll
import ru.apteka.components.ui.BaseFragment
import ru.apteka.components.ui.delegate_adapter.CompositeDelegateAdapter
import ru.apteka.work_with_us.R
import ru.apteka.work_with_us.databinding.WorkWithUsJobOpeningsFragmentBinding
import ru.apteka.work_with_us.presentation.work_with_us.WorkWithUsFragmentDirections


/**
 * Представляет фрагмент "Работа у нас, Вакансии".
 */
@AndroidEntryPoint
class WorkWithUsJobOpeningsFragment :
    BaseFragment<WorkWithUsJobOpeningsViewModel, WorkWithUsJobOpeningsFragmentBinding>() {

    override val viewModel: WorkWithUsJobOpeningsViewModel by viewModels()
    override val layoutId: Int = R.layout.work_with_us_job_openings_fragment

    private val eventsAdapter by lazy {
        CompositeDelegateAdapter(
            EventsCardAdapter()
        )
    }

    private val employeeReviewsAdapter by lazy {
        CompositeDelegateAdapter(
            EmployeeReviewsAdapter()
        )
    }

    override fun onViewBindingInflated(binding: WorkWithUsJobOpeningsFragmentBinding) {
        binding.viewModel = viewModel

        binding.rvWorkWithUsJobOpeningsEvents.adapter = eventsAdapter
        binding.workWithUsJobOpeningsEmployeeReviews.rv.adapter = employeeReviewsAdapter

        viewModel.events.observe(viewLifecycleOwner) {
            eventsAdapter.swapData(it)
            if (it.isNotEmpty()) {
                lifecycleScope.launchIO { recyclerAutoScroll(binding.rvWorkWithUsJobOpeningsEvents) }
            }
        }

        viewModel.employeeReviews.observe(viewLifecycleOwner) {
            employeeReviewsAdapter.swapData(it)
        }


        binding.workWithUsJobOpeningsAttachResume.setOnClickListener {

        }

        binding.workWithUsJobOpeningsCareer.setOnClickListener {
            viewModel.navigationManager.generalNavController.navigateWithAnim(
                WorkWithUsFragmentDirections.toWorkWithUsQuestionnaireFragment()
            )
        }

        binding.workWithUsJobOpeningsGuarantees.setOnClickListener {
            viewModel.navigationManager.generalNavController.navigateWithAnim(
                WorkWithUsFragmentDirections.toWorkWithUsQuestionnaireFragment()
            )
        }

        binding.workWithUsJobOpeningsRespond.setOnClickListener {

        }
    }

    override fun onResume() {
        super.onResume()
        binding.workWithUsJobOpeningsToolbar.apply {
            toolbar.setNavigationIcon(ru.apteka.components.R.drawable.ic_navigation_back)
            tvToolbarTitle.text = getString(R.string.work_with_us_job_openings_title)
            toolbar.setNavigationOnClickListener {
                viewModel.navigationManager.generalNavController.popBackStack()
            }
        }
    }

}