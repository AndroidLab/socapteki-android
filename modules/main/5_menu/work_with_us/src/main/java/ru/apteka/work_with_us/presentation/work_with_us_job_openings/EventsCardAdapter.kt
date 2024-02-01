package ru.apteka.work_with_us.presentation.work_with_us_job_openings

import ru.apteka.components.ui.delegate_adapter.ViewBindingDelegateAdapter
import ru.apteka.work_with_us.databinding.JobOpeningsEventHolderBinding

/**
 * Представляет адаптер для карточки событий.
 */
class EventsCardAdapter :
    ViewBindingDelegateAdapter<Int, JobOpeningsEventHolderBinding>(JobOpeningsEventHolderBinding::inflate) {

    override fun JobOpeningsEventHolderBinding.onBind(
        item: Int,
        position: Int,
        isFirst: Boolean,
        isLast: Boolean
    ) {
        model = item
        executePendingBindings()
        /*advertCardItem.setOnClickListener {
            onItemClick()
        }*/
    }

    override fun isForViewType(item: Any) = item is Int

    override fun Int.getItemId() = this
}
