package ru.apteka.work_with_us.presentation.work_with_us_job_openings


import ru.apteka.components.ui.delegate_adapter.ViewBindingDelegateAdapter
import ru.apteka.work_with_us.data.model.EmployeeReviewModel
import ru.apteka.work_with_us.databinding.EmployeeReviewHolderBinding


/**
 * Представляет адаптер для карточки отзыва работника.
 */
class EmployeeReviewsAdapter :
    ViewBindingDelegateAdapter<EmployeeReviewModel, EmployeeReviewHolderBinding>(EmployeeReviewHolderBinding::inflate) {

    override fun EmployeeReviewHolderBinding.onBind(
        item: EmployeeReviewModel, position: Int,
        isFirst: Boolean,
        isLast: Boolean
    ) {
        model = item
        executePendingBindings()
        /*otherCardItem.setOnClickListener {
            onItemClick()
        }*/
    }

    override fun isForViewType(item: Any) = item is EmployeeReviewModel

    override fun EmployeeReviewModel.getItemId() = uuid
}