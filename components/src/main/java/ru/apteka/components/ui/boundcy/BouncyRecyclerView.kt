package ru.apteka.components.ui.boundcy

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.widget.EdgeEffect
import androidx.dynamicanimation.animation.SpringAnimation
import androidx.dynamicanimation.animation.SpringForce
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.apteka.components.R
import ru.apteka.components.ui.boundcy.util.Bouncy
import ru.apteka.components.ui.boundcy.util.BouncyViewHolder
import ru.apteka.components.ui.boundcy.util.DragDropAdapter
import ru.apteka.components.ui.boundcy.util.DragDropCallBack
import ru.apteka.components.ui.boundcy.util.OnOverPullListener


class BouncyRecyclerView(context: Context, attrs: AttributeSet?) : RecyclerView(context, attrs) {
    private lateinit var callBack: DragDropCallBack

    var onOverPullListener: OnOverPullListener? = null

    var overscrollAnimationSize = 0.5f

    var flingAnimationSize = 0.5f

    var orientation: Int? = 1
        set(value) {
            field = value
            setupDirection(value)

        }

    var bouncyOverscrollMode = Bouncy.OVERSCROLL_ALL

    @Suppress("MemberVisibilityCanBePrivate")
    var dampingRatio = SpringForce.DAMPING_RATIO_NO_BOUNCY
        set(value) {
            field = value
            this.spring.spring = SpringForce()
                .setFinalPosition(0f)
                .setDampingRatio(value)
                .setStiffness(stiffness)
        }

    @Suppress("MemberVisibilityCanBePrivate")
    var stiffness = SpringForce.STIFFNESS_LOW
        set(value) {
            field = value
            this.spring.spring = SpringForce()
                .setFinalPosition(0f)
                .setDampingRatio(dampingRatio)
                .setStiffness(value)
        }

    @Suppress("MemberVisibilityCanBePrivate")
    var longPressDragEnabled = false
        set(value) {
            field = value
            if (adapter is DragDropAdapter<*>) callBack.setDragEnabled(value)
        }

    @Suppress("MemberVisibilityCanBePrivate")
    var itemSwipeEnabled = false
        set(value) {
            field = value
            if (adapter is DragDropAdapter<*>) callBack.setSwipeEnabled(value)
        }

    var spring: SpringAnimation = SpringAnimation(this, SpringAnimation.TRANSLATION_Y)
        .setSpring(
            SpringForce()
                .setFinalPosition(0f)
                .setDampingRatio(dampingRatio)
                .setStiffness(stiffness)
        )


    var touched: Boolean = false


    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(e: MotionEvent?): Boolean {

        touched = when (e?.actionMasked) {
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> false
            else -> true
        }
        return super.onTouchEvent(e)
    }


    override fun setAdapter(adapter: RecyclerView.Adapter<*>?) {
        super.setAdapter(adapter)
        if (adapter is DragDropAdapter<*>) {
            callBack = DragDropCallBack(adapter, longPressDragEnabled, itemSwipeEnabled)
            val touchHelper = ItemTouchHelper(callBack)
            touchHelper.attachToRecyclerView(this)
        }
    }

    inline fun <reified T : ViewHolder> RecyclerView.forEachVisibleHolder(action: (T) -> Unit) {
        for (i in 0 until childCount) action(getChildViewHolder(getChildAt(i)) as T)
    }


    override fun setLayoutManager(layout: LayoutManager?) {
        super.setLayoutManager(layout)
        if (layout is LinearLayoutManager) {
            orientation = layout.orientation
            setupDirection(orientation)
        }
    }


    private fun setupDirection(orientation: Int?) {
        if (stiffness > 0) {
            when (orientation) {
                HORIZONTAL -> spring = SpringAnimation(this, SpringAnimation.TRANSLATION_X)
                    .setSpring(
                        SpringForce()
                            .setFinalPosition(0f)
                            .setDampingRatio(dampingRatio)
                            .setStiffness(stiffness)
                    )

                VERTICAL -> spring = SpringAnimation(this, SpringAnimation.TRANSLATION_Y)
                    .setSpring(
                        SpringForce()
                            .setFinalPosition(0f)
                            .setDampingRatio(dampingRatio)
                            .setStiffness(stiffness)
                    )

            }
        }
    }

    init {
        context.theme.obtainStyledAttributes(attrs, R.styleable.BouncyRecyclerView, 0, 0)
            .apply {
                longPressDragEnabled =
                    getBoolean(R.styleable.BouncyRecyclerView_allowDragReorder, false)
                itemSwipeEnabled =
                    getBoolean(R.styleable.BouncyRecyclerView_allowItemSwipe, false)

                overscrollAnimationSize = getFloat(
                    R.styleable.BouncyRecyclerView_recyclerviewOverscrollAnimationSize,
                    0.5f
                )
                flingAnimationSize =
                    getFloat(R.styleable.BouncyRecyclerView_recyclerview_fling_animation_size, 0.5f)

                when (getInt(R.styleable.BouncyRecyclerView_bouncyRecyclerviewDampingRatio, 0)) {
                    0 -> dampingRatio = Bouncy.DAMPING_RATIO_NO_BOUNCY
                    1 -> dampingRatio = Bouncy.DAMPING_RATIO_LOW_BOUNCY
                    2 -> dampingRatio = Bouncy.DAMPING_RATIO_MEDIUM_BOUNCY
                    3 -> dampingRatio = Bouncy.DAMPING_RATIO_HIGH_BOUNCY
                }
                when (getInt(R.styleable.BouncyRecyclerView_bouncyRecyclerviewStiffness, 1)) {
                    0 -> stiffness = Bouncy.STIFFNESS_VERY_LOW
                    1 -> stiffness = Bouncy.STIFFNESS_LOW
                    2 -> stiffness = Bouncy.STIFFNESS_MEDIUM
                    3 -> stiffness = Bouncy.STIFFNESS_HIGH
                }
                when (getInt(
                    R.styleable.BouncyRecyclerView_bouncyRecyclerviewOverscrollMode,
                    bouncyOverscrollMode
                )) {
                    0 -> bouncyOverscrollMode = Bouncy.OVERSCROLL_ALL
                    1 -> bouncyOverscrollMode = Bouncy.OVERSCROLL_START
                    2 -> bouncyOverscrollMode = Bouncy.OVERSCROLL_END
                }
                recycle()
            }


        val rc = this


        this.edgeEffectFactory = object : EdgeEffectFactory() {
            override fun createEdgeEffect(recyclerView: RecyclerView, direction: Int): EdgeEffect {
                return object : EdgeEffect(recyclerView.context) {
                    override fun onPull(deltaDistance: Float) {
                        super.onPull(deltaDistance)
                        Log.d("myL", "onPull 1 " + deltaDistance)
                        onPullAnimation(deltaDistance)
                    }

                    @SuppressLint("SwitchIntDef")
                    override fun onPull(deltaDistance: Float, displacement: Float) {
                        super.onPull(deltaDistance, displacement)
                        Log.d("myL", "onPull 2 " + deltaDistance)
                        onPullAnimation(deltaDistance)

                        when (direction) {
                            DIRECTION_BOTTOM -> onOverPullListener?.onOverPulledBottom(deltaDistance)
                            DIRECTION_TOP -> onOverPullListener?.onOverPulledTop(deltaDistance)
                        }

                    }

                    private fun onPullAnimation(deltaDistance: Float) {
                        Log.d("myL", "onPullAnimation")
                        if (orientation == VERTICAL) {
                            val delta: Float =
                                if (direction == DIRECTION_BOTTOM) {
                                    if (bouncyOverscrollMode == Bouncy.OVERSCROLL_ALL || bouncyOverscrollMode == Bouncy.OVERSCROLL_END) {
                                        -1 * recyclerView.width * deltaDistance * overscrollAnimationSize
                                    } else {
                                        1f
                                    }
                                } else {
                                    if (bouncyOverscrollMode == Bouncy.OVERSCROLL_ALL || bouncyOverscrollMode == Bouncy.OVERSCROLL_START) {
                                        1 * recyclerView.width * deltaDistance * overscrollAnimationSize
                                    } else {
                                        0f
                                    }

                                }
                            rc.translationY += delta
                            spring.cancel()
                        } else {
                            val delta: Float =
                                if (direction == DIRECTION_RIGHT) {
                                    if (bouncyOverscrollMode == Bouncy.OVERSCROLL_ALL || bouncyOverscrollMode == Bouncy.OVERSCROLL_END) {
                                        -1 * recyclerView.width * deltaDistance * overscrollAnimationSize
                                    } else {
                                        1f
                                    }
                                } else {
                                    if (bouncyOverscrollMode == Bouncy.OVERSCROLL_ALL || bouncyOverscrollMode == Bouncy.OVERSCROLL_START) {
                                        1 * recyclerView.width * deltaDistance * overscrollAnimationSize
                                    } else {
                                        0f
                                    }
                                }
                            rc.translationX += delta
                            spring.cancel()
                        }

                        forEachVisibleHolder { holder: ViewHolder? ->
                            if (holder is BouncyViewHolder) holder.onPulled(
                                deltaDistance
                            )
                        }
                    }

                    override fun onRelease() {
                        super.onRelease()
                        Log.d("myL", "onRelease")
                        if (touched)
                            return


                        onOverPullListener?.onRelease()
                        spring.start()

                        forEachVisibleHolder { holder: ViewHolder? -> if (holder is BouncyViewHolder) holder.onRelease() }
                    }

                    override fun onAbsorb(velocity: Int) {
                        super.onAbsorb(velocity)
                        Log.d("myL", "onAbsorb")
                        if (orientation == VERTICAL) {
                            val v: Float =
                                if (direction == DIRECTION_BOTTOM) {
                                    if (bouncyOverscrollMode == Bouncy.OVERSCROLL_ALL || bouncyOverscrollMode == Bouncy.OVERSCROLL_END) {
                                        -1 * velocity * flingAnimationSize
                                    } else {
                                        1f
                                    }
                                } else {
                                    if (bouncyOverscrollMode == Bouncy.OVERSCROLL_ALL || bouncyOverscrollMode == Bouncy.OVERSCROLL_START) {
                                        1 * velocity * flingAnimationSize
                                    } else {
                                        0f
                                    }
                                }
                            spring.setStartVelocity(v).start()
                        } else {
                            val v: Float =
                                if (direction == DIRECTION_RIGHT) {
                                    if (bouncyOverscrollMode == Bouncy.OVERSCROLL_ALL || bouncyOverscrollMode == Bouncy.OVERSCROLL_END) {
                                        -1 * velocity * flingAnimationSize
                                    } else {
                                        0f
                                    }
                                } else {
                                    if (bouncyOverscrollMode == Bouncy.OVERSCROLL_ALL || bouncyOverscrollMode == Bouncy.OVERSCROLL_START) {
                                        1 * velocity * flingAnimationSize
                                    } else {
                                        0f
                                    }
                                }
                            spring.setStartVelocity(v).start()
                        }

                        forEachVisibleHolder { holder: ViewHolder? ->
                            if (holder is BouncyViewHolder) holder.onAbsorb(
                                velocity
                            )
                        }
                    }

                    override fun draw(canvas: Canvas?): Boolean {
                        setSize(0, 0)
                        return super.draw(canvas)
                    }
                }
            }
        }
    }

    abstract class Adapter<T : ViewHolder> : RecyclerView.Adapter<T>(), DragDropAdapter<T>
}
