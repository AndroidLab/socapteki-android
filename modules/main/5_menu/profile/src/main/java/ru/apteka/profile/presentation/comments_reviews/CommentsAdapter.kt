package ru.apteka.profile.presentation.comments_reviews

import android.animation.Animator
import android.animation.ValueAnimator
import androidx.lifecycle.LifecycleOwner
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import ru.apteka.components.data.utils.launchMain
import ru.apteka.components.ui.delegate_adapter.ViewBindingDelegateAdapter
import ru.apteka.profile.data.models.CommentModel
import ru.apteka.profile.databinding.CommentHolderBinding
import ru.apteka.components.R as ComponentsR

/**
 * Представляет адаптер для комментариев.
 */
class CommentsAdapter(private val lifeOwner: LifecycleOwner) :
    ViewBindingDelegateAdapter<CommentModel, CommentHolderBinding>(CommentHolderBinding::inflate) {

    override fun CommentHolderBinding.onBind(
        item: CommentModel,
        position: Int,
        isFirst: Boolean,
        isLast: Boolean
    ) {
        lifecycleOwner = lifeOwner
        model = item

        var llCollapsedHeight = 0
        llCommentsReadCompletely.setOnClickListener {
            if (tvCommentsReadCompletely.text == root.context.getString(ComponentsR.string.read_completely)) {
                llCollapsedHeight = flCommentText.height
                flCommentText.layoutParams.height = llCollapsedHeight
                tvCommentsReadCompletely.text = root.context.getString(ComponentsR.string.hide)
                tvCommentText.maxLines = Int.MAX_VALUE
                GlobalScope.launchMain {
                    delay(100)
                    ValueAnimator.ofInt(
                        flCommentText.height,
                        tvCommentText.layout.height
                        // llCollapsedHeight,
                        // tvCommentText.layout.height
                    ).apply {
                        addUpdateListener { valueAnimator ->
                            val lp = flCommentText.layoutParams
                            flCommentText.layoutParams =
                                lp.apply { height = valueAnimator.animatedValue as Int }
                        }
                        duration = 350
                        start()
                    }
                }
            } else {
                GlobalScope.launchMain {
                    delay(100)
                    ValueAnimator.ofInt(
                        flCommentText.height,
                        llCollapsedHeight
                    ).apply {
                        addUpdateListener { valueAnimator ->
                            val lp = flCommentText.layoutParams
                            flCommentText.layoutParams =
                                lp.apply { height = valueAnimator.animatedValue as Int }
                        }
                        addListener(
                            object : Animator.AnimatorListener {
                                override fun onAnimationStart(animation: Animator) {}
                                override fun onAnimationEnd(animation: Animator) {
                                    tvCommentsReadCompletely.text =
                                        root.context.getString(ComponentsR.string.read_completely)
                                    tvCommentText.maxLines = 5
                                }

                                override fun onAnimationCancel(animation: Animator) {}
                                override fun onAnimationRepeat(animation: Animator) {}
                            }
                        )
                        duration = 350
                        start()
                    }
                }
            }
        }

        executePendingBindings()
    }

    override fun isForViewType(item: Any) = item is CommentModel

    override fun CommentModel.getItemId() = id
}
